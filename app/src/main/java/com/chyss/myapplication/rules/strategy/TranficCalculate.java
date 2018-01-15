package com.chyss.myapplication.rules.strategy;

import com.chyss.myapplication.rules.strategy.calculate.BusCalculate;
import com.chyss.myapplication.rules.strategy.calculate.Calculate;
import com.chyss.myapplication.rules.strategy.calculate.SubwayCalculate;

/**
 * 交通费用计算类，统一管理不同的交通计费
 *
 * @author chyss 2017-11-22
 */
public class TranficCalculate
{
    private Calculate calculate;

    public void setCalculate(Calculate calculate)
    {
        this.calculate = calculate;
    }

    public int calculatePrice(float km)
    {
        return calculate.calculatePrice(km);
    }
}
