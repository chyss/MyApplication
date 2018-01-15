package com.chyss.myapplication.rules.observer.observable;

import java.util.Observable;

/**
 * 被观察者：
 * 继承Observable类，当状态发生改变时通知所有的观察者
 *
 * @author chyss 2017-11-24
 */
public class Subscriber extends Observable
{
    /**
     * 当状况发生改变时，通知所有的观察者
     *
     * @param content
     */
    public void postChange(String content)
    {
        //标识状态或内容发生改变
        setChanged();
        //通知所有观察者
        notifyObservers(content);
    }
}
