package com.thunisoft.orchid.config;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.thunisoft.artery.data.mybatis.ArteryInterceptor;
import com.thunisoft.artery.datadict.mybatis.interceptor.DictGroupTagInterceptor;
import com.zaxxer.hikari.HikariDataSource;

/**
 * 检察服务事项办理系统数据源
 * 
 * @author huayu
 *
 */
@Configuration
@Import({ DictGroupTagInterceptor.class, ArteryInterceptor.class })
@MapperScan(basePackages = "com.thunisoft.clue.mapperFwzx", sqlSessionTemplateRef = "fwzxSqlSessionTemplate")
public class TyywDataSourceConfig {
	@Autowired
	private DictGroupTagInterceptor dictGroupTagInterceptor;
	@Autowired
	private ArteryInterceptor arteryInterceptor;

	/**
	 * fwzxDataSourceBuilder
	 * 
	 * @return javax.sql.DataSource
	 */
	@Bean(name = "fwzxDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.db-fwzx")
	public DataSource fwzxDataSource() {
		HikariDataSource build = (HikariDataSource) DataSourceBuilder.create().build();
		build.addDataSourceProperty("oracle.jdbc.ReadTimeout", "30000");
		return build;
	}

	/**
	 * SqlSessionFactory
	 * 
	 * @param dataSource 数据源
	 * @return  SqlSessionFactory
	 * @throws Exception 异常
	 */
	@Bean(name = "fwzxSqlSessionFactory")
	public SqlSessionFactory fwzxSqlSessionFactory(@Qualifier("fwzxDataSource") DataSource dataSource)
			throws Exception {
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setMapperLocations(
				new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setPlugins(new Interceptor[] { dictGroupTagInterceptor, arteryInterceptor });
		return sessionFactory.getObject();
	}

	/**
	 * DataSourceTransactionManager
	 * 
	 * @param dataSource 数据源
	 * @return DataSourceTransactionManager
	 */
	@Bean(name = "fwzxTransactionManagerOther")
	public DataSourceTransactionManager testTransactionManager(@Qualifier("fwzxDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	/**
	 * SqlSessionTemplate
	 * 
	 * @param sqlSessionFactory  sqlSessionFactory
	 * @return SqlSessionTemplate
	 * @throws Exception 异常
	 */
	@Bean(name = "fwzxSqlSessionTemplate")
	public SqlSessionTemplate fwzxSqlSessionTemplate(
			@Qualifier("fwzxSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
