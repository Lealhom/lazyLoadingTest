package com.omg.lazyLoadingTest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.MultitenantType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "lazy_test_copy_customer")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "$$ref" })
@Multitenant(MultitenantType.SINGLE_TABLE)
public class CopyCustomer {

    @Id
    private final String id = UUID.randomUUID().toString();

    @Column(name = "CUSTOMER_NUMBER", nullable = false)
    @NotNull
    private String       customerNumber;

}
