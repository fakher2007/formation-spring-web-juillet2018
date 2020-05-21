package com.acme.common;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class JpaConfig4Test {

	
	@Bean(name="emf")
	public FactoryBean<EntityManagerFactory> emfForTests_(){
		
		DataSource ds = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.DERBY).build();
		
        Properties jpaProperties = new Properties();
        jpaProperties.put("javax.persistence.schema-generation.database.action", "create");
        jpaProperties.put("javax.persistence.sql-load-script-source", "test-data.sql");
        
        jpaProperties.put("hibernate.cache.use_second_level_cache", true);
        jpaProperties.put("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
        jpaProperties.put("hibernate.show_sql", "false");
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.DerbyTenSevenDialect");
        
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setPersistenceProviderClass(org.hibernate.jpa.HibernatePersistenceProvider.class);
		factoryBean.setJpaProperties(jpaProperties);
		factoryBean.setDataSource(ds);

		// même si notre responsabilité se limite à la création d'une FactoryBean<EntityManagerFactory> (LocalContainerEntityManagerFactoryBean), 
		// c'est au final un bean de type EntityManagerFactory qui sera inscrit dans le contexte 
		return factoryBean;

	}
}
