package cn.cici.frigate.config.server.jpa;

import cn.cici.frigate.config.server.entity.ServiceConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author xiehf
 * @date 2018/12/22 22:52
 * @concat 370693739@qq.com
 **/
public interface PropertiesRepository extends JpaRepository<ServiceConfig, Long> {

    @Query("select keyName, value from ServiceConfig where application = ?1")
    List<ServiceConfig> findByApplication(String application);

    @Query("select keyName, value from ServiceConfig where application = ?1 and profile = ?2")
    List<ServiceConfig> findByApplicationAndProfile(String application, String profile);

    @Modifying
    @Query("update ServiceConfig set value = ?2 where keyName = ?1")
    int updateValue(String keyName, String value);
}
