package com.singer.database;

import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan("com.singer.dao")
@Slf4j
public class DataSourceConfig {

    private DataSource dataSource;

    public DataSourceConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory getSqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(
            new PathMatchingResourcePatternResolver().getResources("classpath*:mappers/*Mapper.xml"));
        log.debug("SqlSessionFactory create");
        return factoryBean.getObject();
    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate getSqlSessionTemplate() throws Exception {
        log.debug("SqlSessionTemplate create");
        return new SqlSessionTemplate((SqlSessionFactory) getSqlSessionFactoryBean());
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager getDataSourceTransactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }
}
