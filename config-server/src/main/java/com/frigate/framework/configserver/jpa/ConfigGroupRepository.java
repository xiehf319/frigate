package com.frigate.framework.configserver.jpa;

import com.frigate.framework.configserver.entity.ConfigGroup;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @description:
 * @createDate:2019/4/25$14:23$
 * @author: Heyfan Xie
 */
public interface ConfigGroupRepository extends JpaRepository<ConfigGroup, Long> {


}
