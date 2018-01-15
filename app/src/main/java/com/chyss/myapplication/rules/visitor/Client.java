package com.chyss.myapplication.rules.visitor;

import com.chyss.myapplication.rules.visitor.visitor.CEOVisitor;
import com.chyss.myapplication.rules.visitor.visitor.CTOVisitor;

/**
 * 访问者模式调用类
 *
 * @author chyss 2017-11-27
 */
public class Client
{
    public static void useVisitor()
    {
        //创建报表
        BusinessReport report = new BusinessReport();

        //CEO访问
        report.showReport(new CEOVisitor());

        //CTO访问
        report.showReport(new CTOVisitor());
    }
}
