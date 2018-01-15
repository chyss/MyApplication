package com.chyss.myapplication.rules.mediator.mediator;

import com.chyss.myapplication.rules.mediator.colleague.CDDevice;
import com.chyss.myapplication.rules.mediator.colleague.CPU;
import com.chyss.myapplication.rules.mediator.colleague.Colleague;
import com.chyss.myapplication.rules.mediator.colleague.GraphicsCard;
import com.chyss.myapplication.rules.mediator.colleague.SoundCard;

/**
 * 主板中介者
 *
 * @author chyss 2017-11-28
 */
public class MainBoard extends Mediator
{
    private CPU cpu;
    private CDDevice cdDevice;
    private SoundCard soundCard;
    private GraphicsCard graphicsCard;

    @Override
    public void changed(Colleague colleague)
    {
        if(colleague == cdDevice)
        {
            handleCD((CDDevice)colleague);
        }
        else if(colleague == cpu)
        {
            handleCPU((CPU)colleague);
        }
    }

    private void handleCPU(CPU colleague)
    {

    }

    private void handleCD(CDDevice colleague)
    {

    }

    public void setGraphicsCard(GraphicsCard graphicsCard)
    {
        this.graphicsCard = graphicsCard;
    }

    public void setCpu(CPU cpu)
    {
        this.cpu = cpu;
    }

    public void setCdDevice(CDDevice cdDevice)
    {
        this.cdDevice = cdDevice;
    }

    public void setSoundCard(SoundCard soundCard)
    {
        this.soundCard = soundCard;
    }
}
