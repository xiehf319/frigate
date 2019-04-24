package com.mantis.framework.configserver.jpa;

import com.mantis.framework.configserver.entity.ComponentList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author xiehf
 * @date 2018/12/22 22:52
 * @concat 370693739@qq.com
 **/
public interface TemplatesRepository extends JpaRepository<ComponentList, Long> {

    @Query("select group, key, value from templates where group = ?1")
    List<ComponentList> findByGroup(String group);

    @Query("delete from templates where group = ?1")
    void deleteByGroup(String group);
}
