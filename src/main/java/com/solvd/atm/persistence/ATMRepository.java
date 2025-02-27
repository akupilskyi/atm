package com.solvd.atm.persistence;

import com.solvd.atm.domain.ATMElements.ATM;
import com.solvd.atm.domain.ATMElements.Status;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ATMRepository {

    List<ATM> getAll();

    ATM getById(@Param("id") Long id);

    void create(ATM atm);

    void updateStatus(@Param("atmId") Long atmId, @Param("newStatus") Status newStatus);

    void insertBanknotes(@Param("atmId") Long atmId, @Param("banknoteTypeId") Long banknoteTypeId, @Param("quantity") Integer quantity);

    void updateBanknotes(@Param("atmId") Long atmId, @Param("banknoteTypeId") Long banknoteTypeId, @Param("quantity") Integer quantity);
}