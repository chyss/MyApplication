package com.chyss.myapplication.rules.factory.product;

import com.chyss.myapplication.utils.Logg;

/**
 * 奥迪车型
 *
 * @author chyss 2017-05-05
 */
public class AudiCar extends Car
{
    @Override
    public void drive()
    {
        Logg.e("TAG","dive the audi car.");
    }

    public void autoDrive()
    {
        Logg.e("TAG","this kind of audi car has the character of autodrive.");
    }
}
