package ru.deft.homework.memento;

import ru.deft.homework.actions.CashMachineActions;
import ru.deft.homework.actions.impl.AbstractATM;
import ru.deft.homework.errors.HistoryException;

import java.util.LinkedList;
import java.util.Map;

import static ru.deft.homework.constants.Constants.*;

/*
 * Created by sgolitsyn on 9/28/19
 */
public class History {


    private LinkedList<Pair> history = new LinkedList<>();
    private int virtualSize = 0;

    private class Pair {
        AbstractATM cashMachineActions;
        Memento memento;

        Pair(AbstractATM c, Memento m) {
            cashMachineActions = c;
            memento = m;
        }

        private CashMachineActions getAbstractATM() {
            return cashMachineActions;
        }

        private Memento getMemento() {
            return memento;
        }
    }

    public void push(AbstractATM c, Memento m) {
        history.add(new Pair(c, m));
        virtualSize = history.size();
    }

    public boolean undo() {
        Pair pair = history.poll();
        if (pair == null) {
            return false;
        }
        System.out.println(UNDOING + pair.getAbstractATM().getBalance());
        pair.getMemento().restore();
        virtualSize--;
        return true;
    }

    public Map<Integer, Integer> getCurrentState() {
        Pair pair = history.peek();
        if (pair == null) {
            throw new HistoryException(PAIR_IS_NULL);
        }
        System.out.println(GET_CURRENT_STATE + pair.getMemento().getCurrentState());
        return pair.getMemento().getCurrentState();
    }

    public Map<Integer, Integer> getBaseState() {
        Pair pair = history.peekLast();
        if (pair == null) {
            throw new HistoryException(PAIR_IS_NULL);
        }
        System.out.println(GET_BASE_STATE + pair.getMemento().getCurrentState());
        return pair.getMemento().getCurrentState();
    }

    public boolean resetState() {
        Pair pair = history.getLast();
        if (pair == null) {
            return false;
        }
        System.out.println(RESET_STATE + pair.getAbstractATM().getBalance());
        pair.getMemento().restore();
        virtualSize--;
        return true;
    }
}
