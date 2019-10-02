package ru.deft.homework.department.impl;

import ru.deft.homework.department.DepartmentActions;
import ru.deft.homework.observer.SubscriberResetState;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by sgolitsyn on 9/28/19
 */
public class Department implements DepartmentActions {
    private List<SubscriberResetState> atmList = new ArrayList<>();


    @Override
    public Double getAmountOfBalances() {
        return atmList
                .parallelStream()
                .map(SubscriberResetState::getResidualAmount)
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    @Override
    public void resetCashMachineState() {
        atmList.parallelStream().forEach(SubscriberResetState::resetState);
    }

    @Override
    public void addSubscriber(SubscriberResetState subscriber) {
        atmList.add(subscriber);
    }

    public void unSubscribe(SubscriberResetState subscriber) {
        atmList.remove(subscriber);
    }
}
