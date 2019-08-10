package ru.deft.homework;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;

import com.google.common.util.concurrent.AtomicDouble;
import com.google.common.util.concurrent.AtomicDoubleArray;

public class Heavy {

   private AtomicBoolean atomicBoolean = new AtomicBoolean();
   private AtomicInteger atomicInteger = new AtomicInteger();
//   private AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(1_000);
   private AtomicLong atomicLong = new AtomicLong();
//   private AtomicLongArray atomicLongArray = new AtomicLongArray(1_000);
   private AtomicDouble atomicDouble = new AtomicDouble();
//   private AtomicDoubleArray atomicDoubleArray = new AtomicDoubleArray(1_000);

    public AtomicBoolean getAtomicBoolean() {
        return atomicBoolean;
    }

    public void setAtomicBoolean(AtomicBoolean atomicBoolean) {
        this.atomicBoolean = atomicBoolean;
    }

    public AtomicInteger getAtomicInteger() {
        return atomicInteger;
    }

    public void setAtomicInteger(AtomicInteger atomicInteger) {
        this.atomicInteger = atomicInteger;
    }
//
//    public AtomicIntegerArray getAtomicIntegerArray() {
//        return atomicIntegerArray;
//    }
//
//    public void setAtomicIntegerArray(AtomicIntegerArray atomicIntegerArray) {
//        this.atomicIntegerArray = atomicIntegerArray;
//    }

    public AtomicLong getAtomicLong() {
        return atomicLong;
    }

    public void setAtomicLong(AtomicLong atomicLong) {
        this.atomicLong = atomicLong;
    }

//    public AtomicLongArray getAtomicLongArray() {
//        return atomicLongArray;
//    }
//
//    public void setAtomicLongArray(AtomicLongArray atomicLongArray) {
//        this.atomicLongArray = atomicLongArray;
//    }

    public AtomicDouble getAtomicDouble() {
        return atomicDouble;
    }

    public void setAtomicDouble(AtomicDouble atomicDouble) {
        this.atomicDouble = atomicDouble;
    }

//    public AtomicDoubleArray getAtomicDoubleArray() {
//        return atomicDoubleArray;
//    }
//
//    public void setAtomicDoubleArray(AtomicDoubleArray atomicDoubleArray) {
//        this.atomicDoubleArray = atomicDoubleArray;
//    }
}
