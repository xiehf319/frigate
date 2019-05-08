package cn.cici.frigate.component.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;

/**
 *
 * 返回时间戳
 *
 * 使用方法
 * @JsonSerialize(using = TimeStampSerializer.class)
 * private Date timestamp;
 *
 * @description:
 * @createDate:2019/5/7$15:07$
 * @author: Heyfan Xie
 */
public class TimeStampSerializer extends JsonSerializer<Date> {
    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (date == null) {
            jsonGenerator.writeNumber(0L);
            return;
        }
        jsonGenerator.writeNumber(date.getTime());
    }
}
