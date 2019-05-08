package cn.cici.frigate.component.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * 返回指定格式的日期
 *
 * 使用方法
 * @JsonSerialize(using = DateSerializer.class)
 * private Date date;
 *
 * @description:
 * @createDate:2019/5/7$14:56$
 * @author: Heyfan Xie
 */
public class DateSerializer extends JsonSerializer<Date> {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (date == null) {
            jsonGenerator.writeString("");
            return;
        }
        jsonGenerator.writeString(DATE_FORMAT.format(date));
    }
}
