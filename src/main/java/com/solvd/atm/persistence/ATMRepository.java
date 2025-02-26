package com.solvd.atm.persistence;

import com.solvd.atm.domain.ATMElements.ATM;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ATMRepository {

    List<ATM> getAll();

    ATM getById(@Param("id") Long id);

    void create(ATM atm);
}