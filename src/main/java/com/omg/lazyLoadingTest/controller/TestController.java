package com.omg.lazyLoadingTest.controller;

import com.omg.lazyLoadingTest.model.BankCard;
import com.omg.lazyLoadingTest.model.IdentityCard;
import com.omg.lazyLoadingTest.model.Person;
import com.omg.lazyLoadingTest.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    private PersonRepository personRepository;

    @RequestMapping("/save")
    public void save(){
        Person person = new Person();
        IdentityCard identityCard = new IdentityCard();
        identityCard.setCardNo("511524199878945687");

        BankCard bankCard1 = new BankCard();
        bankCard1.setBankName("工商银行");

        BankCard bankCard2 = new BankCard();
        bankCard2.setBankName("农业银行");

        person.getBankCards().add(bankCard1);
        person.getBankCards().add(bankCard2);

        personRepository.save(person);

    }

    @GetMapping(path = "/test1/{id}")
    @ResponseBody
    public Person test1(@PathVariable String id) {
        Person person = personRepository.findOne(id);
        return person;
    }
    @GetMapping(path = "/test2/{id}")
    public Person test2(@PathVariable String id) {
        Person person = personRepository.findOne(id);
        person.getIdentityCard();
        return person;
    }
    @GetMapping(path = "/test3/{id}")
    public Person test3(@PathVariable String id) {
        Person person = personRepository.findOne(id);
        person.getBankCards();
        return person;
    }
}
