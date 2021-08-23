package cn.cici.frigate.component.cache;


public interface ICache {

    void put(String key, String value);

    void clear();

    String get(String key);

    void remove(String key);

    boolean contain(String key);

    int size();
}
