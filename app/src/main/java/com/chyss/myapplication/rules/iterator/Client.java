package com.chyss.myapplication.rules.iterator;

import com.chyss.myapplication.rules.factory.factoty.CarFactoty;
import com.chyss.myapplication.rules.factory.factoty.Factoty;
import com.chyss.myapplication.rules.factory.product.AudiCar;
import com.chyss.myapplication.rules.factory.product.PublicCar;
import com.chyss.myapplication.utils.Logg;

/**
 * 调用类
 *
 * @author chyss 2017-11-24
 */
public class Client
{

    public static void useInterator()
    {
        //构造具体容器对象
        ConcreteAggregate<String> ca = new ConcreteAggregate<>();

        //装载
        ca.add("hello");
        ca.add("chyss");
        ca.add("6666");

        //遍历
        Iterator<String> i = ca.iterator();
        while (i.hasNext())
        {
            Logg.d("iterator","------------ "+i.next());
        }


    }
}
