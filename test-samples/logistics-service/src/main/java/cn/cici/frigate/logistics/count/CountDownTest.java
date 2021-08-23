package cn.cici.frigate.logistics.count;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 类描述:
 *
 * @version 1.0
 * @date 2020/6/15 16:57
 */
public class CountDownTest {

    private CountDownLatch countDownLatch;

    ExecutorService executorService;

    private int size = 10;

    public void count() {
        countDownLatch = new CountDownLatch(size);
        executorService = Executors.newFixedThreadPool(size);
        for (Person person : getPerson()) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (person.getId()==1) {
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(person);
                    countDownLatch.countDown();
                }
            });
        }
        try {
            System.out.println("执行完成");
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public List<Person> getPerson() {
        List<Person> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add(new Person(i, "zhangsan" + i + Thread.currentThread().getName()));
        }
        return list;
    }
}
