package ru.deft.homework.actions.impl;

import ru.deft.homework.actions.CashMachineActions;
import ru.deft.homework.chainofresp.ChainOfResp;
import ru.deft.homework.chainofresp.CheckAvailableBill;
import ru.deft.homework.chainofresp.CheckEnoughMoney;
import ru.deft.homework.constants.CachMachineType;
import ru.deft.homework.department.impl.Department;
import ru.deft.homework.errors.CashMachineException;
import ru.deft.homework.memento.History;
import ru.deft.homework.memento.Memento;
import ru.deft.homework.observer.SubscriberResetState;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.deft.homework.constants.Constants.YOU_HAVEN_T_ENOUGH_MONEY;


public abstract class AbstractATM implements CashMachineActions, SubscriberResetState {

    private History history;
    private Department department;
    private ChainOfResp chainOfResp;
    private final CachMachineType type;
    private Map<Integer, Integer> availableDenomination;

    //todo use cells
    private Map<Integer, Integer> moneyCells = new HashMap<>();

    protected AbstractATM(CachMachineType type, Map<Integer, Integer> availableDenomination, Department department) {
        this.type = type;
        this.availableDenomination = availableDenomination;
        this.history = new History();
        ChainOfResp chainOfResp = new CheckEnoughMoney(this);
        chainOfResp.linkWith(new CheckAvailableBill(this));
        this.chainOfResp = chainOfResp;
        this.department = department;
    }

    @Override
    public void depositCash(int banknoteDenomination, int count) {
        final Integer curCount = Optional.ofNullable(moneyCells.get(banknoteDenomination)).orElse(0);
        moneyCells.put(banknoteDenomination, curCount + count);
        history.push(this, new Memento(this));
    }

    @Override
    public int withdrawCash(int value) {
        if (chainOfResp.check(value)) {
            AtomicInteger amount = getWithdrawAmount(value);
            if (amount.get() == value) {
                System.out.println("You withdraw " + amount + " " + type);
                history.push(this, new Memento(this));
                return amount.get();

            } else {
                history.undo();
                throw new CashMachineException(YOU_HAVEN_T_ENOUGH_MONEY);
            }
        } else {
            throw new CashMachineException(YOU_HAVEN_T_ENOUGH_MONEY);
        }
    }

    private AtomicInteger getWithdrawAmount(int value) {
        final Set<Integer> integers =
                moneyCells.keySet().stream().sorted(Integer::compareTo).collect(Collectors.toCollection(LinkedHashSet::new));
        AtomicInteger amount = new AtomicInteger();
        int[] val = new int[]{value};
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
        return amount;
    }

    @Override
    public int getBalance() {
        final int sum = moneyCells.keySet().stream().mapToInt(key -> moneyCells.get(key) * key).sum();
        System.out.println("Your amount is " + sum + " " + type);
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        return sum;
    }

    @Override
    public Integer checkDenomination(int denomination) {
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

    @Override
    public void initCashMachine(Map<Integer, Integer> baseCash) {
        baseCash.keySet().forEach(key -> {
            Integer denomination = checkDenomination(key);
            depositCash(denomination, baseCash.get(denomination));
        });
        history.push(this, new Memento(this));
    }

    /**
     * Create backup
     */
    public Map<Integer, Integer> createBackup() {
        return moneyCells
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    /**
     * Restore state from history
     */
    public void restore(Map<Integer, Integer> restoreMoneyCells) {
        this.moneyCells = restoreMoneyCells
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Reset state from history
     */
    public void resetState() {
        this.history.resetState();
    }

    /**
     * Get current state from history
     */
    public Map<Integer, Integer> getCurrentState() {
        return history.getCurrentState();
    }

    /**
     * Get base state from history
     */
    private Map<Integer, Integer> getBaseState() {
        return history.getBaseState();
    }

    /**
     * Клиент подаёт готовую цепочку в АТМ. Это увеличивает гибкость и
     * упрощает тестирование класса сервера.
     */
    public void setChainOfResp(ChainOfResp chainOfResp) {
        this.chainOfResp = chainOfResp;
    }

    public Double getResidualAmount() {
        Double[] baseAmount = new Double[0];
        Double[] currentAmount = new Double[0];
        this.getBaseState()
                .entrySet()
                .parallelStream()
                .forEach(entry -> baseAmount[0] += entry.getKey() * entry.getValue());
        this.getCurrentState()
                .entrySet()
                .parallelStream()
                .forEach(entry -> currentAmount[0] += entry.getKey() * entry.getValue());
        return currentAmount[0] - baseAmount[0];

    }
}
