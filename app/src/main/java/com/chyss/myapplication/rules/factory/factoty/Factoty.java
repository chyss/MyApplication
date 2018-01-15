package com.chyss.myapplication.rules.factory.factoty;
import com.chyss.myapplication.rules.factory.product.Car;

/**
 * 工厂抽象类
 *
 * @author chyss 2017-11-21
 */
public abstract class Factoty
{
    /**
     * 生产的抽象方法
     */
    public abstract <T extends Car> T createCar(Class<T> clz);
}
