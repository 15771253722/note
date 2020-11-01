package com.mcj.zhongruan.modules.leetcode;

/**
 * @Author: MCJ
 * @Date: 2020/8/27 15:29
 * 定义适配器接口
 */
public interface HandlerAdapter {

    public boolean supports(Object hander);
    public void handle(Object handler);

}
