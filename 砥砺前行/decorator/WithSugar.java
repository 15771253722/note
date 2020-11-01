package com.mcj.zhongruan.modules.leetcode.decorator;

/**
 * @Author: MCJ
 * @Date: 2020/8/28 12:55
 */
public class WithSugar extends CofferDecorator {
    public WithSugar(Coffer coffer) {
        super(coffer);
    }

    @Override
    public double getCost() {
        double sugarCost = 2;
        return super.getCost()+sugarCost;
    }

    @Override
    public String getIngredients() {
        String addMilkIngredients = "Sugar";
        return super.getIngredients()+","+addMilkIngredients;
    }
}
