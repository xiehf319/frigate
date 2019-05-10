package cn.cici.auth.server.web;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * 由于oauth2没有提供好看的错误页面，这里需要自定义一个
 * @see org.springframework.security.oauth2.provider.endpoint.WhitelabelApprovalEndpoint
 *
 *
 * @description:
 * @createDate:2019/5/10$11:07$
 * @author: Heyfan Xie
 */
@Controller
@SessionAttributes({"authorizationRequest"})
@Slf4j
public class CustomApprovalController {

    /**
     * 授权成功返回的数据
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping({"/oauth/custom_approval"})
    @ResponseBody
    public JSONObject getAccessConfirmation(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
        log.info("model: {}", JSONObject.toJSON(model));
        Map<String, String> scopes = (Map<String, String>) (model.containsKey("scopes") ? model.get("scopes") : request.getAttribute("scopes"));
        List<String> scopeList = new ArrayList<>(scopes.keySet());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("scopeList", scopeList);
        return jsonObject;
    }

    /**
     * 错误返回的页面
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping({"/oauth/custom_error"})
    public String handleError(Map<String, Object> model, HttpServletRequest request) {
        Object error = request.getAttribute("error");
        String errorSummary;
        if (error instanceof OAuth2Exception) {
            OAuth2Exception exception = (OAuth2Exception) error;
            errorSummary = HtmlUtils.htmlEscape(exception.getSummary());
        } else {
            errorSummary = "Unknown error.";
        }
        model.put("errorSummary", errorSummary);
        return "oauth_error";
    }
}
