package cn.cici.frigate.logistics.dead;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/17 11:30
 * @author: Heyfan Xie
 */
public class DeadLockLauncher {

    public static void main(String[] args) {

        final DeadLock deadLock1 = new DeadLock(1);
        final DeadLock deadLock2 = new DeadLock(0);

        Thread t1 = new Thread(deadLock1, "线程1");
        Thread t2 = new Thread(deadLock2, "线程2");
        t1.start();
        t2.start();
    }
}
