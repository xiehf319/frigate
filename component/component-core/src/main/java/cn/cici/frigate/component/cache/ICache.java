package cn.cici.frigate.component.cache;

/**
 * 类描述:
 *
 * @author 003300
 * @version 1.0
 * @date 2020/4/8 17:50
 */
public interface ICache {

    void put(String key, String value);

    void clear();

    String get(String key);

    void remove(String key);

    boolean contain(String key);

    int size();
}
