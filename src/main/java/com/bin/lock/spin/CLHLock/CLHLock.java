package com.bin.lock.spin.CLHLock;

import com.bin.lock.ILock;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class CLHLock implements ILock {

    public static class CLHNode {
        private volatile boolean isLocked =true;
    }

    @SuppressWarnings("unused")
    private volatile CLHNode tail;

    private static final ThreadLocal <CLHNode> LOCAL = new ThreadLocal <CLHNode>();

    private static final AtomicReferenceFieldUpdater <CLHLock, CLHNode> UPDATER = AtomicReferenceFieldUpdater.newUpdater(CLHLock.class, CLHNode.class, "tail");

    public void lock() {
        //// 新建节点并将节点与当前线程保存起来
        CLHNode node = new CLHNode();
        LOCAL.set(node);
        //将新建的节点设置为尾部节点，并返回旧的节点（原子操作），这里旧的节点实际上就是当前节点的前驱节点
        CLHNode preNode = UPDATER.getAndSet(this, node);

        if(null != preNode) {
            while(preNode.isLocked){

            }
            preNode = null;
            LOCAL.set(node); // ? 有必要？
        }
        //// 如果不存在前驱节点，表示该锁没有被其他线程占用，则当前线程获得锁

    }

    public void unlock() {
        CLHNode clhNode = LOCAL.get();
        if(!UPDATER.compareAndSet(this, clhNode, null)) { //判断是否是最后一个申请者
            clhNode.isLocked = false;
        }
        clhNode = null;
    }
}
