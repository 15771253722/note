package com.mcj.zhongruan.modules.leetcode;

/**
 * @Author: MCJ
 * @Date: 2020/8/27 15:52
 */
public class AnnotationHandlerAdapter implements HandlerAdapter{
    @Override
    public boolean supports(Object hander) {
        return (hander instanceof AnnotationController);
    }

    @Override
    public void handle(Object handler) {
        ((AnnotationController)handler).doAnnotationHandler();
    }
}
