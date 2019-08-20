package ru.deft.homework.impl;

import ru.deft.homework.constants.CachMachineType;

// todo add some diffs between currency
public class UsdATM extends AbstractATM {

    public UsdATM() {
        super.type = CachMachineType.USD;
    }
}
