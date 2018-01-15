package com.chyss.myapplication.rules.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * 具体枝干节点
 *
 * @author chyss 2017-11-30
 */
public class Composite extends Component
{
    private List<Component> components = new ArrayList<>();

    public Composite(String name)
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
        components.add(child);
    }

    @Override
    public void removeChild(Component child)
    {
        components.remove(child);
    }

    @Override
    public Component getChild(int index)
    {
        return components.get(index);
    }
}
