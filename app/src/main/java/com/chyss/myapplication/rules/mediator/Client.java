package com.chyss.myapplication.rules.mediator;

import com.chyss.myapplication.rules.iterator.ConcreteAggregate;
import com.chyss.myapplication.rules.iterator.Iterator;
import com.chyss.myapplication.rules.mediator.colleague.CDDevice;
import com.chyss.myapplication.rules.mediator.colleague.CPU;
import com.chyss.myapplication.rules.mediator.colleague.GraphicsCard;
import com.chyss.myapplication.rules.mediator.colleague.SoundCard;
import com.chyss.myapplication.rules.mediator.mediator.MainBoard;
import com.chyss.myapplication.utils.Logg;

/**
 * 调用类
 *
 * @author chyss 2017-11-28
 */
public class Client
{

    public static void useMediator()
    {
        //构造主板对象
        MainBoard mediator = new MainBoard();

        //构建零部件
        CDDevice cdDevice = new CDDevice(mediator);
        CPU cpu = new CPU(mediator);
        GraphicsCard graphicsCard = new GraphicsCard(mediator);
        SoundCard soundCard = new SoundCard(mediator);

        //装载零部件
        mediator.setCdDevice(cdDevice);
        mediator.setCpu(cpu);
        mediator.setGraphicsCard(graphicsCard);
        mediator.setSoundCard(soundCard);

        //开始播放视频
        cdDevice.load();
    }
}
