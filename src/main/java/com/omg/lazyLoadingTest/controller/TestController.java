package com.omg.lazyLoadingTest.controller;

import com.omg.lazyLoadingTest.model.BankCard;
import com.omg.lazyLoadingTest.model.IdentityCard;
import com.omg.lazyLoadingTest.model.Person;
import com.omg.lazyLoadingTest.repository.BankCardRepository;
import com.omg.lazyLoadingTest.repository.IdentityCardRepository;
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

    @Autowired
    private IdentityCardRepository identityCardRepository;

    @Autowired
    private BankCardRepository bankCardRepository;

    @RequestMapping("/save")
    public void save(){
        Person person = new Person();
        IdentityCard identityCard = new IdentityCard();
        identityCard.setCardNo("511524199878945687");

        BankCard bankCard1 = new BankCard();
        bankCard1.setBankName("工商银行");

        BankCard bankCard2 = new BankCard();
        bankCard2.setBankName("农业银行");

        bankCard1.setPerson(person);
        bankCard2.setPerson(person);

        person.getBankCards().add(bankCard1);
        person.getBankCards().add(bankCard2);

        person.setName("张三");
        person.setIdentityCard(identityCard);

        identityCardRepository.save(identityCard);

        personRepository.save(person);

    }



    /**
     * 这里是ResponseBody, 框架会进行序列化，当调用到BeanSerializerBase类的serializeFields方法时，
     * 会继续调用到BeanPropertyWriter类serializeAsField方法，里面会使用反射调用各个属性的get方法
     * 即_accessorMethod.invoke(bean)， 这样就导致Fetch.LAZY的对象也会被查询出来
     *
     */
    @GetMapping(path = "/person1/{id}")
    @ResponseBody
    public Person person1(@PathVariable String id) {
        Person person = personRepository.findOne(id);
        //至此，只会发出一条SQL语句，查询person表的，不会查询lazy对象IdentityCard和BankCard
        return person;
    }

    //这里没有@ResponseBody, 则不会进行序列化，不会转json,所以也不会有查询lazy对象的SQL
    @GetMapping(path = "/person2/{id}")
    public Person person2(@PathVariable String id) {
        Person person = personRepository.findOne(id);
        return person;
    }


    @GetMapping(path = "/person3/{id}")
    public Person person3(@PathVariable String id) {
        Person person = personRepository.findOne(id);
        person.getIdentityCard();//此时还不会查数据库
        person.getIdentityCard().getId();//这时候才会查数据库取IdentityCard,下面同理
        person.getBankCards();
        person.getBankCards().size();
        return person;
    }

    @GetMapping(path = "/idCard/{id}")
    public IdentityCard idCard(@PathVariable String id) {
        IdentityCard identityCard = identityCardRepository.findOne(id);
        return identityCard;
    }
    @GetMapping(path = "/bankCard/{id}")
    public BankCard bankCard(@PathVariable String id) {
        BankCard bankCard = bankCardRepository.findOne(id);
        return bankCard;
    }
}
