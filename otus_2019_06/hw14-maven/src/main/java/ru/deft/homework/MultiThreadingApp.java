package ru.deft.homework;

import ru.deft.homework.threads.Worker;

/*
 * Created by sgolitsyn on 11/10/19
 */
public class MultiThreadingApp {

    public static void main(String[] args) {
        Thread thread1 = new Thread(new Worker());
        thread1.setName("tr1");

        Thread thread2 = new Thread(new Worker());
        thread2.setName("tr2");

        thread1.start();
        thread2.start();
    }
}
