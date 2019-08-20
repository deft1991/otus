package ru.deft.homework.impl;

import ru.deft.homework.annotation.Log;
import ru.deft.homework.interfaces.PrintInterface;

public class PrintHello implements PrintInterface {

    @Override @Log public void print() {
        System.out.println("Hello world!");
    }

    @Override public void print(String text) {
        System.out.println(String.format("Hello world %s", text));
    }
}
