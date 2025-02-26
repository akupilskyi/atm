package com.solvd.atm.persistence;

import com.solvd.atm.domain.AccountResources.Transaction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TransactionRepository {

    List<Transaction> getAll();

    Transaction getById(@Param("id") Long id);

    void create(Transaction transaction);
}