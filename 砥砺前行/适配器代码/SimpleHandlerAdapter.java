package com.mcj.zhongruan.modules.leetcode;

/**
 * @Author: MCJ
 * @Date: 2020/8/27 15:39
 */
public class SimpleHandlerAdapter implements HandlerAdapter{

    @Override
    public boolean supports(Object hander) {
        return (hander instanceof SimpleController);
    }

    @Override
    public void handle(Object handler) {
        ((SimpleController)handler).doSimpleHandler();
    }
}
