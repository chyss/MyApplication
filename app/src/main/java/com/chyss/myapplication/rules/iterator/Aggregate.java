package com.chyss.myapplication.rules.iterator;

/**
 * 容器抽象类
 *
 * @author chyss 2017-11-24
 */
public interface Aggregate<T>
{
    /**
     * 添加元素
     * @param obj
     */
    void add(T obj);

    /**
     * 移除元素
     * @param obj
     */
    void remove(T obj);

    /**
     * 获取元素的迭代器
     * @return 迭代器对象
     */
    Iterator<T> iterator();
}
