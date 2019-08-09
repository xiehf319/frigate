package cn.cici.frigate.rbac.config;

import cn.cici.frigate.component.vo.R;
import com.alibaba.fastjson.JSONObject;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @description: 类介绍：
 * @createDate: 2019/8/9 17:33
 * @author: Heyfan Xie
 */
@Component
public class CustomFeignDecoder implements Decoder {

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        String result = Util.toString(response.body().asReader());
        R r = JSONObject.parseObject(result, R.class);
        if (!r.isSuccess()) {
            throw new RuntimeException(r.getMessage());
        }
        return r.getData();
    }
}
