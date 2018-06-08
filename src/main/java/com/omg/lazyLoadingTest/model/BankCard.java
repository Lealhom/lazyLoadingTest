package com.omg.lazyLoadingTest.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "lazy_test_bank_card")
public class BankCard implements Serializable {

    @Id
    private final String id = UUID.randomUUID().toString();

    @Column
    private String bankName;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    @JsonBackReference("bankCards")
    private Person person;
}
