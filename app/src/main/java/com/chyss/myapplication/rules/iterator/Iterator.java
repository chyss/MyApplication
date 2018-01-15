package com.chyss.myapplication.rules.iterator;

/**
 * 迭代器接口
 *
 * @author chyss 2017-05-05
 */
public interface Iterator<T>
{
    /**
     * @return true 有， false 没有
     */
    boolean hasNext();

    /**
     * @return 返回当前元素 T
     */
    T next();
}
