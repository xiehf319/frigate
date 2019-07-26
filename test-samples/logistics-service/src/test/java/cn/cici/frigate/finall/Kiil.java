package cn.cici.frigate.finall;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/25 11:08
 * @author: Heyfan Xie
 */
public class Kiil {

    public static void main(String[] args) {
        System.out.println(k(10));
        System.out.println(k(100));
        System.out.println(k(1000));
        System.out.println(k(10000));
    }


    public static int k(int total) {
        int k=1;
        while((total = total / 2) >= 0 && total > 0 && (k= k << 1) > 0) {}
        return k;
    }
}
