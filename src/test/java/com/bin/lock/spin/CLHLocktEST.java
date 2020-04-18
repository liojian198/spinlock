package com.bin.lock.spin;

import com.bin.lock.spin.CLHLock.CLHLock;

import java.util.concurrent.TimeUnit;

public class CLHLocktEST {
    public static void main(String[] args) throws InterruptedException {
        Student st = new Student(new CLHLock());
        for (int i = 0; i < 100; i++) {
            new Thread(new CLHLockThread(st), i + "").start();
        }
        TimeUnit.SECONDS.sleep(30);
    }
}
