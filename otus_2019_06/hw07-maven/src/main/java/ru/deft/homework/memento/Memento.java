package ru.deft.homework.memento;

import ru.deft.homework.actions.impl.AbstractATM;

import java.util.Map;
import java.util.stream.Collectors;

/*
 * Created by sgolitsyn on 9/28/19
 */
public class Memento {
    private Map<Integer, Integer> availableMoneyCellsCopy;
    private AbstractATM abstractATM;

    public Memento(AbstractATM abstractATM) {
        this.abstractATM = abstractATM;
        this.availableMoneyCellsCopy = abstractATM.createBackup();
    }

    public void restore() {
        abstractATM.restore(availableMoneyCellsCopy);
    }

    public Map<Integer, Integer> getCurrentState() {
        return availableMoneyCellsCopy
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
