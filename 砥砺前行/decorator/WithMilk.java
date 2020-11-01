package com.mcj.zhongruan.modules.leetcode.decorator;

/**
 * @Author: MCJ
 * @Date: 2020/8/28 12:44
 */
public class WithMilk extends CofferDecorator {
    public WithMilk(Coffer coffer) {
        super(coffer);
    }

    @Override
    public double getCost() {
        double mikeCost = 1.5;
        return super.getCost()+mikeCost;
    }

    @Override
    public String getIngredients() {
        String addMilkIngredients = "Milk";
        return super.getIngredients()+","+addMilkIngredients;
    }
}
