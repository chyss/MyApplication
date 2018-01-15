package com.chyss.myapplication.rules.iterator;

import java.util.ArrayList;
import java.util.List;

/**
 * 具体迭代器类
 *
 * @author chyss 2017-11-24
 */
public class ConcreteIterator<T> implements Iterator<T>
{
    private List<T> list = new ArrayList<>();
    private int cursor = 0;

    public ConcreteIterator(List<T> list)
    {
        this.list = list;
    }

    @Override
    public boolean hasNext()
    {
        return cursor != list.size();
    }

    @Override
    public T next()
    {
        if(this.hasNext())
        {
            //返回T后cursor指向下一个位置
            return this.list.get(cursor ++);
        }
        return null;
    }
}
