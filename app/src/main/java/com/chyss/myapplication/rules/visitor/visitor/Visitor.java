package com.chyss.myapplication.rules.visitor.visitor;

import com.chyss.myapplication.rules.visitor.staff.Engneer;
import com.chyss.myapplication.rules.visitor.staff.Manager;

/**
 * Visitor接口
 *
 * @author chyss 2017-11-27
 */
public interface Visitor
{
    //访问工程师类型
    void visit(Engneer engneer);
    //访问产品经理类型
    void visit(Manager manager);
}
