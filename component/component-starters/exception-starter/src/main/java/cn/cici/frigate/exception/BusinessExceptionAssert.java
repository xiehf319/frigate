package cn.cici.frigate.exception;

import java.text.MessageFormat;

/**
 * @description:
 * @createDate:2019/7/3$12:00$
 * @author: Heyfan Xie
 */
public interface BusinessExceptionAssert extends ResponseEnum, Assert {


    @Override
    default BaseException newException() {
        return new BusinessException(this, this.getMessage());
    }

    @Override
    default BaseException newException(Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);
        return new BusinessException(this, args, msg);
    }

    @Override
    default BaseException newException(Throwable t, Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);
        return new BusinessException(this, args, msg, t);
    }
}
