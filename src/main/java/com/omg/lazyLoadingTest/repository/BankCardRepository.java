package com.omg.lazyLoadingTest.repository;

import com.omg.lazyLoadingTest.model.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface BankCardRepository extends JpaRepository<BankCard, String>,
                                 JpaSpecificationExecutor<BankCard> {

}