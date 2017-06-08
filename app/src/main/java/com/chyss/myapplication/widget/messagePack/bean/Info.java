package com.chyss.myapplication.widget.messagePack.bean;

import org.msgpack.annotation.Message;

/**
 * @author chyss 2017-05-05
 */
@Message
public class Info
{
    private String name;
    private String id;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "name : "+ name +", id : "+ id;
    }
}
