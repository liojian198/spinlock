package com.bin.lock.spin.ticketlock;

import com.bin.lock.ILock;

import java.util.concurrent.atomic.AtomicInteger;

public class TicketLock implements ILock {

    //服务号
    private AtomicInteger stateNum = new AtomicInteger();

    //排队号
    private AtomicInteger ticketNum = new AtomicInteger();

    private ThreadLocal <Integer> ticketOwnNum = new ThreadLocal<Integer>();

    public void lock() {
        int currentTicketNum = ticketNum.incrementAndGet();
        ticketOwnNum.set(currentTicketNum);
        while ( currentTicketNum != stateNum.get()){

        }
    }

    public void unlock() {
        Integer currentNum = ticketOwnNum.get();
        stateNum.compareAndSet(currentNum, currentNum + 1);
    }

    public AtomicInteger getStateNum() {
        return stateNum;
    }

    public AtomicInteger getTicketNum() {
        return ticketNum;
    }

    public ThreadLocal<Integer> getTicketOwnNum() {
        return ticketOwnNum;
    }
}
