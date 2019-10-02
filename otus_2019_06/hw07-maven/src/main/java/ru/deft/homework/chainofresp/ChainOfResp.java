package ru.deft.homework.chainofresp;

/*
 * Created by sgolitsyn on 9/28/19
 */
public abstract class ChainOfResp {
    private ChainOfResp next;

    /**
     * Помогает строить цепь из объектов-проверок.
     */
    public ChainOfResp linkWith(ChainOfResp next) {
        this.next = next;
        return next;
    }
    /**
     * Подклассы реализуют в этом методе конкретные проверки.
     */
    public abstract boolean check(int value);

    /**
     * Запускает проверку в следующем объекте или завершает проверку, если мы в
     * последнем элементе цепи.
     */
    protected boolean checkNext(int value) {
        if (next == null) {
            return true;
        }
        return next.check(value);
    }
}
