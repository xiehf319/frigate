package cn.cici.frigate.finall;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/19 15:13
 * @author: Heyfan Xie
 */
public class FinallTest {

    public static void main(String[] args) {

        Thread thread = new Thread(new DeamanRunner());
        thread.setDaemon(true);
        thread.start();
    }

    static class DeamanRunner implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                System.out.println("hahhhhh");
            }
        }
    }
}
