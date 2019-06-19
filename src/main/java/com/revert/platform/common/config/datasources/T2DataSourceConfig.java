package com.revert.platform.common.config.datasources;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.github.pagehelper.PageHelper;
import com.revert.platform.common.annotation.SPMySqlDao;
import com.revert.platform.common.annotation.T2MySqlDao;
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
//@MapperScan(basePackages = "com.revert", sqlSessionFactoryRef = "t2SqlSessionFactory", annotationClass = T2MySqlDao.class)
public class T2DataSourceConfig {

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
    @Bean(name="t2DataSource",initMethod = "init", destroyMethod = "close")
    @ConfigurationProperties(prefix = "spring.datasource.druid2")
    public DataSource masterDataSource() {
        return new DruidDataSource();
    }

    /**
     * 根据数据源创建对应的jdbcTemplate
     */
    @Bean(name = "masterJdbcTemplate")
    public JdbcTemplate masterJdbcTemplate(@Qualifier("t2DataSource")DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean(name="t2TransactionManager")
    public DataSourceTransactionManager t2TransactionManager(@Qualifier("t2DataSource") DataSource masterDataSource) {
        return new DataSourceTransactionManager(masterDataSource);
    }
//    @Bean(name="t2TransactionInterceptor")
//    public TransactionInterceptor t2TransactionInterceptor(@Qualifier("t2TransactionManager") DataSourceTransactionManager masterTransactionManager,
//                                                               NameMatchTransactionAttributeSource nameMatchTransactionAttributeSource) {
//        TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
//        transactionInterceptor.setTransactionManager(masterTransactionManager);
//        transactionInterceptor.setTransactionAttributeSource(nameMatchTransactionAttributeSource);
//        return transactionInterceptor;
//    }
    /**
     * mybatis SQLsessionManage
     */

    @Bean(name = "t2SqlSessionFactory")
    public SqlSessionFactory masterSqlSessionFactory() throws Exception {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(masterDataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(
                resolver.getResources("classpath*:com/revert/"+datasourceType+"/**/mapper/impI/*Mapper.xml")
        );
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pagePlugin()});
        return sqlSessionFactoryBean.getObject();
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
