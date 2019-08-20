package ru.deft.homework.constants;

public enum BanknoteDenomination {
    FIVE_THOUSAND(5000), ONE_THOUSAND(1000), FIVE_HUNDRED(500), ONE_HUNDRED(100);

    int denomination;

    BanknoteDenomination(int denomination) {
        this.denomination = denomination;
    }

    public int getDenomanation() {
        return denomination;
    }

}
