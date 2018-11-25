package spittr.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

@Configuration
@EnableTransactionManagement
@ComponentScan
public class SpittrConfig implements TransactionManagementConfigurer {
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:tcp://localhost/~/spittr");
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		return dataSource;
	}

	@Bean(name = "jdbcOperations")
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory());
		return transactionManager;
	}

	@Bean
	public DataSource dataSourceEmbeded() {
		EmbeddedDatabaseBuilder edb = new EmbeddedDatabaseBuilder();
		edb.setType(EmbeddedDatabaseType.H2);
		edb.addScript("spittr/db/schema.sql");
		edb.addScript("spittr/db/test-data.sql");
		EmbeddedDatabase embeddedDatabase = edb.build();
		return embeddedDatabase;
	}

	@Bean
	public SessionFactory sessionFactory() {
		try {
			LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
			lsfb.setDataSource(dataSource());
			lsfb.setPackagesToScan("spittr.dto");
			Properties props = new Properties();
			props.setProperty("dialect", "org.hibernate.dialect.H2Dialect");
			lsfb.setHibernateProperties(props);
			lsfb.afterPropertiesSet();
			SessionFactory object = lsfb.getObject();
			return object;
		} catch (IOException e) {
			return null;
		}
	}

	@Bean
	public BeanPostProcessor persistenceTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
}
