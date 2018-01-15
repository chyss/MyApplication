package com.chyss.myapplication.rules.mediator.mediator;

import com.chyss.myapplication.rules.mediator.colleague.Colleague;

/**
 * 抽象中介类
 *
 * @author chyss 2017-11-28
 */
public abstract class Mediator
{
    public abstract void changed(Colleague colleague);
}
