package com.revert.platform.common.config.datasources;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.github.pagehelper.PageHelper;
import com.revert.platform.common.annotation.SPMySqlDao;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;
import java.util.Properties;

//@Configuration
//@MapperScan(basePackages = "com.revert", sqlSessionFactoryRef = "spSqlSessionFactory", annotationClass = SPMySqlDao.class)
public class SPDataSourceConfig {

    @Value("${platform.dataSource.type}")
    private String datasourceType;

    @Value("${platform.druid.stat.loginUsername}")
    private String loginUsername;

    @Value("${platform.druid.stat.loginPassword}")
    private String loginPassword;

    /**
     * 数据源
     * @return
     */
    @Bean(name= "spDataSource",initMethod = "init", destroyMethod = "close")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSource spDataSource() {
        return new DruidDataSource();
    }

    /**
     * 根据数据源创建对应的jdbcTemplate
     */
    @Bean(name = "spJdbcTemplate")
    public JdbcTemplate spJdbcTemplate(@Qualifier("spDataSource")DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    /**
     * mybatis SQLsessionManage
     */

    @Bean(name = "spSqlSessionFactory")
    @Primary
    public SqlSessionFactory spSqlSessionFactory() throws Exception {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(spDataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(
                resolver.getResources("classpath*:com/revert/"+datasourceType+"/**/mapper/impI/*Mapper.xml")
        );
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pagePlugin()});
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name="spTransactionManager")
    public DataSourceTransactionManager spTransactionManager(@Qualifier("spDataSource") DataSource masterDataSource) {
        return new DataSourceTransactionManager(masterDataSource);
    }
//    @Bean(name="nameMatchTransactionAttributeSource")
//    public NameMatchTransactionAttributeSource nameMatchTransactionAttributeSource() {
//        NameMatchTransactionAttributeSource nameMatchTransactionAttributeSource = new NameMatchTransactionAttributeSource();
//        Properties properties = new Properties();
//        properties.put("get*","PROPAGATION_REQUIRED,readOnly");
//        properties.put("list*","PROPAGATION_REQUIRED,readOnly");
//        properties.put("find*","PROPAGATION_REQUIRED,readOnly");
//        properties.put("select*","PROPAGATION_REQUIRED,readOnly");
//        properties.put("load*","PROPAGATION_REQUIRED,readOnly");
//        properties.put("query*","PROPAGATION_REQUIRED,readOnly");
//        properties.put("import*","PROPAGATION_REQUIRED,readOnly");
//        properties.put("export*","PROPAGATION_REQUIRED,readOnly");
//
//        properties.put("save*","PROPAGATION_REQUIRED");
//        properties.put("submit*","PROPAGATION_REQUIRED");
//        properties.put("send*","PROPAGATION_REQUIRED");
//        properties.put("create*","PROPAGATION_REQUIRED");
//        properties.put("update*","PROPAGATION_REQUIRED");
//        properties.put("modify*","PROPAGATION_REQUIRED");
//        properties.put("delete*","PROPAGATION_REQUIRED");
//        properties.put("remove*","PROPAGATION_REQUIRED");
//        properties.put("restore*","PROPAGATION_REQUIRED");
//        properties.put("complete*","PROPAGATION_REQUIRED");
//        properties.put("cancel*","PROPAGATION_REQUIRED");
//        properties.put("do*","PROPAGATION_REQUIRED");
//        properties.put("execute*","PROPAGATION_REQUIRED");
//
//        properties.put("debug*","PROPAGATION_REQUIRES_NEW");
//        properties.put("error*","PROPAGATION_REQUIRES_NEW");
//        properties.put("fatal*","PROPAGATION_REQUIRES_NEW");
//        properties.put("warn*","PROPAGATION_REQUIRES_NEW");
//        nameMatchTransactionAttributeSource.setProperties(properties);
//        return nameMatchTransactionAttributeSource;
//    }
//    @Bean(name="spTransactionInterceptor")
//    public TransactionInterceptor masterTransactionInterceptor(@Qualifier("spTransactionManager") DataSourceTransactionManager masterTransactionManager,
//                                                               NameMatchTransactionAttributeSource nameMatchTransactionAttributeSource) {
//        TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
//        transactionInterceptor.setTransactionManager(masterTransactionManager);
//        transactionInterceptor.setTransactionAttributeSource(nameMatchTransactionAttributeSource);
//        return transactionInterceptor;
//    }



    /**
     * druid 连接池 界面优化版
     * @return
     */
    @Bean
    @Primary
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean reg = new ServletRegistrationBean();
        reg.setServlet(new StatViewServlet());
        reg.addUrlMappings("/druid/*");
        reg.addInitParameter("loginUsername", loginUsername);
        reg.addInitParameter("loginPassword", loginPassword);
        reg.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return reg;
    }

    /**
     * mybatis 分页插件
     */
    public PageHelper pagePlugin(){
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("dialect", datasourceType);
        properties.setProperty("reasonable", "true");
        pageHelper.setProperties(properties);
        return pageHelper;
    }



}
