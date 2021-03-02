package com.mcj.zhongruan.modules.leetcode.Proxy;

/**
 * @Author: MCJ
 * @Date: 2020/8/28 15:59
 */
public class App2 {
    public static void main(String[] args) {
        //目标对象
        IUserDao target = new UserDao();
        System.out.println(target.getClass());

        //给目标对象，创建代理对象
        IUserDao proxy =(IUserDao) new ProxyFactory(target).getProxyInstance();
        System.out.println(proxy.getClass());

        proxy.save();
    }
}
