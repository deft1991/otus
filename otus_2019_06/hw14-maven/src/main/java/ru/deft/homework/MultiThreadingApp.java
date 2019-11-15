package ru.deft.homework;

import ru.deft.homework.logic.HomeworkWriter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/*
 * Created by sgolitsyn on 11/10/19
 */
public class MultiThreadingApp {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        HomeworkWriter homeworkLogic = new HomeworkWriter(0, new ReentrantLock());

        executorService.submit(homeworkLogic::work);
        executorService.submit(homeworkLogic::work);

        executorService.shutdown();
        executorService.awaitTermination(60, TimeUnit.SECONDS);
    }
}
