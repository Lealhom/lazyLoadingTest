package com.omg.lazyLoadingTest.multitanant;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.EntityManager;


public class MultiTenantJpaTransactionManager extends JpaTransactionManager {
 

	private static final long serialVersionUID = 2572923846057689785L;
	
	@Autowired
    private MyHeaderHolder headerHolder;
 

    @Override
    protected void doBegin(final Object transaction, final TransactionDefinition definition) {
        logger.info("MultiTanantJPATransactionManager: begin");
        super.doBegin(transaction, definition);
        try {
            final EntityManagerHolder entityManagerHolder = (EntityManagerHolder) TransactionSynchronizationManager.getResource(getEntityManagerFactory());
            if(entityManagerHolder == null) {
                return;
            }
            final EntityManager entityManager = entityManagerHolder.getEntityManager();
            final String currentTenantSet = (String) entityManager.getProperties().get(PersistenceUnitProperties.MULTITENANT_PROPERTY_DEFAULT);
            try{
            	final MyHeaderParameters headerParameters  = headerHolder.extractHeaderFromThreadLocal();
            	logger.info("MultiTanantJPATransactionManager :  headerParameters="+headerParameters);
	        	if (headerParameters != null) {
		        	final String resolvedTenant = headerParameters.getTenant();
		        	logger.info("MultiTanantJPATransactionManager,resolvedTenant:"+resolvedTenant+",currentTenantSet:"+currentTenantSet);
		            //check whether there is a conflict due to concurrent access to entity manager
		            if (currentTenantSet != null && !currentTenantSet.equals(resolvedTenant)) {
		                throw new IllegalStateException("Resource conflict - the entity manager is already assigned to tenant '" + currentTenantSet + "'. It cannot be reassigned to tenant '" + resolvedTenant + "'.");
		            }
		            entityManager.setProperty(PersistenceUnitProperties.MULTITENANT_PROPERTY_DEFAULT, resolvedTenant);
	        	}
            }catch(final NullPointerException ex) {
            	// see : currently don't need to process with NullPointerException
            }
        } catch (final RuntimeException e) {
            this.doCleanupAfterCompletion(transaction);
            throw e;
        }
    }
}
