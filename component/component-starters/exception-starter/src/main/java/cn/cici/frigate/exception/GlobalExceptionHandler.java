package cn.cici.frigate.exception;

import cn.cici.frigate.component.vo.R;
import cn.cici.frigate.i18n.config.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @description:
 * @createDate:2019/6/20$14:30$
 * @author: Heyfan Xie
 */
@ControllerAdvice(annotations = RestController.class)
@ConditionalOnWebApplication
@ConditionalOnMissingBean(GlobalExceptionHandler.class)
@Slf4j
public class GlobalExceptionHandler {

    private final static String ENV_PROD = "prod";

    @Autowired
    private MessageUtils messageUtils;

    @Value("${spring.profiles.active: dev}")
    private String profile;


    public String getMessage(BaseException e) {
        int code = e.getResponseEnum().getCode();
        String message = messageUtils.getMessage(String.valueOf(code), e.getArgs());
        if (message == null || message.isEmpty()) {
            return e.getMessage();
        }
        return message;
    }

    /**
     * 业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public R handleBusinessException(BaseException e) {
        log.error(e.getMessage(), e);
        return R.error(e.getResponseEnum().getCode(), getMessage(e));
    }

    /**
     * 自定义异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public R handleBaseException(BaseException e) {
        log.error(e.getMessage(), e);
        return R.error(e.getResponseEnum().getCode(), getMessage(e));
    }

    @ExceptionHandler({
            NoHandlerFoundException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            MissingServletRequestPartException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
            HttpMediaTypeNotAcceptableException.class,
            ServletRequestBindingException.class,
            ConversionNotSupportedException.class,
            AsyncRequestTimeoutException.class
    })
    @ResponseBody
    public R handleServletException(Exception e) {
        log.error(e.getMessage(), e);
        int code = CommonResponseEnum.SERVER_ERROR.getCode();
        try {
            String simpleName = e.getClass().getSimpleName();
            ServletResponseEnum servletResponseEnum = ServletResponseEnum.valueOf(simpleName);
            code = servletResponseEnum.getCode();
        } catch (IllegalArgumentException e1) {
            log.error("class [{}] not defined in enum {}", e.getClass().getName(), ServletResponseEnum.class.getName());
        }
        if (ENV_PROD.equals(profile)) {
            // 当为生产环境, 不适合把具体的异常信息展示给用户.
            code = CommonResponseEnum.SERVER_ERROR.getCode();
            BaseException baseException = new BaseException(CommonResponseEnum.SERVER_ERROR);
            String message = getMessage(baseException);
            return R.error(code, message);
        }
        return R.error(code, e.getMessage());
    }

    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public R handleBindException(BindException e) {
        log.error("参数绑定异常", e);
        return wrapperBindingResult(e.getBindingResult());
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public R handleValidException(MethodArgumentNotValidException e) {
        log.error("参数校验异常", e);
        return wrapperBindingResult(e.getBindingResult());
    }


    private R wrapperBindingResult(BindingResult bindingResult) {
        StringBuilder msg = new StringBuilder();
        for (ObjectError error : bindingResult.getAllErrors()) {
            msg.append(", ");
            if (error instanceof FieldError) {
                msg.append(((FieldError) error).getField()).append(": ");
            }
            msg.append(error.getDefaultMessage() == null ? "" : error.getDefaultMessage());
        }
        return R.error(CommonResponseEnum.VALID_ERROR.getCode(), msg.substring(2));
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public R handleException(Exception e) {
        log.error(e.getMessage(), e);

        if (ENV_PROD.equals(profile)) {
            // 当为生产环境, 不适合把具体的异常信息展示给用户.
            int code = CommonResponseEnum.SERVER_ERROR.getCode();
            BaseException baseException = new BaseException(CommonResponseEnum.SERVER_ERROR);
            String message = getMessage(baseException);
            return R.error(code, message);
        }
        return R.error(CommonResponseEnum.SERVER_ERROR.getCode(), e.getMessage());
    }
}
