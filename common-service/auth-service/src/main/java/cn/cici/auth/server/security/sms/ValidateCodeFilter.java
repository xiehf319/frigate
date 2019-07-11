package cn.cici.auth.server.security.sms;

import cn.cici.frigate.component.exception.BusinessException;
import cn.cici.frigate.component.exception.CommonResponseEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 类介绍：检验验证码过滤器
 * @createDate: 2019/7/11 9:44
 * @author: Heyfan Xie
 */
@Component
public class ValidateCodeFilter extends OncePerRequestFilter {

    /**
     * 需要校验短信验证码的请求
     */
    private List<String> smsCodeUrls = new ArrayList<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 如果需要校验短信验证码的请求，则进行短信验证码校验
        if (smsCodeUrls.contains(request.getRequestURI())) {
            if (smsCodeValid(request, response)) {
                filterChain.doFilter(request, response);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    protected void initFilterBean() throws ServletException {
        smsCodeUrls.add("/login/mobile");
    }

    /**
     * 短信验证码是否有效
     *
     * @param request
     * @param response
     * @return
     */
    private boolean smsCodeValid(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException {
        String smsCode = ServletRequestUtils.getStringParameter(request, "smsCode");
        ValidateCode validateCode = (ValidateCode) request.getSession().getAttribute(ValidateCodeController.SESSION_CODE_KEY);
        if (StringUtils.isEmpty(smsCode)) {
            throw new BusinessException(CommonResponseEnum.SERVER_ERROR, "验证码不能为空");
        }
        if (null == validateCode) {
            throw new BusinessException(CommonResponseEnum.SERVER_ERROR, "验证码不存在");
        }
        if (LocalDateTime.now().isAfter(validateCode.getExpireTime())) {
            throw new BusinessException(CommonResponseEnum.SERVER_ERROR, "验证码已过期");
        }
        if (!StringUtils.equals(smsCode, validateCode.getCode())) {
            throw new BusinessException(CommonResponseEnum.SERVER_ERROR, "验证码不正确");
        }
        // 移除
        request.getSession().removeAttribute(ValidateCodeController.SESSION_CODE_KEY);
        return true;
    }
}
