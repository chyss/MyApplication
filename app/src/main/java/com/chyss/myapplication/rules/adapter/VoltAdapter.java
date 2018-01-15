package com.chyss.myapplication.rules.adapter;

/**
 * 对象适配器模式
 *
 * @author chyss 2017-11-30
 */
public class VoltAdapter implements VoltFive
{
    private Volt220 volt220;

    public VoltAdapter(Volt220 volt220)
    {
        this.volt220 = volt220;
    }

    @Override
    public int getVolt5()
    {
        // 电压转换 .....
        return 5;
    }
}
