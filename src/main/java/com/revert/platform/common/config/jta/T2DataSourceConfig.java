package com.revert.platform.common.config.jta;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.github.pagehelper.PageHelper;
import com.revert.platform.common.annotation.T2MySqlDao;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@MapperScan(basePackages = "com.revert", sqlSessionFactoryRef = "t2SqlSessionFactory", annotationClass = T2MySqlDao.class)
public class T2DataSourceConfig {
    @Autowired
    private Environment env;
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
    @Bean(name="t2DataSource")
    public DataSource masterDataSource() {
        String prefix = "spring.datasource.druid2.";
        DruidXADataSource druidXADataSource = new DruidXADataSource();
        druidXADataSource.setUrl( env.getProperty(prefix + "url") );
        druidXADataSource.setUsername( env.getProperty(prefix + "username") );
        druidXADataSource.setPassword( env.getProperty(prefix + "password") );
        druidXADataSource.setDriverClassName( env.getProperty(prefix + "driver-class-name") );

        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        ds.setXaDataSource(druidXADataSource);
        ds.setUniqueResourceName("t2DataSource");
        ds.setMaxPoolSize(Integer.parseInt(env.getProperty(prefix + "maxActive")));
        ds.setMinPoolSize(Integer.parseInt(env.getProperty(prefix + "initialSize")));
        return ds;
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
