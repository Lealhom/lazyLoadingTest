package com.omg.lazyLoadingTest.multitanant.aspect;

import com.omg.lazyLoadingTest.multitanant.EnableMultitenancy;
import com.omg.lazyLoadingTest.multitanant.MyHeaderHolder;
import com.omg.lazyLoadingTest.multitanant.MyHeaderParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;


@RestControllerAdvice(annotations = { EnableMultitenancy.class })
public class MultitenantControllerAdvice {

    @Autowired
    private MyHeaderHolder myHeaderHolder;


    @ModelAttribute
    public MyHeaderParameters myHeader(final HttpServletRequest request) {
        return myHeaderHolder.extractHeaderFromHttpRequest(request);
    }
}
