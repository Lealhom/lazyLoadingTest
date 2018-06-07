package com.omg.lazyLoadingTest.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "lazy_test_person")
public class Person implements Serializable {

    @Id
    private final String id = UUID.randomUUID().toString();

    @Column
    private String name;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "identity_card_id")
    private IdentityCard identityCard;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person", orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference("bankCards")
    @Valid
    @Size(min = 1)
    private final List<BankCard> bankCards = new ArrayList<>();
}
