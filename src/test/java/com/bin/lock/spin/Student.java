package com.bin.lock.spin;

import com.bin.lock.spin.CLHLock.CLHLock;

import java.util.concurrent.TimeUnit;

public class Student {
    private int     i = 0;

    private CLHLock lock;

    public Student(CLHLock lock) {
        this.lock = lock;
    }

    public void add() {
        try {
            lock.lock();
            this.i = this.i + 1;
            System.out.println(String.format("线程%s的变量i=%s", Thread.currentThread().getName(), this.i));
            TimeUnit.SECONDS.sleep(5);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}
