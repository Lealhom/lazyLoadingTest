package com.omg.lazyLoadingTest.multitanant;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * using EclipseLInkJpaVendorAdapter() for creation of datasource and transation.
 * 
 */
@Configuration
public class JpaConfig extends JpaBaseConfiguration
{
	protected JpaConfig(DataSource dataSource, JpaProperties properties,
                        ObjectProvider<JtaTransactionManager> jtaTransactionManagerProvider,
                        ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers)
	{
		super(dataSource, properties, jtaTransactionManagerProvider, transactionManagerCustomizers);
	}

	@Override
	protected AbstractJpaVendorAdapter createJpaVendorAdapter()
	{
		return new EclipseLinkJpaVendorAdapter();
	}

	@Override
	protected Map<String, Object> getVendorProperties()
	{
		final HashMap<String, Object> props = new HashMap<>();
		props.put(PersistenceUnitProperties.WEAVING, detectWeavingMode());
		return props;
	}

	private String detectWeavingMode()
	{
		String mode = InstrumentationLoadTimeWeaver.isInstrumentationAvailable() ? "true" : "static";
		System.out.println("mode1:"+mode);
		return mode;
	}
	

	@Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
        final JpaTransactionManager transactionManager = new MultiTenantJpaTransactionManager();
		HashMap<String, Object> props = new HashMap<>();
//		props.put("loadTimeWeaver","org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver");
		transactionManager.setJpaPropertyMap(props);
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }


}
