package cn.cici.frigate.i18n.manager.service;

import cn.cici.frigate.i18n.manager.cache.StatusCodeCache;
import cn.cici.frigate.i18n.manager.controller.vo.req.ReqServiceLangVo;
import cn.cici.frigate.i18n.manager.jpa.entity.CodeMessage;
import cn.cici.frigate.i18n.manager.jpa.entity.ServiceLang;
import cn.cici.frigate.i18n.manager.jpa.repository.CodeMessageRepository;
import cn.cici.frigate.i18n.manager.jpa.repository.ServiceLangRepository;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/29 10:52
 * @author: Heyfan Xie
 */
@Service
public class StatusCodeService {

    @Autowired
    private ServiceLangRepository serviceLangRepository;

    @Autowired
    private CodeMessageRepository codeMessageRepository;

    @Autowired
    private StatusCodeCache statusCodeCache;

    /**
     * 新增
     *
     * @param codeVo
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(ReqServiceLangVo codeVo) {

        codeVo.getMessageList().forEach(messageVo -> {
            ServiceLang serviceLang = serviceLangRepository.findByLangAndServiceName(messageVo.getLang(), codeVo.getServiceName());
            if (serviceLang == null) {
                serviceLang = new ServiceLang();
                serviceLang.setLang(messageVo.getLang());
                serviceLang.setServiceName(codeVo.getServiceName());
            }
            serviceLang.setLangCN(messageVo.getLangCN());
            serviceLangRepository.saveAndFlush(serviceLang);

            CodeMessage codeMessage = codeMessageRepository.findByLangIdAndCode(serviceLang.getId(), codeVo.getCode());
            if (codeMessage == null) {
                codeMessage = new CodeMessage();
                codeMessage.setLangId(serviceLang.getId());
                codeMessage.setLang(messageVo.getLang());
                codeMessage.setCode(codeVo.getCode());
            }
            codeMessage.setMessage(messageVo.getMessage());
            codeMessageRepository.saveAndFlush(codeMessage);
        });

    }

//    public Map<String, List<CodeMessage>> findByServiceName(String serviceName) {
//        Map<String, List<CodeMessage>> langMap = statusCodeCache.getAll(serviceName);
//        return langMap;
//    }


    public Map<String, LinkedHashMap<String, String>> findByServiceName(String serviceName) {
        List<ServiceLang> serviceLangList = serviceLangRepository.findByServiceName(serviceName);
        if (serviceLangList == null) {
            return Maps.newHashMap();
        }
        List<Long> ids = serviceLangList.stream().map(ServiceLang::getId).collect(Collectors.toList());

        List<CodeMessage> codeMessageList = codeMessageRepository.findByLangIds(ids);
        Map<String, LinkedHashMap<String, String>> map = Maps.newHashMap();

        for (ServiceLang serviceLang : serviceLangList) {
            LinkedHashMap<String, String> subMap = Maps.newLinkedHashMap();
            codeMessageList
                    .stream()
                    .filter(codeMessage -> codeMessage.getLangId().equals(serviceLang.getId()))
                    .forEach(codeMessage -> subMap.put(codeMessage.getCode(), codeMessage.getMessage()));
            map.put(serviceLang.getLang(), subMap);
        }
        return map;
    }
}
