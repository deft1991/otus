package ru.deft.homework.actions.impl;

import ru.deft.homework.constants.BanknoteDenominationRub;
import ru.deft.homework.constants.CachMachineType;
import ru.deft.homework.department.impl.Department;

import java.util.Arrays;
import java.util.stream.Collectors;

// todo add some diffs between currency
public class RubATM extends AbstractATM {

    public RubATM() {
        super(CachMachineType.RUB, Arrays.stream(BanknoteDenominationRub.values())
                .collect(Collectors.toMap(BanknoteDenominationRub::getDenomanation, el -> 0)));
    }
}
