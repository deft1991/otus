package ru.deft.homework.test;

import ru.deft.homework.annotation.Before;
import ru.deft.homework.annotation.Test;
import ru.deft.homework.logic.ForTestFirst;

public class TestFirst {

    private ForTestFirst forTestFirst;

    @Before
    public void init(){
        forTestFirst = new ForTestFirst((int) (Math.random() * 10), (int) (Math.random() * 10));
    }

    @Test
    public int sum(int a, int b){
        return a + b;
    }

    @Test
    public int sumRand(){
        return forTestFirst.getA() + forTestFirst.getB();
    }

    @Test
    public int minus(int a, int b){
        return a - b;
    }

}
