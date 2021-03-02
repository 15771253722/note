package com.mcj.zhongruan.modules.leetcode.decorator;

/**
 * @Author: MCJ
 * @Date: 2020/8/28 12:16
 */
public class SimpleCoffer implements Coffer {

    @Override
    public double getCost() {
        return 12;
    }

    @Override
    public String getIngredients() {
        return "Coffer";
    }
}
