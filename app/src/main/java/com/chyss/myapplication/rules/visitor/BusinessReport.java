package com.chyss.myapplication.rules.visitor;

import com.chyss.myapplication.rules.visitor.staff.Engneer;
import com.chyss.myapplication.rules.visitor.staff.Manager;
import com.chyss.myapplication.rules.visitor.staff.Staff;
import com.chyss.myapplication.rules.visitor.visitor.Visitor;

import java.util.LinkedList;
import java.util.List;

/**
 * 员工业务报表类
 *
 * @author chyss 2017-11-27
 */
public class BusinessReport
{
    List<Staff> staffs = new LinkedList<>();

    public BusinessReport()
    {
        // 获取数据（初始化）
        staffs.add(new Manager("王经理",8));
        staffs.add(new Manager("李经理",7));
        staffs.add(new Engneer("chyss",8));
        staffs.add(new Engneer("cary",7));
        staffs.add(new Engneer("joson",6));
    }

    /**
     * 为访问者展示报表
     *
     * @param visitor
     */
    public void showReport(Visitor visitor)
    {
        for (Staff staff:staffs)
        {
            staff.accept(visitor);
        }
    }
}
