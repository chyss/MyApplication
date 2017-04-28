package com.chyss.myapplication.data.response;

import java.util.List;

/**
 * Created by Administrator on 2017/4/25.
 */

public class RecyData02
{
    private List<RecyData> list;
    private String name;
    private int type;

    public List<RecyData> getList()
    {
        return list;
    }

    public void setList(List<RecyData> list)
    {
        this.list = list;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }
}
