package com.chyss.myapplication.rules.factory;

import com.chyss.myapplication.rules.factory.factoty.CarFactoty;
import com.chyss.myapplication.rules.factory.factoty.Factoty;
import com.chyss.myapplication.rules.factory.product.AudiCar;
import com.chyss.myapplication.rules.factory.product.PublicCar;

/**
 * 调用类
 *
 * @author chyss 2017-11-21
 */
public class Client
{

    public static void useFactory()
    {
        //构造一个汽车工厂对象
        Factoty factoty = new CarFactoty();

        //生产奥迪
        AudiCar audiCar = factoty.createCar(AudiCar.class);
        audiCar.drive();
        audiCar.autoDrive();

        //生产大众
        PublicCar publicCar = factoty.createCar(PublicCar.class);
        publicCar.drive();
    }
}
