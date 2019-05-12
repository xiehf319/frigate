//package cn.cici.frigate.component.redis;
//
//import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
///**
// * @author xiehf
// * @date 2019/5/7 0:31
// * @concat 370693739@qq.com
// **/
//public class JdkSerializationStrategy {
//
//    private static final JdkSerializationRedisSerializer OBJECT_SERIALIZER = new JdkSerializationRedisSerializer();
//
//    private static final StringRedisSerializer STRING_SERIALIZER = new StringRedisSerializer();
//
//    public String deserializeStringInternal(byte[] bytes) {
//        return STRING_SERIALIZER.deserialize(bytes);
//    }
//
//    public byte[] serializeInternal(String string) {
//        return STRING_SERIALIZER.serialize(string);
//    }
//
//    @SuppressWarnings("unchecked")
//    public <T> T deserializeInternal(byte[] bytes, Class<T> clazz) {
//        return (T) OBJECT_SERIALIZER.deserialize(bytes);
//    }
//
//    protected byte[] serializeInternal(Object object) {
//        return OBJECT_SERIALIZER.serialize(object);
//    }
//
//    private static final byte[] EMPTY_ARRAY = new byte[0];
//
//    private static boolean isEmpty(byte[] bytes) {
//        return bytes == null || bytes.length == 0;
//    }
//
//    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
//        if (isEmpty(bytes)) {
//            return null;
//        }
//        return deserializeInternal(bytes, clazz);
//    }
//
//
//    public String deserializeString(byte[] bytes) {
//        if (isEmpty(bytes)) {
//            return null;
//        }
//        return deserializeStringInternal(bytes);
//    }
//
//    public byte[] serialize(Object object) {
//        if (object == null) {
//            return EMPTY_ARRAY;
//        }
//        return serializeInternal(object);
//    }
//
//    public byte[] serialize(String data) {
//        if (data == null) {
//            return EMPTY_ARRAY;
//        }
//        return serializeInternal(data);
//    }
//}
