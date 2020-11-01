package com.mcj.zhongruan.modules.leetcode;

/**
 * @Author: MCJ
 * @Date: 2020/8/27 15:49
 */
public class HttpHandlerApapter implements HandlerAdapter {
    @Override
    public boolean supports(Object hander) {
        return (hander instanceof HttpController);
    }

    @Override
    public void handle(Object handler) {
        ((HttpController)handler).doHttpHander();
    }
}
