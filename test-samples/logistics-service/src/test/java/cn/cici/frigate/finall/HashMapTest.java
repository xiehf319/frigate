package cn.cici.frigate.finall;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/22 9:33
 * @author: Heyfan Xie
 */
public class HashMapTest {

    public static void main(String[] args) {

        HashMap<Person, Integer> hashMap = new HashMap<>();

        hashMap.put(new Person(10), 1);
        hashMap.put(new Person(11), 1);
        hashMap.put(new Person(12), 2);
        hashMap.put(new Person(13), 2);
        hashMap.put(new Person(14), 2);
        hashMap.put(new Person(15), 2);
        hashMap.put(new Person(16), 2);
        hashMap.put(new Person(17), 2);
        hashMap.put(new Person(18), 2);
        hashMap.put(new Person(19), 2);
        hashMap.put(new Person(20), 2);
        hashMap.put(new Person(21), 2);
        hashMap.put(new Person(22), 2);
        hashMap.put(new Person(23), 2);

        hashMap.get(new Person(10));

        System.out.println(new Person(11));
        System.out.println(new Person(10));

        System.out.println((4-1) & 9 );



        System.out.println(hashMap);

    }
}

@Data
@AllArgsConstructor
class Person {

    int age;

    @Override
    public int hashCode() {
        return 10;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}