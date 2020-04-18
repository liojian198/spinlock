package com.bin.lock.spin;

public class CLHLockThread extends Thread{
    private Student st;

    public CLHLockThread(Student st) {
        this.st = st;
    }

    @Override
    public void run() {
        st.add();
    }
}
