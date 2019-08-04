package ru.deft.homework.logic;

public class ForTestFirst {
    private int a, b;

    public ForTestFirst(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public int sum(int a, int b){
        return a + b;
    }

    public int minus(int a, int b){
        return a - b;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }
}
