package com.chyss.myapplication.rules.composite;

/**
 * 抽象根节点
 *
 * @author chyss 2017-11-30
 */
public abstract class Component
{
    protected String name;

    public Component(String name)
    {
        this.name = name;
    }

    /**
     * 执行方法
     */
    public abstract void doSomething();

    /**
     * 添加子节点
     *
     * @param child
     */
    public abstract void addChild(Component child);

    /**
     * 删除子节点
     *
     * @param child
     */
    public abstract void removeChild(Component child);

    /**
     * 获取子节点
     *
     * @param index
     * @return
     */
    public abstract Component getChild(int index);
}
