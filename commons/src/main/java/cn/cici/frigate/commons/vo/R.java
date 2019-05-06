package cn.cici.frigate.commons.vo;

import lombok.Data;

/**
 * @author xiehf
 * @date 2019/5/6 23:42
 * @concat 370693739@qq.com
 **/
@Data
public class R<T> {

    private int code;

    private String message;

    private T data;

    private R() {
    }

    private R(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> R<T> success() {
        return new R<>(200, "", null);
    }

    public static <T> R<T> success(T data) {
        return new R<>(200, "", data);
    }

    public static <T> R<T> error(int code, String message) {
        return new R<>(code, message, null);
    }

}
