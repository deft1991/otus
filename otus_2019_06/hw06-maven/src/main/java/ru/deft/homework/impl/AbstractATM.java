package ru.deft.homework.impl;

import ru.deft.homework.actions.CashMachineActions;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class AbstractATM implements CashMachineActions {

    String type = "Abstract";

    //todo use cells
    private Map<Integer, Integer> rubCells = new HashMap<>();

    @Override public void depositCash(int cellValue, int count) {
        final Integer curCount = Optional.ofNullable(rubCells.get(cellValue)).orElse(0);
        rubCells.put(cellValue, curCount + count);
    }

    @Override public int withdrawCash(int value) {
        final Set<Integer> integers = rubCells.keySet().stream().sorted().collect(Collectors.toCollection(LinkedHashSet::new));
        AtomicInteger amount = new AtomicInteger();
        int[] val = new int[] {value};
        integers.forEach(key -> {
            while (val[0] >= key) {
                if (rubCells.get(key) != null) {
                    rubCells.put(key, rubCells.get(key) - 1);
                    amount.addAndGet(key);
                    val[0] -= key;
                } else {
                    break;
                }
            }
        });
        System.out.println("You withdraw " + amount + " " + type);
        return amount.get();
    }

    @Override public int getBalance() {
        final int sum = rubCells.keySet().stream().mapToInt(key -> rubCells.get(key) * key).sum();
        System.out.println("Your amount is " + sum + " " + type);
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        return sum;
    }

}
