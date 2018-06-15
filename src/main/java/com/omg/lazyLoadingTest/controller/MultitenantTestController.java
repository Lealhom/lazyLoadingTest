package com.omg.lazyLoadingTest.controller;

import com.omg.lazyLoadingTest.model.CopyCustomer;
import com.omg.lazyLoadingTest.model.Order;
import com.omg.lazyLoadingTest.multitanant.EnableMultitenancy;
import com.omg.lazyLoadingTest.repository.CopyCustomerRepository;
import com.omg.lazyLoadingTest.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/mul-test")
@EnableMultitenancy
public class MultitenantTestController {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CopyCustomerRepository copyCustomerRepository;

    @RequestMapping("/save")
    public void save(){
        Order order = new Order();
        CopyCustomer copyCustomer = new CopyCustomer();
        copyCustomer.setCustomerNumber("123456");
        order.setCopyCustomer(copyCustomer);
        copyCustomerRepository.save(copyCustomer);
        orderRepository.save(order);
    }


    @GetMapping(path = "/order/{id}")
    @ResponseBody
    public Order get(@PathVariable String id) {
        Order order = orderRepository.findOne(id);
        return order;
    }

}
