package cn.cici.frigate.i18n.manager.jpa.repository;

import cn.cici.frigate.i18n.manager.jpa.entity.ServiceLang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/29 10:45
 * @author: Heyfan Xie
 */
public interface ServiceLangRepository extends JpaRepository<ServiceLang, Long> {

    /**
     * 根据服务名查找所有状态码
     *
     * @param serviceName 服务名
     * @return 状态码集合
     */
    List<ServiceLang> findByServiceName(String serviceName);

    /**
     * 根据语言查找一条记录
     *
     * @param lang 语言
     * @param serviceName 服务名
     * @return 一条状态码
     */
    ServiceLang findByLangAndServiceName(String lang, String serviceName);
}
