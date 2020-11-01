package com.mcj.zhongruan.modules.leetcode.decorator;

/**
 * @Author: MCJ
 * @Date: 2020/8/28 12:19
 */
abstract class CofferDecorator implements Coffer {

    protected final Coffer decoratorCoffer;


    public CofferDecorator (Coffer coffer) {
         decoratorCoffer=coffer;
    }

    public double getCost() {
        return decoratorCoffer.getCost();
    }

    public String getIngredients() {
        return decoratorCoffer.getIngredients();
    }

}
