package com.mantis.framework.configserver.jpa;

import com.mantis.framework.configserver.entity.ServiceConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author xiehf
 * @date 2018/12/22 22:52
 * @concat 370693739@qq.com
 **/
public interface PropertiesRepository extends JpaRepository<ServiceConfig, Long> {

    @Query("select key, value from properties where application = ?1")
    List<ServiceConfig> findByApplication(String application);

    @Query("select key, value from properties where application = ?1 and profile = ?2")
    List<ServiceConfig> findByApplicationAndProfile(String application, String profile);

    @Query("update properties set value = ?2 where key = ?1")
    void updateValue(String key, String value);
}
