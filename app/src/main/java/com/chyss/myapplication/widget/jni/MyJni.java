package com.chyss.myapplication.widget.jni;

/**
 * @author chyss
 */
public class MyJni
{
    static {
        System.loadLibrary("_native");
    }

    public native static String fromNative();
}
