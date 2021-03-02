package com.mcj.zhongruan.modules.leetcode.decorator;

/**
 * 场景：客户12元买一杯咖啡，加糖加奶。（适配器设计模式）
 * @Author: MCJ
 * @Date: 2020/8/28 11:52
 */
public interface Coffer {
    double getCost();
    String getIngredients();
}
