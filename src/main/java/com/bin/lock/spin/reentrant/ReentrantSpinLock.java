package com.bin.lock.spin.reentrant;

import com.bin.lock.ILock;

import java.util.concurrent.atomic.AtomicReference;

public class ReentrantSpinLock implements ILock {
    //用于表示竞争资源
    private AtomicReference state = new AtomicReference();
    //用于记录重入的次数
    private int counts;
    public void lock() {
        Thread t = Thread.currentThread();
        if(state.get() == state) {
            counts ++;
            return;
        }

        while (!state.compareAndSet(null, t)){

        }
    }

    public void unlock() {
        Thread t = Thread.currentThread();
        if(state.get() == t) {
            if(counts > 0) {
                counts --;
            } else {
                state.compareAndSet(t, null);
            }
        }
    }
}
