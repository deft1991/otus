package ru.deft.homework.threads;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * Created by sgolitsyn on 11/10/19
 */
public class Worker implements Runnable {

    public static final String THREAD_NAME_S_ATOMIC_INTEGER_D = "Thread name : %s, atomicInteger: %d ";
//    public static final String THREAD_NAME_S_ATOMIC_INTEGER_D = " %d ";
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    private AtomicBoolean isChanged = new AtomicBoolean(false);
    private boolean isGrow = true;

    @Override
    public void run() {
        System.out.println();
        while (true) {

            if (isGrow) {
                work(atomicInteger.get() == 10, false, atomicInteger.incrementAndGet());
            } else {
                work(atomicInteger.get() == 0, true,  atomicInteger.decrementAndGet());
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread.yield();
        }
    }

    private void work(final boolean isGreater, final boolean grow, final int value) {
        if (isChanged.get()) {
            System.out.printf(THREAD_NAME_S_ATOMIC_INTEGER_D, Thread.currentThread().getName(), atomicInteger.get());
//            System.out.printf(THREAD_NAME_S_ATOMIC_INTEGER_D, atomicInteger.get());
            System.out.println();
            isChanged.set(false);
        } else {
            if (isGreater) {
                isGrow = grow;
                if (grow){
                    atomicInteger.incrementAndGet();
                }else {
                    atomicInteger.decrementAndGet();
                }
                return;
            }

            System.out.printf(THREAD_NAME_S_ATOMIC_INTEGER_D, Thread.currentThread().getName(), value);
//            System.out.printf(THREAD_NAME_S_ATOMIC_INTEGER_D, value);
            System.out.println();
        }
    }
}
