package com.chyss.myapplication.rules.strategy.calculate;

import com.chyss.myapplication.rules.strategy.calculate.Calculate;

/**
 * 地铁价格的具体计算类，实现Calculate接口
 *
 * 假如：6公里内2元，6 ～ 10公里3元，10 ～ 15公里4元，15 ～ 20公里5元，20公里以上6元
 *
 * @author chyss 2017-11-22
 */
public class SubwayCalculate implements Calculate
{
    @Override
    public int calculatePrice(float km)
    {
        if(km <= 6)
        {
            return 2;
        }
        else if(km > 6 && km <= 10)
        {
            return 3;
        }
        else if(km > 10 && km <= 15)
        {
            return 4;
        }
        else if(km > 15 && km <= 20)
        {
            return 5;
        }

        return 6;
    }
}
