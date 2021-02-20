package cn.cici.frigate.logistics.count;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 类描述:
 *
 * @author 003300
 * @version 1.0
 * @date 2020/6/15 17:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    private Integer id;

    private String name;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
