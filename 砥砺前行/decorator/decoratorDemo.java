package com.mcj.zhongruan.modules.leetcode.decorator;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.apache.ibatis.logging.stdout.StdOutImpl;

/**
 * @Author: MCJ
 * @Date: 2020/8/28 13:25
 */
public class decoratorDemo {
    static void print(Coffer coffer){
        System.out.println("花费："+coffer.getCost());
        System.out.println("配料："+coffer.getIngredients());
    }

    public static void main(String[] args) {
        Coffer c = new SimpleCoffer();
        print(c);

        c = new WithMilk(c);
        print(c);

        c = new  WithSugar(c);
        print(c);

    }
}
