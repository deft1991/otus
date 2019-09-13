package ru.deft.homework.impl;

import ru.deft.homework.constants.BanknoteDenominationRub;
import ru.deft.homework.constants.CachMachineType;

import java.util.Arrays;
import java.util.stream.Collectors;

// todo add some diffs between currency
public class RubATM extends AbstractATM {

    public RubATM() {
        super.type = CachMachineType.RUB;
        super.availableDenomination = Arrays.stream(BanknoteDenominationRub.values())
                                            .collect(Collectors.toMap(BanknoteDenominationRub::getDenomanation, el -> 0));
    }
}
