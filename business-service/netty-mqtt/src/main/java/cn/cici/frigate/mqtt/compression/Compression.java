package cn.cici.frigate.mqtt.compression;

import java.io.IOException;

/**
 *
 * @date 2019/1/14 11:09
 */
public interface Compression {

    /**
     * compress
     *
     * @param buffer
     * @return
     * @throws IOException
     */
    byte[] compress(byte[] buffer) throws IOException;

    /**
     * decompress
     *
     * @param buffer
     * @return
     * @throws IOException
     */
    byte[] decompress(byte[] buffer) throws IOException;
}
