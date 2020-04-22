package cn.cici.frigate.rbac.service;

import cn.cici.frigate.component.util.Tree;
import cn.cici.frigate.rbac.controller.vo.resp.RespMenuVo;
import cn.cici.frigate.rbac.dao.entity.Menu;
import com.alibaba.fastjson.JSON;
import com.codingapi.txlcn.tc.annotation.DTXPropagation;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @description:
 * @createDate:2019/7/9$14:16$
 * @author: Heyfan Xie
 */
public class MenuService {

    @LcnTransaction(propagation = DTXPropagation.SUPPORTS)
    public void add(){
    }

    public static void main(String[] args) {

        Menu menu = new Menu(0L, 1L, "0-1");
        Menu menu1 = new Menu(1L, 2L, "1-2");
        Menu menu2 = new Menu(1L, 3L, "1-3");
        Menu menu3 = new Menu(2L, 4L, "2-4");
        Menu menu4 = new Menu(2L, 5L, "2-5");
        Menu menu5 = new Menu(3L, 6L, "2-6");

        List<Menu> menuList = Lists.newArrayList(menu, menu1, menu2, menu3, menu4, menu5);

        Collection<RespMenuVo> menuTree = new Tree<RespMenuVo, Menu>() {

            @Override
            public Predicate<Menu> createPredicate(RespMenuVo respMenuVo) {
                return m -> m.getPid().equals(respMenuVo.getId());
            }

            @Override
            public void setChildren(RespMenuVo parent, Collection<RespMenuVo> tList) {
                parent.setChildren(Lists.newArrayList(tList.iterator()));
            }

            @Override
            public Collection<RespMenuVo> convert(Collection<Menu> source) {
                return source.stream().map(m -> {
                    RespMenuVo respMenuVo = new RespMenuVo();
                    respMenuVo.setId(m.getId());
                    respMenuVo.setParentId(m.getId());
                    respMenuVo.setName(m.getMenuName());
                    return respMenuVo;
                }).collect(Collectors.toList());
            }

            @Override
            public List<Menu> getChildren(Collection<Menu> list, Predicate<Menu> predicate) {
                return list.stream().filter(predicate).collect(Collectors.toList());
            }
        }.loop(menuList, m -> m.getPid() == 0);

        List<RespMenuVo> respMenuVos = Lists.newArrayList(menuTree.iterator());
        System.out.println(JSON.toJSONString(respMenuVos));
    }
}
