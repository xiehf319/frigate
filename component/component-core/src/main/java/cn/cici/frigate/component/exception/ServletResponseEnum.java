package cn.cici.frigate.component.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description:
 * @createDate:2019/7/3$13:51$
 * @author: Heyfan Xie
 */
@Getter
@AllArgsConstructor
public enum ServletResponseEnum implements BusinessExceptionAssert {

    /**
     * 首先根据请求Url查找有没有对应的控制器，若没有则会抛该异常，也就是大家非常熟悉的404异常
     */
    NoHandlerFoundException(500, "NoHandlerFoundException"),

    /**
     * 若匹配到了（匹配结果是一个列表，不同的是http方法不同，如：Get、Post等），
     * 则尝试将请求的http方法与列表的控制器做匹配，若没有对应http方法的控制器，则抛该异常
     */
    HttpRequestMethodNotSupportedException(500, "HttpRequestMethodNotSupportedException"),

    /**
     * 然后再对请求头与控制器支持的做比较，比如content-type请求头，
     * 若控制器的参数签名包含注解@RequestBody，
     * 但是请求的content-type请求头的值没有包含application/json，
     * 那么会抛该异常（当然，不止这种情况会抛这个异常）
     */
    HttpMediaTypeNotSupportedException(500, "HttpMediaTypeNotSupportedException"),

    /**
     * 未检测到路径参数。比如url为：/licence/{licenceId}，参数签名包含@PathVariable("licenceId")，
     * 当请求的url为/licence，在没有明确定义url为/licence的情况下，会被判定为：缺少路径参数
     */
    MissingPathVariableException(500, "MissingPathVariableException"),

    /**
     * 缺少请求参数。比如定义了参数@RequestParam("licenceId") String licenceId，
     * 但发起请求时，未携带该参数，则会抛该异常；
     */
    MissingServletRequestParameterException(500, "MissingServletRequestParameterException"),


    MissingServletRequestPartException(500, "MissingServletRequestPartException"),

    /**
     * 参数类型匹配失败。比如：接收参数为Long型，但传入的值确是一个字符串，
     * 那么将会出现类型转换失败的情况，这时会抛该异常
     */
    TypeMismatchException(500, "TypeMismatchException"),

    /**
     * 与上面的HttpMediaTypeNotSupportedException举的例子完全相反，
     * 即请求头携带了"content-type: application/json;charset=UTF-8"，
     * 但接收参数却没有添加注解@RequestBody，
     * 或者请求体携带的 json 串反序列化成 pojo 的过程中失败了，也会抛该异常；
     */
    HttpMessageNotReadableException(500, "HttpMessageNotReadableException"),

    /**
     * 返回的 pojo 在序列化成 json 过程失败了，那么抛该异常
     */
    HttpMessageNotWritableException(500, "HttpMessageNotWritableException"),

    HttpMediaTypeNotAcceptableException(500, "HttpMediaTypeNotAcceptableException"),

    ServletRequestBindingException(500, "ServletRequestBindingException"),

    ConversionNotSupportedException(500, "ConversionNotSupportedException"),

    AsyncRequestTimeoutException(500, "AsyncRequestTimeoutException"),
    ;

    private int code;

    private String message;

}
