package ru.deft.homework.actions.impl;

import ru.deft.homework.constants.BanknoteDenominationUs;
import ru.deft.homework.constants.CachMachineType;

import java.util.Arrays;
import java.util.stream.Collectors;

// todo add some diffs between currency
public class UsdATM extends AbstractATM {

    public UsdATM() {
        super(CachMachineType.USD, Arrays.stream(BanknoteDenominationUs.values())
                .collect(Collectors.toMap(BanknoteDenominationUs::getDenomanation, el -> 0)));
    }
}
