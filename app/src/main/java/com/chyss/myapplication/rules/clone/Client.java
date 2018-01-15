package com.chyss.myapplication.rules.clone;

/**
 * 调用类
 *
 * @author chyss 2017-11-20
 */
public class Client
{

    public static void useClone()
    {
        //创建WordDoc对象
        WordDoc doc = new WordDoc();
        doc.setmText("原始的text");
        doc.addmImages("img1");
        doc.addmImages("img2");
        doc.addmImages("img3");

        //已原始对象为原型，clone一份副本，cloneDoc的内容修改不会影响doc的内容。clone拷贝对象不会执行构造函数
        WordDoc cloneDoc = doc.clone();
        cloneDoc.setmText("修改过的text");

    }
}
