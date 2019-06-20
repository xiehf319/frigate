package cn.cici.frigate.component.exception;

import cn.cici.frigate.component.util.MessageUtils;
import cn.cici.frigate.component.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @description:
 * @createDate:2019/6/20$14:30$
 * @author: Heyfan Xie
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private MessageUtils messageUtils;

    @ExceptionHandler(ServiceException.class)
    public R handlerServiceException(ServiceException exception) {
        String code = exception.getCode();
        String message = messageUtils.getMessage(code, exception.getParams());
        if (StringUtils.isEmpty(message)) {
            message = exception.getMessage();
        }
        return R.error(Integer.valueOf(code), message);
    }
}
