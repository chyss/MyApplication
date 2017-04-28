package com.chyss.myapplication.data;

/**
 * 接收数据对象基类
 *
 * create by chyss on 2017/4/6
 */
public class ResultDesc<T> {

    private String msg; //错误信息，如果success 为true 则返回空，
    private int status;// 错误代码，如果success 为true 则返回空，或者按照现有约定错误类型进行,0正常 >0有错误
    private T data;// 返回的正确数据，为一个json对象或json数组。
    private String commandCode;//接口名称

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCommandCode() {
        return commandCode;
    }

    public void setCommandCode(String commandCode) {
        this.commandCode = commandCode;
    }
    
    @Override
    public String toString() {
        return "ResultDesc { " +
                " commandCode = " + commandCode +
                ", status = " + status +
                ", msg = " + msg +
                ", data = { " + data.toString() +
                " }";
    }
}
