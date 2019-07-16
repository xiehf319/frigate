package cn.cici.frigate.rbac.service;

import com.codingapi.txlcn.tc.annotation.DTXPropagation;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;

/**
 * @description:
 * @createDate:2019/7/9$14:16$
 * @author: Heyfan Xie
 */
public class MenuService {

    @LcnTransaction(propagation = DTXPropagation.SUPPORTS)
    public void add(){

    }
}
