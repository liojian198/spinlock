package com.bin.lock.spin.reentrant;

import com.bin.lock.spin.ticketlock.TicketLock;

public class ReentrantTicketLocak extends TicketLock {

    private int counts;

    public void lock() {
        Integer currentNum = getTicketOwnNum().get();
        if(getStateNum().get() == currentNum) {
            counts ++;
            return;
        }
        super.lock();
    }

    public void unlock() {
        Integer currentNum = getTicketOwnNum().get();
        if(getStateNum().get() == currentNum) {
            if(counts > 0) {
                counts --;
            } else {
                super.unlock();
            }

        }
    }
}
