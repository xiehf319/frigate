package cn.cici.frigate.id;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author xiehf
 * @date 2019/5/14 23:26
 * @concat 370693739@qq.com
 **/
@SpringBootTest(classes = IdProviderApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class UidGeneratorTest {

    @Autowired
    private UidGenerator cachedUidGenerator;

    @Test
    public void testUID() throws Exception {
        System.out.println(cachedUidGenerator.getUID());
    }
}
