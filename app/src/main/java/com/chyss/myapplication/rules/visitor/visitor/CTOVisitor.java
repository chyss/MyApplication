package com.chyss.myapplication.rules.visitor.visitor;

import com.chyss.myapplication.rules.visitor.staff.Engneer;
import com.chyss.myapplication.rules.visitor.staff.Manager;
import com.chyss.myapplication.utils.Logg;

/**
 * CTO 关心技术产出
 *
 * @author chyss 2017-11-27
 */
public class CTOVisitor implements Visitor
{
    @Override
    public void visit(Engneer engneer)
    {
        // CTO 要访问工程师的信息
        Logg.e("visitor","工程师 ： "+engneer.name+",完成代码数量 : "+engneer.getCodeLines());
    }

    @Override
    public void visit(Manager manager)
    {
        // CTO 要访问产品经理的信息
        Logg.e("visitor","产品经理 ： "+manager.name+",完成产品数量 : "+manager.getProducts());
    }
}
