package com.chyss.myapplication.rules.strategy.calculate;

import com.chyss.myapplication.rules.strategy.calculate.Calculate;

/**
 * 公交车价格的具体计算类，实现Calculate接口
 *
 * 假如：10公里内1元，超10公里每加1元可乘5公里
 *
 * @author chyss 2017-11-22
 */
public class BusCalculate implements Calculate
{
    @Override
    public int calculatePrice(float km)
    {
        if(km <= 10)
        {
            return 1;
        }

        float extraKm = km - 10;
        int extraFactor = (int)extraKm / 5;
        float furplus = extraKm - extraFactor * 5;
        int price = 1 + extraFactor * 1;

        return furplus > 0 ? ++price : price;
    }
}
