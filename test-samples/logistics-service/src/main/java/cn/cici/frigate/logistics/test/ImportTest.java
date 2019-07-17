package cn.cici.frigate.logistics.test;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @description:
 * @createDate:2019/5/30$17:03$
 * @author: Heyfan Xie
 */
@Configuration
@Import({Bean1.class, BeanImportSelector.class, MyImportBeanDeinitionRegistry.class})
public class ImportTest {

}
