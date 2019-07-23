package cn.cici.frigate.finall;

import static org.junit.Assert.assertEquals;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/23 9:49
 * @author: Heyfan Xie
 */
public class Test1 {
   static int number = 0;
   static volatile Error error;
   static volatile Exception ex;
    public static void main(String[] args) throws InterruptedException{


        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    assertEquals(2, number);
                } catch (Error e) {
                    error = e;
                } catch (Exception e) {
                    ex = e;
                }
            }
        });
        number = 1;
        th.start();
        number++;
        th.join();
    }
}
