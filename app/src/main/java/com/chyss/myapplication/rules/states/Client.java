package com.chyss.myapplication.rules.states;

import com.chyss.myapplication.rules.strategy.TranficCalculate;

/**
 * 状态模式，调用类。
 *
 * 以下几种常见优化状态：
 * 1、如音乐的播放下一曲和上一曲，在单曲循环、随机循环、顺序循环等状态下的不同实现也可以用状态模式进行优化。
 * 2、用户登录、未登录状态下的操作
 *
 * @author chyss 2017-11-22
 */
public class Client
{
    public static void useStates()
    {
        //创建tv控制对象
        TvController tc = new TvController();
        //设置tv开机状态
        tc.powerOn();
        //tv的控制，下一频道
        tc.nextChannel();

        //设置tv关机状态
        tc.powerOff();
        //tv的控制，上一频道
        tc.prevChannel();
    }
}
