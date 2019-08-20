package ru.deft.homework.impl;

import ru.deft.homework.constants.CachMachineType;

// todo add some diffs between currency
public class RubATM extends AbstractATM {

    public RubATM() {
        super.type = CachMachineType.RUB;
    }
}
