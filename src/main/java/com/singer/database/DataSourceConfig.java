package com.singer.database;

import javax.inject.Inject;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.singer.util.PropertyUtil;

import net.sf.log4jdbc.Log4jdbcProxyDataSource;
import net.sf.log4jdbc.tools.Log4JdbcCustomFormatter;
import net.sf.log4jdbc.tools.LoggingType;

@Configuration
@MapperScan("com.singer.dao")
public class DataSourceConfig {

	private final Log log = LogFactory.getLog(DataSourceConfig.class);

	@Inject
	private PropertyUtil propertyUtil;

	@Bean(name = "dataSourceSpied")
	public BasicDataSource getBasicDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(propertyUtil.getOracleDriver());
		dataSource.setUrl(propertyUtil.getOracleUrl());
		dataSource.setUsername(propertyUtil.getOracleUsername());
		dataSource.setPassword(propertyUtil.getOraclePassword());
//		maxActive 최대 커넥션 >= initialSize 초기 커넥션 default 0
//		maxActive 최대 커넥션 = maxIdle 최대유지 커넥션
//		Connection Pool < WAS Thread 갯수 
		dataSource.setMaxActive(10);
		dataSource.setInitialSize(10);
		dataSource.setMaxIdle(10);
		dataSource.setMinIdle(10);
// 		pool이 고갈되었을 경우 최대 대기 타임
		dataSource.setMaxWait(3000);
//		Evictor 동작 시 커넥션의 유휴 시간을 확인해 설정 값 이상일 경우 커넥션을 제거 (ms단위, default = 30분) -1 로 설정할 경우 사용하지 않음. (권장)	
		dataSource.setMinEvictableIdleTimeMillis(-1);
//		preparedStatement 풀링 여부
		dataSource.setPoolPreparedStatements(true);
//		커넥션 당 최대 pooling 할 PreparedStatement 의 갯수 default가 무한이라 OOM 발생 가능
		dataSource.setMaxOpenPreparedStatements(50);
		log.debug("BasicDataSource create");
		return dataSource;
	}

	@Bean(name = "dataSource")
	public Log4jdbcProxyDataSource getLog4jdbcProxyDataSource() {
		Log4jdbcProxyDataSource log4DataSource = new Log4jdbcProxyDataSource(getBasicDataSource());
		Log4JdbcCustomFormatter log4Formatter = new Log4JdbcCustomFormatter();
		log4Formatter.setLoggingType(LoggingType.SINGLE_LINE);
		log4Formatter.setSqlPrefix("SQL :  ");
		log4DataSource.setLogFormatter(log4Formatter);
		return log4DataSource;
	}

	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory getSqlSessionFactoryBean() throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(getLog4jdbcProxyDataSource());
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
		transactionManager.setDataSource(getLog4jdbcProxyDataSource());
		return transactionManager;
	}

}
