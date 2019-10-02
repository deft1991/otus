package ru.deft.homework.actions;

import java.util.Map;

public interface CashMachineActions {

    /**
     * @param banknoteDenomination - denomination of bancnote
     * @param count                - banknotes count
     */
    void depositCash(int banknoteDenomination, int count);

    /**
     * @param value - count cash for withdraw
     * @return - withdraw cash
     */
    int withdrawCash(int value);

    /**
     * @return user balance
     */
    int getBalance();

    /**
     * @param denomination check that atm contains denomination
     * @return - denomination
     */
    Integer checkDenomination(int denomination);

    /**
     * @param baseCash - base cash for init CashMachine
     */
    void initCashMachine(Map<Integer, Integer> baseCash);

    /**
     * reset state to base state
     */
    void resetState();
}
