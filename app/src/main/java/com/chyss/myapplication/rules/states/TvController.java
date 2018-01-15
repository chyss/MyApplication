package com.chyss.myapplication.rules.states;

import com.chyss.myapplication.rules.states.interfaces.IoController;
import com.chyss.myapplication.rules.states.interfaces.PowerController;
import com.chyss.myapplication.rules.states.kinds.TvOffController;
import com.chyss.myapplication.rules.states.kinds.TvOnController;

/**
 *  TV控制的实现类
 *
 * @author chyss 2017-11-22
 */
public class TvController implements PowerController,IoController
{
    private IoController powerState;

    private void setPowerState(IoController powerState)
    {
        this.powerState = powerState;
    }

    @Override
    public void powerOn()
    {
        setPowerState(new TvOnController());
    }

    @Override
    public void powerOff()
    {
        setPowerState(new TvOffController());
    }

    @Override
    public void nextChannel()
    {
        //下一频道
        powerState.nextChannel();
    }

    @Override
    public void prevChannel()
    {
        //上一频道
        powerState.prevChannel();
    }
}
