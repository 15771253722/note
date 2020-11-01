package com.mcj.zhongruan.modules.leetcode.Proxy;

import org.junit.Test;

/**
 * @Author: MCJ
 * @Date: 2020/8/28 17:17
 */
public class AppCglib {

    @Test
    public void test(){
        //目标对象
        UserDao target = new UserDao();

        //代理对象
        UserDao proxy = (UserDao)new ProxyCglibFactory(target).getProxyInstance();

        //执行代理对象方法
        proxy.save();
    }

}
