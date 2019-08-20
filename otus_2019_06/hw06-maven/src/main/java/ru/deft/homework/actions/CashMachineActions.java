package ru.deft.homework.actions;

import ru.deft.homework.constants.BanknoteDenomination;

public interface CashMachineActions {

    void depositCash(BanknoteDenomination banknoteDenomination, int count);

    int withdrawCash(int value);

    int getBalance();
}
