package com.omg.lazyLoadingTest.multitanant.context;

import com.omg.lazyLoadingTest.multitanant.MyHeaderHolder;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;


public class ContextAwarePoolExecutor extends ThreadPoolTaskExecutor {
	

	private static final long serialVersionUID = -512901139668329917L;


    @Override
    public <T> Future<T> submit(Callable<T> task) {
    	final ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        final Map<String, String> threadContext = MyHeaderHolder.setTenantIdAndCloneToNewThreadContext();
        return super.submit(new ContextAwareCallable<T>(task, servletRequestAttributes, threadContext));
    }


    @Override
    public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
    	final ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        final Map<String, String> threadContext = MyHeaderHolder.setTenantIdAndCloneToNewThreadContext();
        return super.submitListenable(new ContextAwareCallable<T>(task, servletRequestAttributes, threadContext));
    }
    
}
