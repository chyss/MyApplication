package com.chyss.myapplication.rules.mediator.colleague;

import com.chyss.myapplication.rules.mediator.mediator.Mediator;

/**
 * 光驱
 *
 * @author chyss 2017-11-28
 */
public class CDDevice extends Colleague
{
    //视频数据
    private String data;

    public CDDevice(Mediator mediator)
    {
        super(mediator);
    }

    /**
     * 读取视频数据
     *
     * @return 视频数据
     */
    public String read()
    {
        return data;
    }

    /**
     * 加载视频数据
     */
    public void load()
    {
        data = "video data，sound data";

        //通知中介状态改变
        mediator.changed(this);
    }
}
