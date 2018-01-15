package com.chyss.myapplication.rules.observer;

import com.chyss.myapplication.rules.observer.observable.Subscriber;
import com.chyss.myapplication.rules.observer.observer.Coder;
import com.chyss.myapplication.rules.observer.observer.User;

/**
 * 观察者模式，调用类。
 *
 * android中的使用例子：
 * 1、BaseAdapter的实现：
 *      notifyDataSetChanged();
 * 2、BroadcastReceiver 广播的订阅：
 *      接收对象通过Context的registerReceiver函数注册到AMS中，当通过sendBroadcast发送广播时，
 *      所有注册了对应的IntentFilter的BroadcastReceiver对象就会接收到这个消息，BroadcastReceiver的
 *      onReceive方法就会被调用。
 *
 * @author chyss 2017-11-24
 */
public class Client
{
    public static void useObserver()
    {
        //创建被观察对象
        Subscriber subscriber = new Subscriber();

        //将观察者注册到订阅列表
        Coder coder = new Coder();
        User user = new User();
        subscriber.addObserver(coder);
        subscriber.addObserver(user);

        //发布消息
        subscriber.postChange("新的订阅开始啦....");
    }
}
