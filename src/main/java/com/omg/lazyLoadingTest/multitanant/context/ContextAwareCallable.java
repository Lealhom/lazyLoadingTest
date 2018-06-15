package com.omg.lazyLoadingTest.multitanant.context;

import com.omg.lazyLoadingTest.multitanant.MyHeaderHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Map;
import java.util.concurrent.Callable;


public class ContextAwareCallable<T> implements Callable<T> {
   

    private Callable<T> task;
    

    private RequestAttributes context;
    

    private Map<String, String> threadContext;


    public ContextAwareCallable(Callable<T> task, final RequestAttributes context, final Map<String, String> threadContext) {
        this.task = task;
        this.context = context;
        this.threadContext = threadContext;
    }


    @Override
    public T call() throws Exception {
        if (this.threadContext != null) {
            MyHeaderHolder.setThreadLocalContext(this.threadContext);
        }
        if (this.context != null) {
            RequestContextHolder.setRequestAttributes(this.context, true);
        }

        try {
            return task.call();
        } finally {
            if (this.context != null) {
            	RequestContextHolder.resetRequestAttributes();
            }
            if (this.threadContext != null) {
                MyHeaderHolder.clearThreadLocalContext();
            }
        }
    }
}