package com.solvd.atm.persistence;

import com.solvd.atm.domain.ATMElements.ATM;
import com.solvd.atm.domain.AccountResources.Card;
import com.solvd.atm.domain.AccountResources.Transaction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TransactionRepository {

    List<Transaction> getAll();

    Transaction getById(@Param("id") Long id);

    void create(@Param("transaction") Transaction transaction, @Param("card") Card card, @Param("atm") ATM atm);
}