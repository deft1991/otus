package ru.deft.homework.constants;

public enum BanknoteDenominationRub {
    FIVE_THOUSAND(5000), TWO_THOUSAND(2000), ONE_THOUSAND(1000), FIVE_HUNDRED(500), TWO_HUNDRED(200), ONE_HUNDRED(100);

    int denomination;

    BanknoteDenominationRub(int denomination) {
        this.denomination = denomination;
    }

    public int getDenomanation() {
        return denomination;
    }
}
