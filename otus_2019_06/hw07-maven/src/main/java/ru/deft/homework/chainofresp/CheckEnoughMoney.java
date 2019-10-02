package ru.deft.homework.chainofresp;

import ru.deft.homework.actions.impl.AbstractATM;

/*
 * Created by sgolitsyn on 9/28/19
 *
 * Check that enough money for withdraw
 */
public class CheckEnoughMoney extends ChainOfResp {

    private AbstractATM abstractATM;

    public CheckEnoughMoney(AbstractATM abstractATM) {
        this.abstractATM = abstractATM;
    }

    @Override
    public boolean check(int value) {
        if (abstractATM.getBalance() >= value) {
           return checkNext(value);
        }
        System.out.println("Недостаточно средств =)");
        return false;
    }
}
