package com.chyss.myapplication.rules.adapter;

/**
 * 对象适配器模式
 *
 * @author chyss 2017-11-30
 */
public class Client
{
    public static void main()
    {
        //创建对象
        VoltFive voltFive = new VoltAdapter(new Volt220());
        //获取5V电压
        voltFive.getVolt5();
    }
}
