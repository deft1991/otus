package ru.deft.homework.impl;

import ru.deft.homework.constants.BanknoteDenominationUs;
import ru.deft.homework.constants.CachMachineType;

import java.util.Arrays;
import java.util.stream.Collectors;

// todo add some diffs between currency
public class UsdATM extends AbstractATM {

    public UsdATM() {
        super.type = CachMachineType.USD;
        super.availableDenomination = Arrays.stream(BanknoteDenominationUs.values())
                                            .collect(Collectors.toMap(BanknoteDenominationUs::getDenomanation, el -> 0));
    }
}
