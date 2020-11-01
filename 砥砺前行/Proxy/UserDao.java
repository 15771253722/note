package com.mcj.zhongruan.modules.leetcode.Proxy;

/**
 * 接口实现
 * 目标对象
 * @Author: MCJ
 * @Date: 2020/8/28 15:07
 */
public class UserDao implements IUserDao {

    @Override
    public void save() {
        System.out.println("---已经保存数据---");
    }
}
