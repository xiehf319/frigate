package cn.cici.frigate.i18n.manager.jpa.repository;

import cn.cici.frigate.i18n.manager.jpa.entity.CodeMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/29 10:46
 * @author: Heyfan Xie
 */
public interface CodeMessageRepository extends JpaRepository<CodeMessage, Long> {

    CodeMessage findByLangIdAndCode(Long langId, String code);

    @Query("select c from CodeMessage c where c.langId in (:ids)")
    List<CodeMessage> findByLangIds(@Param(value = "ids") List<Long> ids);
}
