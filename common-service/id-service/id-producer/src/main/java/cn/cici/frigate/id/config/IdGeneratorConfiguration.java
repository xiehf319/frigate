package cn.cici.frigate.id.config;

import cn.cici.frigate.id.UidGenerator;
import cn.cici.frigate.id.impl.CachedUidGenerator;
import cn.cici.frigate.id.worker.DisposableWorkerIdAssigner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiehf
 * @date 2019/5/14 23:22
 * @concat 370693739@qq.com
 **/
@Configuration
public class IdGeneratorConfiguration {

    @Bean
    public DisposableWorkerIdAssigner disposableWorkerIdAssigner() {
        return new DisposableWorkerIdAssigner();
    }

    @Bean("uidGenerator")
    public UidGenerator cachedUidGenerator(DisposableWorkerIdAssigner disposableWorkerIdAssigner) {
        CachedUidGenerator cachedUidGenerator = new CachedUidGenerator();
        cachedUidGenerator.setWorkerIdAssigner(disposableWorkerIdAssigner);
        return cachedUidGenerator;
    }
}
