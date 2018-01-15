package com.chyss.myapplication.rules.factory.product;

import com.chyss.myapplication.utils.Logg;

/**
 * 大众车型
 *
 * @author chyss 2017-11-21
 */
public class PublicCar extends Car
{
    @Override
    public void drive()
    {
        Logg.e("TAG","dive the public car.");
    }
}
