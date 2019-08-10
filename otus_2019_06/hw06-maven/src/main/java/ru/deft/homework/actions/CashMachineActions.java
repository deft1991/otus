package ru.deft.homework.actions;

public interface CashMachineActions {

    void depositCash(int cellValue, int count);

    int withdrawCash(int value);

    int getBalance();
}
