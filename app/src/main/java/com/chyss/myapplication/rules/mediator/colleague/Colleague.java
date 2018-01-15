package com.chyss.myapplication.rules.mediator.colleague;

import com.chyss.myapplication.rules.mediator.mediator.Mediator;

/**
 * 抽象同事类
 *
 * @author chyss 2017-11-28
 */
public abstract class Colleague
{
    protected Mediator mediator;

    public Colleague(Mediator mediator)
    {
        this.mediator = mediator;
    }
}
