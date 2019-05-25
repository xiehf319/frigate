package cn.cici.frigate.gateway.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @description:
 * @createDate:2019/5/25$14:38$
 * @author: Heyfan Xie
 */
@Component
public class HttpConverter {

    @Autowired
    ObjectMapper objectMapper;

    public <T> T readyBody(HttpServletRequest request, Class<?> clazz) {
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return (T) objectMapper.readValue(sb.toString(), clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String object2Str(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {

        }
        throw new RuntimeException();
    }
}
