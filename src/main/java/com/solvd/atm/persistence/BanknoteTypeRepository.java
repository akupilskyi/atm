package com.solvd.atm.persistence;

import com.solvd.atm.domain.ATMElements.BanknoteType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BanknoteTypeRepository {

    List<BanknoteType> getAll();

    BanknoteType getById(@Param("id") Integer id);

    void create(BanknoteType banknoteType);
}