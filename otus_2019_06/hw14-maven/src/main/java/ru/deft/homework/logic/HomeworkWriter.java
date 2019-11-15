package ru.deft.homework.logic;

import java.util.concurrent.locks.Lock;

/*
 * Created by sgolitsyn on 11/12/19
 */
public class HomeworkWriter {

    public static final String THREAD_NAME_S_ATOMIC_INTEGER_D = "Thread name : %s, atomicInteger: %d ";

    private volatile int atomicInteger;
    private boolean isGrow;
    private boolean isChanged;
    //    private AtomicBoolean isChanged;
    private final Object monitor;

    public HomeworkWriter(int atomicInteger, Lock lock) {
        this.atomicInteger = atomicInteger;
//        this.isChanged = new AtomicBoolean(false);
        this.isChanged = false;
        isGrow = true;
        monitor = new Object();
    }

    public void work() {
        System.out.println();
        while (true) {
            synchronized (monitor) {
                if (isChanged) {
                    System.out.printf(THREAD_NAME_S_ATOMIC_INTEGER_D, Thread.currentThread().getName(), atomicInteger);
                    System.out.println();
                    isChanged = false;
                    monitor.notify();
                } else {

                    if (isGrow) {
                        atomicInteger++;
                        changeValue(10, false);
                    } else {
                        synchronized (monitor) {
                            atomicInteger--;
                        }
                        changeValue(0, true);
                    }
                    isChanged = true;
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
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
