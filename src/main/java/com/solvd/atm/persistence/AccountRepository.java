package com.solvd.atm.persistence;

import com.solvd.atm.domain.AccountResources.Account;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface AccountRepository {

    List<Account> getAll();

    Account getById(@Param("id") Integer id);

    void create(Account account);

    void updateBalance(@Param("accountId") Integer accountId, @Param("newBalance") BigDecimal newBalance);
}