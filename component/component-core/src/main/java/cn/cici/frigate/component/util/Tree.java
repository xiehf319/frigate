package cn.cici.frigate.component.util;

/**
 * 类描述:
 *
 * @author 003300
 * @version 1.0
 * @date 2020/4/22 16:56
 */

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 将关系型存储的树的集合数据 转为 层次的树结构
 *
 * @description:
 * @createDate:2018/11/30$17:33$
 * @author: Heyfan Xie
 */
public abstract class Tree<T, S> {

    public abstract Predicate<S> createPredicate(T t);

    public Collection<T> loop(Collection<S> list, Predicate<S> predicate) {
        Collection<S> children = getChildren(list, predicate);
        return Optional.ofNullable(children).map(c -> {
            Collection<T> parentCol = convert(c);
            parentCol.forEach(parent -> {
                Collection<T> tList = loop(list, createPredicate(parent));
                Optional.ofNullable(tList).ifPresent(t -> setChildren(parent, t));
            });
            return parentCol;
        }).orElse(null);
    }

    public abstract void setChildren(T parent, Collection<T> tList);

    public abstract Collection<T> convert(Collection<S> source);

    public Collection<S> getChildren(Collection<S> list, Predicate<S> predicate) {
        return list.stream().filter(predicate).collect(Collectors.toList());
    }
}