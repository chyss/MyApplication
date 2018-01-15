package com.chyss.myapplication.rules.clone;

import com.chyss.myapplication.data.Net;
import com.chyss.myapplication.utils.Logg;

import java.util.ArrayList;

/**
 * 原型模式
 *
 * 通过实现Cloneable接口的原型模式在调用clone函数构造实例时并不一定比通过new操作速度快，
 * 只有当通过new构造对象较为耗时或者成本较高时，通过clone方法才能获得效率上的提升。
 *
 * 优点：原型模式是在内存中二进制流的拷贝，要比直接new一个对象性能好很多，特别是要在一个循环体内产生大量的对象时。
 *
 * 缺点：直接在内存中拷贝，构造函数不会执行，实际开发中要注意这个潜在的问题。
 *
 * @author chyss 2017-11-20
 */
public class WordDoc implements Cloneable
{
    private String mText;

    private ArrayList<String> mImages = new ArrayList<>();

    @Override
    protected WordDoc clone()
    {
        try
        {
        	WordDoc doc = (WordDoc) super.clone();
            doc.mText = this.mText;
            //对mImages对象也调用cline()函数，进行深拷贝
            doc.mImages = (ArrayList<String>) this.mImages.clone();

            return doc;
        }
        catch (Exception e)
        {
        	Logg.e(Net.TAG, "catch error : " + e);
        }
        return null;
    }

    public ArrayList<String> getmImages()
    {
        return mImages;
    }

    public void addmImages(String mImage)
    {
        this.mImages.add(mImage);
    }

    public String getmText()
    {
        return mText;
    }

    public void setmText(String mText)
    {
        this.mText = mText;
    }
}
