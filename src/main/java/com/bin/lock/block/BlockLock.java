package com.bin.lock.block;

import com.bin.lock.ILock;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.LockSupport;

public class BlockLock implements ILock {

    public static class BlockNode {
        private volatile Thread isLocked;
    }

    private volatile BlockNode tail;
    private static final ThreadLocal <BlockNode> LOCAL = new ThreadLocal<BlockNode>();
    private AtomicReferenceFieldUpdater<BlockLock, BlockNode> UPDATER = AtomicReferenceFieldUpdater.newUpdater(BlockLock.class, BlockNode.class, "tail");

    public void lock() {
        BlockNode node = new BlockNode();
        LOCAL.set(node);
        BlockNode preNode = UPDATER.getAndSet(this, node);

        if(null != preNode) {
            preNode.isLocked = Thread.currentThread();
            LockSupport.park();// 带 this 的接口为什么用不了
            preNode =null;
            LOCAL.set(node);
        }
    }

    public void unlock() {
        BlockNode node = LOCAL.get();
        if (!UPDATER.compareAndSet(this, node, null)) {
            System.out.println("unlock\t" + node.isLocked.getName());
            LockSupport.unpark(node.isLocked);
        }
        node = null;
    }
}
