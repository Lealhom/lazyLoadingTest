package com.omg.lazyLoadingTest.repository;

import com.omg.lazyLoadingTest.model.OrderLine;
import com.omg.lazyLoadingTest.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface OrderLineRepository extends JpaRepository<OrderLine, String>,
                                 JpaSpecificationExecutor<OrderLine> {

}