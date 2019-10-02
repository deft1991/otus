package ru.deft.homework.department;

import ru.deft.homework.observer.SubscriberResetState;

/*
 * Created by sgolitsyn on 9/28/19
 */
public interface DepartmentActions {

    Double getAmountOfBalances();

    void resetCashMachineState();

    void addSubscriber(SubscriberResetState abstractATM);

    void unSubscribe(SubscriberResetState abstractATM);
}
