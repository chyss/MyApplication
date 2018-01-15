package com.chyss.myapplication.rules.visitor.visitor;

import com.chyss.myapplication.rules.visitor.staff.Engneer;
import com.chyss.myapplication.rules.visitor.staff.Manager;
import com.chyss.myapplication.utils.Logg;

/**
 * CEO 关心kpi，产品产出
 *
 * @author chyss 2017-11-27
 */
public class CEOVisitor implements Visitor
{
    @Override
    public void visit(Engneer engneer)
    {
        // CEO 要访问工程师的信息
        Logg.e("visitor","工程师 ： "+engneer.name+",kpi : "+engneer.kpi);
    }

    @Override
    public void visit(Manager manager)
    {
        // CEO 要访问产品经理的信息
        Logg.e("visitor","产品经理 ： "+manager.name+",kpi : "+manager.kpi+",完成产品数量 : "+manager.getProducts());
    }
}
