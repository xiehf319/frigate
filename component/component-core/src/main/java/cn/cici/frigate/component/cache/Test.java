package cn.cici.frigate.component.cache;


public class Test {

    public static void main(String[] args) {

        ICache normal = NormalCache.getInstance();
//        for (int i=0; ;i++) {
//            normal.put("key" + i, new String(new byte[1024]) + i);
//            if (i % 10 == 0) {
//                System.out.println(normal.size());
//            }
//        }
        ICache speed = SpeedCache.getInstance();
        for (int i=0; ;i++) {
            speed.put("key" + i, new String(new byte[1024]) + i);
            if (i % 10 == 0) {
                System.out.println(speed.size());
            }
        }
    }
}
