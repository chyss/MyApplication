package com.chyss.myapplication.rules.proxy.object;

/**
 * 诉讼接口类
 *
 * @author chyss 2017-11-29
 */
public interface ILawsuit
{
    // 提交申请
    void submit();
    // 进行举证
    void burden();
    // 开始辩护
    void defend();
    // 诉讼完成
    void finish();
}
