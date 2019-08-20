package ru.deft.homework.impl;

import ru.deft.homework.actions.CashMachineActions;
import ru.deft.homework.constants.BanknoteDenomination;
import ru.deft.homework.constants.CachMachineType;
import ru.deft.homework.errors.CashMachineException;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.deft.homework.constants.Constants.YOU_HAVEN_T_ENOUGH_MONEY;

public class AbstractATM implements CashMachineActions {

    CachMachineType type;

    //todo use cells
    private Map<BanknoteDenomination, Integer> moneyCells = new HashMap<>();

    @Override public void depositCash(BanknoteDenomination banknoteDenomination, int count) {
        final Integer curCount = Optional.ofNullable(moneyCells.get(banknoteDenomination)).orElse(0);
        moneyCells.put(banknoteDenomination, curCount + count);
    }

    @Override public int withdrawCash(int value) {
        final Set<BanknoteDenomination> integers =
                moneyCells.keySet().stream().sorted(Comparator.comparingInt(BanknoteDenomination::getDenomanation))
                          .collect(Collectors.toCollection(LinkedHashSet::new));
        AtomicInteger amount = new AtomicInteger();
        int[] val = new int[] {value};
        integers.forEach(key -> {
            while (val[0] >= key.getDenomanation()) {
                if (moneyCells.get(key) != null && moneyCells.get(key) - 1 >= 0) {
                    moneyCells.put(key, moneyCells.get(key) - 1);
                    amount.addAndGet(key.getDenomanation());
                    val[0] -= key.getDenomanation();
                } else {
                    break;
                }
            }
        });
        if (amount.get() == value) {
            System.out.println("You withdraw " + amount + " " + type);
            return amount.get();

        }
        throw new CashMachineException(YOU_HAVEN_T_ENOUGH_MONEY);
    }

    @Override public int getBalance() {
        final int sum = moneyCells.keySet().stream().mapToInt(key -> moneyCells.get(key) * key.getDenomanation()).sum();
        System.out.println("Your amount is " + sum + " " + type);
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        return sum;
    }

}
