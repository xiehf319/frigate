package cn.cici.frigate.logistics.dead;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/17 11:30
 * @author: Heyfan Xie
 */
public class DeadLock implements Runnable {

    private int flag;

    private static final Object a = new Object();
    private static final Object b = new Object();

    public DeadLock(int flag) {
        this.flag = flag;
    }

    @Override
    public void run() {

        if (flag == 1) {
            synchronized (a) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("当前线程："+Thread.currentThread().getName()+"尝试获取b");
                synchronized (b) {
                    System.out.println("当前线程："+Thread.currentThread().getName()+"获得了b");
                }
            }
        } else {
            synchronized (b) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("当前线程："+Thread.currentThread().getName()+"尝试获取a");
                synchronized (a) {
                    System.out.println("当前线程："+Thread.currentThread().getName()+"等待获得a");
                }
            }
        }

    }
}
