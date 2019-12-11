package ru.deft.homework.impl;

import ru.deft.homework.actions.CashMachineActions;
import ru.deft.homework.constants.CachMachineType;
import ru.deft.homework.errors.CashMachineException;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.deft.homework.constants.Constants.YOU_HAVEN_T_ENOUGH_MONEY;

public class AbstractATM implements CashMachineActions {

    protected CachMachineType type;
    protected Map<Integer, Integer> availableDenomination;

    //todo use cells
    private Map<Integer, Integer> moneyCells = new HashMap<>();

    @Override public void depositCash(int banknoteDenomination, int count) {
        final Integer curCount = Optional.ofNullable(moneyCells.get(banknoteDenomination)).orElse(0);
        moneyCells.put(banknoteDenomination, curCount + count);
    }

    @Override public int withdrawCash(int value) {
        final Set<Integer> integers =
                moneyCells.keySet().stream().sorted(Integer::compareTo).collect(Collectors.toCollection(LinkedHashSet::new));
        AtomicInteger amount = new AtomicInteger();
        int[] val = new int[] {value};
        integers.forEach(key -> {
            while (val[0] >= key) {
                if (moneyCells.get(key) != null && moneyCells.get(key) - 1 >= 0) {
                    moneyCells.put(key, moneyCells.get(key) - 1);
                    amount.addAndGet(key);
                    val[0] -= key;
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
        final int sum = moneyCells.keySet().stream().mapToInt(key -> moneyCells.get(key) * key).sum();
        System.out.println("Your amount is " + sum + " " + type);
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        return sum;
    }

    @Override public Integer checkDenomination(int denomination) {
        try {
            if (availableDenomination.containsKey(denomination)) {
                return denomination;
            } else {
                throw new CashMachineException(
                        String.format("Unknown banknote denomination type %d. Please try again", denomination));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
