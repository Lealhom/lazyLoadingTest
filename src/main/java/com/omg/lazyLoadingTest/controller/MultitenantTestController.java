package com.omg.lazyLoadingTest.controller;

import com.omg.lazyLoadingTest.model.CopyCustomer;
import com.omg.lazyLoadingTest.model.Order;
import com.omg.lazyLoadingTest.model.OrderLine;
import com.omg.lazyLoadingTest.multitanant.EnableMultitenancy;
import com.omg.lazyLoadingTest.repository.CopyCustomerRepository;
import com.omg.lazyLoadingTest.repository.OrderLineRepository;
import com.omg.lazyLoadingTest.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/mul-test")
@EnableMultitenancy
public class MultitenantTestController {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CopyCustomerRepository copyCustomerRepository;

    @Autowired
    private OrderLineRepository orderLineRepository;

    @RequestMapping("/save")
    public void save(){
        Order order = new Order();
        CopyCustomer copyCustomer = new CopyCustomer();
        copyCustomer.setCustomerNumber("123456");
        order.setCopyCustomer(copyCustomer);

        OrderLine line1 = new OrderLine();
        line1.setLineNumber("1");

        OrderLine line2 = new OrderLine();
        line2.setLineNumber("2");

        List<OrderLine> orderLineList = new ArrayList<>();
        orderLineList.add(line1);
        orderLineList.add(line2);
        order.setOrderLines(orderLineList);

        line1.setOrder(order);
        line2.setOrder(order);


        orderLineRepository.save(line1);
        orderLineRepository.save(line2);
        copyCustomerRepository.save(copyCustomer);
        orderRepository.save(order);
    }


    @GetMapping(path = "/order/{id}")
    @ResponseBody
    public Order get(@PathVariable String id) {
        Order order = orderRepository.findOne(id);
        return order;
    }
    @GetMapping(path = "/order2/{id}")
    public Order get2(@PathVariable String id) {
        Order order = orderRepository.findOne(id);
        return order;
    }
    @GetMapping(path = "/order3/{id}")
    public Order get3(@PathVariable String id) {
        Order order = orderRepository.findOne(id);
        order.getOrderLines().size();//OneToMany 懒加载有效，执行这句才会打印查询order line的sql
        System.out.println("#########################");
        order.getCopyCustomer().getId();//OneToOne 懒加载失效
        return order;
    }
}
