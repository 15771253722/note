package com.mcj.zhongruan.modules.leetcode.Proxy;

/**
 * 测试类
 * @Author: MCJ
 * @Date: 2020/8/28 15:16
 */
public class App {
    public static void main(String[] args) {
        //目标对象
        UserDao target = new UserDao();
        //代理对象，把目标对象传给代理对象，建立代理关系。
        UserDaoProxy proxy = new UserDaoProxy(target);
        //执行的是代理方法
        proxy.save();
    }

}
