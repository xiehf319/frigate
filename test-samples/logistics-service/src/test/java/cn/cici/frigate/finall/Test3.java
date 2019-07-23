package cn.cici.frigate.finall;

import java.io.*;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/23 10:01
 * @author: Heyfan Xie
 */
public class Test3 {

    private static final int SIZE = 10 * 1024;

    public static void main(String[] args) throws Exception{
        File tmp = File.createTempFile("gap", ".txt");

        FileOutputStream out = new FileOutputStream(tmp);

        out.write(1);
        out.write(new byte[SIZE]);
        out.write(2);
        out.close();

        InputStream in = new BufferedInputStream(new FileInputStream(tmp), SIZE + 1);
        int first = in.read();
        long skip = in.skip(SIZE);
        System.out.println(skip);
        int last = in.read();

        System.out.println(first + last);
    }
}
