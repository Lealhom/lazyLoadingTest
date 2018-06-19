package com.omg.lazyLoadingTest.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.MultitenantType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "lazy_test_order_line")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "$$ref" })
@Multitenant(MultitenantType.SINGLE_TABLE)
public class OrderLine {

    @Id
    private final String id = UUID.randomUUID().toString();

    @Column(name = "LINE_NUMBER", nullable = false)
    @NotNull
    private String       lineNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    @JsonBackReference("orderlines")
    private Order order;
}
