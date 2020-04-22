package cn.cici.frigate.component.util;

import java.util.Collection;
import java.util.Optional;

/**
 *
 * 用于解析树结构
 *
 * @description:
 * @createDate:2018/11/29$15:05$
 * @author: Heyfan Xie
 */
public abstract class Cycle<T> {
    private void loop(T item, T parent) {
        perform(item, parent);
        Collection<T> children = getChildren(item);
        Optional.ofNullable(children).ifPresent(c -> {
            for (T aChildren : children) {
                loop(aChildren, item);
            }
        });
        performed(item, parent);
    }

    public void cycle(T t) {
        loop(t, null);
    }

    public abstract Collection<T> getChildren(T t);

    public abstract void perform(T item, T parent);

    public void performed(T item, T parent) {
    }
}
