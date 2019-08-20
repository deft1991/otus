package ru.deft.homework.impl;

import ru.deft.homework.annotation.Log;
import ru.deft.homework.interfaces.PrintInterface;

public class PrintBye implements PrintInterface {

    @Override public void print() {
        System.out.println("Bye bye");
    }

    @Override @Log public void print(String text) {
        System.out.println("Bye " + text);
    }
}
