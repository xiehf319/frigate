package cn.cici.frigate.gateway.exception;

import org.springframework.http.HttpStatus;

/**
 * @description:
 * @createDate:2019/5/5$11:00$
 * @author: Heyfan Xie
 */
public class ServerException extends AbstractException{

    public ServerException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ServerException(ErrorCode errorCode) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, errorCode);
    }

    public ServerException(HttpStatus httpStatus, ErrorCode errorCode) {
        super(httpStatus, errorCode);
    }
}
