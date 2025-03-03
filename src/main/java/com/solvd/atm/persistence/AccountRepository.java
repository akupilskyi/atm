package com.solvd.atm.persistence;

import com.solvd.atm.domain.AccountResources.Account;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface AccountRepository {

    List<Account> getAll();

    Account getById(@Param("id") Long id);

    void create(Account account);

    void updateBalance(@Param("accountId") Long accountId, @Param("newBalance") BigDecimal newBalance);

    void updateLocked(@Param("accountId") Long accountId, @Param("booleanValue") boolean booleanValue);

    Account getAccountByCardNumber(@Param("cardNumber") String cardNumber);
}