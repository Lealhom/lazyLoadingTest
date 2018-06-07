package com.omg.lazyLoadingTest.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "lazy_test_identity_card")
public class IdentityCard implements Serializable {

    @Id
    private final String id = UUID.randomUUID().toString();

    @Column
    private String cardNo;
}
