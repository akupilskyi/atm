package com.solvd.atm.domain.ATMElements;

public class IndividualBanknote {

    private Long series;
    private BanknoteType banknoteType;

    public Long getSeries() {
        return series;
    }

    public void setSeries(Long series) {
        this.series = series;
    }

    public BanknoteType getBanknoteType() {
        return banknoteType;
    }

    public void setBanknoteType(BanknoteType banknoteType) {
        this.banknoteType = banknoteType;
    }
}