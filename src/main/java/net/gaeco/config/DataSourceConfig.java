package net.gaeco.config;

import net.sf.log4jdbc.sql.jdbcapi.DataSourceSpy;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ClassUtils;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@MapperScan("net.gaeco.dao" ) //--> spring boot가 아닐경우 반드시 명시해야한다.
@PropertySource("classpath:application.properties")
public class DataSourceConfig {
    @Bean(name = "oneDataSource")
    public DataSource erpDataSource(@Value("${gaeco.datasource.sqlTrace}") String sqlTrace,
                                      @Value("${gaeco.datasource.type}") String type,
                                      @Value("${gaeco.datasource.driverClassName}") String driverClassName,
                                      @Value("${gaeco.datasource.url}") String url,
                                      @Value("${gaeco.datasource.username}") String username,
                                      @Value("${gaeco.datasource.password}") String password,
                                      @Value("${gaeco.datasource.maxActive}") Integer maxActive,
                                      @Value("${gaeco.datasource.maxIdle}") Integer maxIdle,
                                      @Value("${gaeco.datasource.maxWait}") Integer maxWait,
                                      @Value("${gaeco.datasource.jndiName}") String jndiName,
                                      @Value("${gaeco.datasource.connectionProperties}") String connectionProperties ) throws Exception{

        DataSource dataSource = null;

        // JNDI이름이 먼저 있다면 우선 JNDI Lookup부터 수행한다.
        if(jndiName != null && !"".equals(jndiName) && !jndiName.startsWith("$")){
            final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
            dsLookup.setResourceRef(true);
            dataSource = dsLookup.getDataSource(jndiName);
        }else {
            // DataSource를 직접 생성한다.
            Class<? extends DataSource> dataSourceClass = (Class<? extends DataSource>) ClassUtils.forName(type, null);
            dataSource = BeanUtils.instantiate(dataSourceClass);
            BeanWrapper beanWrapper = new BeanWrapperImpl(dataSource);
            beanWrapper.setPropertyValue("driverClassName", driverClassName);
            beanWrapper.setPropertyValue("url", url);
            beanWrapper.setPropertyValue("username", username);
            beanWrapper.setPropertyValue("password", password);
            beanWrapper.setPropertyValue("maxActive", maxActive);
            beanWrapper.setPropertyValue("maxIdle", maxIdle);
            beanWrapper.setPropertyValue("maxWait", maxWait);
            beanWrapper.setPropertyValue("connectionProperties", connectionProperties);
        }
        //SQL로그 출력을 위한 sqlTrace가 true이면 DataSource를 wrapping한다.
        if(sqlTrace != null && "true".equalsIgnoreCase(sqlTrace.trim()))
            dataSource = new DataSourceSpy(dataSource);

        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager transactionManager(@Qualifier("oneDataSource")DataSource erpDataSource) {
        return new DataSourceTransactionManager(erpDataSource);
    }

    @Bean
    public SqlSessionFactory erpSqlSessionFactory(@Qualifier("oneDataSource")DataSource erpDataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(erpDataSource);
//        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mapper/first/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate erpSqlSessionTemplate(SqlSessionFactory erpSqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(erpSqlSessionFactory);
    }

}