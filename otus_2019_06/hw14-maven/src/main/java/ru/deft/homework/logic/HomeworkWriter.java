package ru.deft.homework.logic;

import java.util.concurrent.locks.Lock;

/*
 * Created by sgolitsyn on 11/12/19
 */
public class HomeworkWriter {

    public static final String THREAD_NAME_S_ATOMIC_INTEGER_D = "Thread name : %s, atomicInteger: %d ";

    private volatile int atomicInteger;
    private boolean isGrow;
    private final Lock lock;
    private final Object monitor;

    public HomeworkWriter(int atomicInteger, Lock lock) {
        this.atomicInteger = atomicInteger;
        this.lock = lock;
        isGrow = true;
        monitor = new Object();
    }

    public void work() {
        System.out.println();
        while (true) {
            if (isGrow) {
                synchronized (monitor) {
                    atomicInteger++;
                    monitor.notifyAll();
                }
                changeValue(10, false);

            } else {
                synchronized (monitor) {
                    atomicInteger--;
                    monitor.notifyAll();
                }
                changeValue(0, true);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void changeValue(int boundary, boolean isGrow) {
        System.out.printf(THREAD_NAME_S_ATOMIC_INTEGER_D, Thread.currentThread().getName(), atomicInteger);
        System.out.println();

        if (boundary == atomicInteger) {
            this.isGrow = isGrow;
        }
    }

    public void print() {
        while (true) {
            synchronized (monitor) {
                System.out.printf(THREAD_NAME_S_ATOMIC_INTEGER_D, Thread.currentThread().getName(), atomicInteger);
                System.out.println();
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
