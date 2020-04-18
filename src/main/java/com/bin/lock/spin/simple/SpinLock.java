package com.bin.lock.spin.simple;


import com.bin.lock.ILock;

import java.util.concurrent.atomic.AtomicReference;

public class SpinLock implements ILock {

    private AtomicReference state = new AtomicReference();
    public void lock() {
        Thread t = Thread.currentThread();
        while (!state.compareAndSet(null, t)){

        }
    }

    public void unlock() {
        Thread t = Thread.currentThread();
        state.compareAndSet(t, null);
    }
}
