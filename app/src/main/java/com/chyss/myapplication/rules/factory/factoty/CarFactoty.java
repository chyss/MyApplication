package com.chyss.myapplication.rules.factory.factoty;

import android.view.ViewGroup;

import com.chyss.myapplication.data.Net;
import com.chyss.myapplication.rules.factory.factoty.Factoty;
import com.chyss.myapplication.rules.factory.product.Car;
import com.chyss.myapplication.utils.Logg;

/**
 * 工厂的具体实现类
 *
 * @author chyss 2017-11-21
 */
public class CarFactoty extends Factoty
{

    @Override
    public <T extends Car> T createCar(Class<T> clz)
    {
        Car car = null;
        try
        {
            // 通过反射获取类的实例
            car = (Car) Class.forName(clz.getName()).newInstance();
        }
        catch (Exception e)
        {
        	Logg.e(Net.TAG, "catch error : " + e);
        }
        return (T) car;
    }
}
