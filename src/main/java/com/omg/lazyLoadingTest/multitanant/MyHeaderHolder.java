/**
 *
 */
package com.omg.lazyLoadingTest.multitanant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class MyHeaderHolder {
    private static final Logger logger = LoggerFactory.getLogger(MyHeaderHolder.class);


    @Autowired
    private Validator validator;

    public static final String X_FORWARDED_HOST = "X-Forwarded-Host";


    private static final ThreadLocal<Map<String, String>> requestAttributesHolder =
            new NamedThreadLocal<>("Request attributes");


    private static void updateTenatIdForCurrentThreadLocal(final String tenantId) {
        Map<String, String> map = requestAttributesHolder.get();
        if (map == null) {
            map = new HashMap<String, String>() {

                private static final long serialVersionUID = 531602980020679250L;

                {
                    this.put(MyHeaderParameters.TEST_TENANT, tenantId);
                }
            };
            requestAttributesHolder.set(map);
        } else {
            // already exists map, just update
            map.put(MyHeaderParameters.TEST_TENANT, tenantId);
        }
    }


    public static String getTenantIdFromThreadLocal() {
        final Map<String, String> map = requestAttributesHolder.get();
        if (map == null) {
            throw new IllegalStateException("Thread context Map couldn't be null");
        }
        return map.get(MyHeaderParameters.TEST_TENANT);
    }


    public static Map<String, String> setTenantIdAndCloneToNewThreadContext() {
        final ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        if (servletRequestAttributes != null) {
            final String tenantId = servletRequestAttributes.getRequest()
                    .getHeader(MyHeaderParameters.TEST_TENANT);
            if (tenantId != null) {
                MyHeaderHolder.updateTenatIdForCurrentThreadLocal(tenantId);
            }
        }
        final Map<String, String> map = requestAttributesHolder.get();
        final Map<String, String> newThreadContext = new HashMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            newThreadContext.put(entry.getKey(), entry.getValue());
        }
        return newThreadContext;
    }


    public static void setThreadLocalContext(final Map<String, String> threadContext) {
        if (threadContext == null) {
            throw new IllegalStateException("Thread context Map couldn't be null");
        } else {
            requestAttributesHolder.set(threadContext);
        }
    }


    public static void clearThreadLocalContext() {
        requestAttributesHolder.remove();
    }



    public MyHeaderParameters extractHeaderFromHttpRequest(final HttpServletRequest request) {
        if (request == null) {
            throw new RuntimeException("http request object shouldn't be null");
        }
        try {
            String stringHeader = "test_tenant";

            MyHeaderHolder.updateTenatIdForCurrentThreadLocal(stringHeader);
            final String tenantId = stringHeader;
            final MyHeaderParameters header = new MyHeaderParameters() {
                {
                    this.setTenant(tenantId);
                }
            };

            return header;
        } catch (Exception exc) {
            System.out.println("error");
            return null;
        }
    }


    public MyHeaderParameters extractHeaderFromThreadLocal() {
        try {
            String _tenantid = requestAttributesHolder.get().get(MyHeaderParameters.TEST_TENANT);
            // see : again try from head
            if (_tenantid == null) {
                ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                        .getRequestAttributes();
                if (servletRequestAttributes == null) {
                    throw new RuntimeException("tenant doesn't exist in http request header");
                }
                _tenantid = requestAttributesHolder.get().get(MyHeaderParameters.TEST_TENANT);

            }
            final String stringHeader = _tenantid;
            final MyHeaderParameters header = new MyHeaderParameters() {
                {
                    this.setTenant(stringHeader);
                }
            };

            return header;
        } catch (final Exception ex) {
            System.out.println("error");
            return null;
        }
    }

    private String getTenantIdFromUrl(String url) {
        Pattern p = Pattern.compile(System.getenv().get("TENANT_REGEX"));
        Matcher m = p.matcher(url);
        while (m.find()) {
            return m.group(1);
        }
        return null;
    }
}
