package com.omg.lazyLoadingTest.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;


//不要用@ToString,也不要用@Data(因为这个就包含了ToString), 只要有ToString，fetech lazy 就会失效
//@Data这个注解会附带equals()、hashCode()、toString()， 而Lombok toString()会调用属性的get方法，导致lazy loading失败
//但不知道为何会调到toString， 我重写了toString方法，打断点调试，执行bankCardRepository.findOne(id);并不会调到toString方法的
@Getter
@Setter
@Entity
@Table(name = "lazy_test_bank_card")
public class BankCard implements Serializable {

    @Id
    private final String id = UUID.randomUUID().toString();

    @Column(name = "bank_name")
    private String bankName;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    @JsonBackReference("bankCards")
    private Person person;

}
