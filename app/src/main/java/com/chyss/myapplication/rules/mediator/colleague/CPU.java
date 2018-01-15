package com.chyss.myapplication.rules.mediator.colleague;

import com.chyss.myapplication.rules.mediator.mediator.Mediator;

/**
 * CPU
 *
 * @author chyss 2017-11-28
 */
public class CPU extends Colleague
{
    private String dataVideo;
    private String dataSound;

    public CPU(Mediator mediator)
    {
        super(mediator);
    }

    /**
     * 获取音频数据
     *
     * @return  音频数据
     */
    public String getDataSound()
    {
        return dataSound;
    }

    /**
     * 获取视频数据
     *
     * @return  视频数据
     */
    public String getDataVideo()
    {
        return dataVideo;
    }

    public void decodeData(String data)
    {
        //分割音视频数据
        String[] tmp = data.split(",");
        dataVideo = tmp[0];
        dataSound = tmp[1];

        //告诉中介自身状态改变
        mediator.changed(this);
    }
}
