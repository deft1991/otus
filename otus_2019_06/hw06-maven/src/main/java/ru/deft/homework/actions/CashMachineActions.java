package ru.deft.homework.actions;

public interface CashMachineActions {

    void depositCash(int banknoteDenomination, int count);

    int withdrawCash(int value);

    int getBalance();

    Integer checkDenomination(int denomination);
}
