/**
 * 
 */
package com.omg.lazyLoadingTest.multitanant;

import javax.ws.rs.HeaderParam;


public class MyHeaderParameters {

	@HeaderParam(TEST_TENANT)
	private String tenant;


	public static final String TEST_TENANT = "test-tenant";


	public final String getTenant() {
		return tenant;
	}


	public final void setTenant(final String tenant) {
		this.tenant = tenant;
	}

}
