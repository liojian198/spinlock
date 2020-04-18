package com.bin.lock.spin.MCSLock;

import com.bin.lock.ILock;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class MCSLock implements ILock {

    public static class MCSNode {
        volatile MCSNode next;
        volatile  boolean isLocked =true;
    }

    private static final ThreadLocal <MCSNode> NODE = new ThreadLocal<MCSNode>();
    private volatile MCSNode queue;
    private static final AtomicReferenceFieldUpdater<MCSLock, MCSNode> UPDATER = AtomicReferenceFieldUpdater.newUpdater(MCSLock.class, MCSNode.class, "queue");

    public void lock() {
        //// 创建节点并保存到ThreadLocal中
        MCSNode node = new MCSNode();
        NODE.set(node);
        // 将queue设置为当前节点，并且返回之前的节点
        MCSNode preNode = UPDATER.getAndSet(this, node);

        if(null != preNode) {
            // 如果之前节点不为null，表示锁已经被其他线程持有
            preNode.next = node;

            // 循环判断，直到当前节点的锁标志位为false
            while(node.isLocked) {

            }

        }
    }

    public void unlock() {
        MCSNode currentNode = NODE.get();

        if(null == currentNode.next) {// 1. 判断是否有直接后继者
            //更新状态并设置queue为null
            if (UPDATER.compareAndSet(this, currentNode, null)) {//2. 判断是否是最后一个申请者
                    return;
            } else {
                // 如果不成功，表示queue!=currentNode,即当前节点后面多了一个节点，表示有线程在等待
                // 由于1，2操作不是原子操作，所以在1，2之间可能加入节点。
                // 如果当前节点的后续节点为null，则需要等待其不为null（参考加锁方法）
                while(currentNode.next == null){

                }
            }

        } else {
            //如果不为null，表示有线程在等待获取锁，此时将等待线程对应的节点锁状态更新为false，同时将当前线程的后继节点设为null
            currentNode.next.isLocked = false;
            currentNode.next = null;
        }
    }
}

