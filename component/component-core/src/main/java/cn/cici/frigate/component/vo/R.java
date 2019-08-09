package cn.cici.frigate.component.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xiehf
 * @date 2019/5/6 23:42
 * @concat 370693739@qq.com
 **/
@Data
public class R<T> implements Serializable {

    private int code;

    private String message;

    private boolean success;

    private T data;

    private R() {
    }

    private R(int code, String message, boolean success, T data) {
        this.code = code;
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public static <T> R<T> success() {
        return new R<>(200, "", true, null);
    }

    public static <T> R<T> success(T data) {
        return new R<>(200, "", true, data);
    }

    public static <T> R<T> error(int code, String message) {
        return new R<>(code, message, false, null);
    }

    public static <T> R<T> error(int code) {
        return new R<>(code, null, false, null);
    }

}
