package ru.deft.homework;

import java.io.IOException;

public class MainATM {

    public static void main(String[] args) throws IOException, InterruptedException {
        ATM atm = new ATM();
        atm.startWork();
    }

}
