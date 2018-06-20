package com.omg.lazyLoadingTest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.MultitenantType;

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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "lazy_test_order")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "$$ref" })
@Multitenant(MultitenantType.SINGLE_TABLE)
public class Order {

    @Id
    private String id = UUID.randomUUID().toString();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COPY_CUSTOMER_ID")
    private CopyCustomer               copyCustomer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference("orderlines")
    @Valid
    @Size(min = 1)
    private List<OrderLine> orderLines = new ArrayList<>();
}
