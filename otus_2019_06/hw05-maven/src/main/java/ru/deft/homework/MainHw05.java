package ru.deft.homework;

import java.util.ArrayList;
import java.util.List;

/**
 * -XX:+UseG1Gc
 * -Xms512m
 * -Xmx512m
 * -Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
 * -XX:+HeapDumpOnOutOfMemoryError
 * -XX:HeapDumpPath=./logs/dump
 * <p>
 * without thread.sleep
 * -Xms2048m
 * -Xmx2048m
 * time: 21 sec
 * Pause Young (Normal) (G1 Evacuation Pause) 1128M->926M(2048M) 64.588ms
 * <p>
 *
 * <p>
 * with thread.sleep(10)
 * -Xms2048m
 * -Xmx2048m
 * <p>
 * time:  142 sec
 * Pause Young (Normal) (G1 Evacuation Pause) 1259M->1087M(2048M) 54.111ms
 * <p>
 *
 *
 * <p>
 * -XX:MaxGCPauseMillis=100000
 * without thread.sleep
 * -Xms2048m
 * -Xmx2048m
 * <p>
 * time:  143 sec
 * Pause Young (Concurrent Start) (G1 Evacuation Pause) 1353M->1204M(2048M) 53.091ms
 *
 * <p>
 * -XX:MaxGCPauseMillis=10
 * without thread.sleep
 * -Xms2048m
 * -Xmx2048m
 * <p>
 * time:  144 sec
 * Pause Young (Normal) (G1 Evacuation Pause) 1007M->965M(2048M) 7.849ms -- memory keeps up 950+- MB
 *
 * <p>
 * without thread.sleep
 * -Xms512m
 * -Xmx512m
 * 506799
 * java.lang.OutOfMemoryError: Java heap space
 * clean when heavyList.size() == 1000
 * time: 130 sec
 * Pause Young (Normal) (G1 Evacuation Pause) 329M->41M(512M) 3.011ms
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * ----------------------------------------------------
 * * <p>
 * -XX:MaxGCPauseMillis=100000
 * without thread.sleep
 * -Xms512m
 * -Xmx512m
 * Pause Young (Normal) (G1 Evacuation Pause) 260M->162M(512M) 139.943ms
 * time: 66 sed
 * <p>
 * -XX:MaxGCPauseMillis=10
 * without thread.sleep
 * -Xms512m
 * -Xmx512m
 * Pause Young (Normal) (G1 Evacuation Pause) 222M->214M(512M) 9.938ms
 * time: 73sec
 * <p> *
 * <p>
 * <p>
 * <p>
 * <p>
 * * <p>
 * -XX:MaxGCPauseMillis=100000
 * with thread.sleep
 * -Xms512m
 * -Xmx512m
 * Pause Young (Normal) (G1 Evacuation Pause) 260M->162M(512M) 97.058ms
 * time: 198 sed
 * <p>
 * -XX:MaxGCPauseMillis=10
 * with thread.sleep
 * -Xms512m
 * -Xmx512m
 * Pause Young (Normal) (G1 Evacuation Pause) 223M->216M(512M) 8.947ms
 * time: 203sec
 * <p>-------------------------------------------------
 * -XX:+UseSerialGC
 * <p>
 * -XX:MaxGCPauseMillis=10
 * without thread.sleep
 * -Xms512m
 * -Xmx512m
 * Pause Young (Allocation Failure) 199M->136M(494M) 205.039ms
 * time: 53 sed
 * <p>
 * -XX:MaxGCPauseMillis=100000
 * without thread.sleep
 * -Xms512m
 * -Xmx512m
 * Pause Young (Allocation Failure) 199M->136M(494M) 205.039ms
 * time: 54 sed
 * <p>
 * <p>
 * -XX:MaxGCPauseMillis=100000
 * with thread.sleep
 * -Xms512m
 * -Xmx512m
 * Pause Young (Allocation Failure) 199M->136M(494M) 198.739ms
 * time: 196 sed
 * <p>
 * -XX:MaxGCPauseMillis=10
 * with thread.sleep
 * -Xms512m
 * -Xmx512m
 * Pause Young (Normal) (G1 Evacuation Pause) 223M->216M(512M) 8.947ms
 * time: 194sec
 * <p>-------------------------------------------------
 * -XX:+UseParallelGC
 * <p>
 * -XX:MaxGCPauseMillis=10
 * without thread.sleep
 * -Xms512m
 * -Xmx512m
 * Pause Young (Allocation Failure) 199M->136M(494M) 205.039ms
 * time: 53 sed
 * <p>
 * -XX:MaxGCPauseMillis=100000
 * without thread.sleep
 * -Xms512m
 * -Xmx512m
 * Pause Young (Allocation Failure) 199M->136M(494M) 205.039ms
 * time: 56 sed
 * <p>
 * <p>
 * -XX:MaxGCPauseMillis=100000
 * with thread.sleep
 * -Xms512m
 * -Xmx512m
 * Pause Young (Allocation Failure) 258M->200M(491M) 67.535ms
 * time: 195 sed
 * <p>
 * -XX:MaxGCPauseMillis=10
 * with thread.sleep
 * -Xms512m
 * -Xmx512m
 * Pause Young (Normal) (G1 Evacuation Pause) 223M->216M(512M) 8.947ms
 * time: 197sec
 * <p>-------------------------------------------------
 * <p>
 */
public class MainHw05 {

    private static List<Heavy> heavyList = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        final long currentTimeMillis = System.currentTimeMillis();
        for (int i = 1; i < 1_000_000; i++) {
            System.out.println(i);
            heavyList.add(new Heavy());
            heavyList.add(new Heavy());

            if (i >= 1000 && i % 100 == 0) {
                for (int j = 0; j < 25; j++) { // if set 90

                    Heavy remove = heavyList.remove(j);
                    remove = new Heavy();
                    //                    Heavy heavy2 = heavyList.remove(2);
                    //                    heavy2 = null;
                    //                    Heavy heavy3 = heavyList.remove(3);
                    //                    heavy3 = null;
                    //                    Heavy heavy4 = heavyList.remove(4);
                    //                    heavy4 = null;
                }
                Thread.sleep(10);
            }
            //            if (heavyList.size() == 50000) {
            //                for (int j = 0; j < heavyList.size() / 2; j++) {
            //                    Heavy remove = heavyList.remove(j);
            //                    remove = new Heavy();
            //                }
            //
            //            }
        }
        final long x = System.currentTimeMillis() - currentTimeMillis;
        final long x1 = x / 1000;
        System.out.println(x1);
    }
}
