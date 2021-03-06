package cn.cici.frigate.config.server.jpa;

import cn.cici.frigate.config.server.entity.ConfigGroupDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author xiehf
 * @date 2018/12/22 22:52
 * @concat 370693739@qq.com
 **/
public interface ConfigGroupDetailRepository extends JpaRepository<ConfigGroupDetail, Long> {

    @Query("select groupName, keyName, value from ConfigGroupDetail where groupName = ?1")
    List<ConfigGroupDetail> findByGroup(String group);

    @Query("delete from ConfigGroupDetail where groupName = ?1")
    int deleteByGroup(String group);
}
