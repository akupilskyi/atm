package com.solvd.atm.persistence;

import com.solvd.atm.domain.AccountResources.Account;
import com.solvd.atm.domain.AccountResources.Card;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CardRepository {

    List<Card> getAll();

    Card getById(@Param("id") Long id);

    void create(@Param("card") Card card, @Param("account") Account account);

    void updatePin(@Param("cardId") Long cardId, @Param("newPin") Integer newPin);
}