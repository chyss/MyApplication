package com.chyss.myapplication.rules.composite;

/**
 * @author chyss 2017-05-05
 */

public class Leaf extends Component
{
    public Leaf(String name)
    {
        super(name);
    }

    @Override
    public void doSomething()
    {

    }

    @Override
    public void addChild(Component child)
    {
        throw new UnsupportedOperationException("叶子节点没有子节点");
    }

    @Override
    public void removeChild(Component child)
    {
        throw new UnsupportedOperationException("叶子节点没有子节点");
    }

    @Override
    public Component getChild(int index)
    {
        throw new UnsupportedOperationException("叶子节点没有子节点");
    }
}
