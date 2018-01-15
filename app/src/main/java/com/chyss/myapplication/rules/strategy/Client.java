package com.chyss.myapplication.rules.strategy;

import com.chyss.myapplication.rules.strategy.calculate.BusCalculate;

/**
 * 策略模式调用类，通常可以优化if -- else ， switch - case 结构
 *
 * @author chyss 2017-11-22
 */
public class Client
{
    public static void useStrategy()
    {
        //创建交通费计算类的对象
        TranficCalculate tc = new TranficCalculate();
        //设置交通类型
        tc.setCalculate(new BusCalculate());
        //计算交通费用
        tc.calculatePrice(15.6f);
    }
}
