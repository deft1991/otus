package ru.deft.homework.chainofresp;

import ru.deft.homework.actions.impl.AbstractATM;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/*
 * Created by sgolitsyn on 9/28/19
 */
public class CheckAvailableBill extends ChainOfResp {

    private final AbstractATM abstractATM;

    public CheckAvailableBill(AbstractATM abstractATM) {
        this.abstractATM = abstractATM;
    }

    @Override
    public boolean check(int value) {
        Map<Integer, Integer> currentState = abstractATM.getCurrentState();
        final Set<Integer> moneyCels = currentState.
                keySet()
                .stream()
                .sorted(Integer::compareTo)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        AtomicInteger amount = new AtomicInteger();

        int[] val = new int[]{value};
        moneyCels.forEach(key -> {
            while (val[0] >= key) {
                if (currentState.get(key) != null && currentState.get(key) - 1 >= 0) {
                    currentState.put(key, currentState.get(key) - 1);
                    amount.addAndGet(key);
                    val[0] -= key;
                } else {
                    break;
                }
            }
        });
        return amount.get() == value;
    }
}
