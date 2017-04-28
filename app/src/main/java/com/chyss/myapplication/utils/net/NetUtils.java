package com.chyss.myapplication.utils.net;

import com.chyss.myapplication.data.Net;
import com.chyss.myapplication.interf.RequestServ;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 网络请求的get，post方式封装类，基于Retrofit的进一步封装。
 *
 * Created by chyss on 2017/4/6
 */

public class NetUtils
{
    private static Retrofit retrofit;

    /**
     * Retrofit and Rxjava 的get请求
     * @param map
     * @param url
     */
    public static Flowable<String> getRequestFlowable(String url,Map<String,String> map)
    {
        RequestServ rs = request();
        Flowable<String> flowable = rs.getFlowable(url,map);

        return flowable;
    }

    /**
     * Retrofit and Rxjava 的post请求
     * @param map
     * @param url
     */
    public static Flowable<String> postRequestFlowable(String url,Map<String,String> map)
    {
        RequestServ rs = request();
        Flowable<String> flowable = rs.postFlowable(url,map);

        return flowable;
    }

    /**
     * Retrofit and Rxjava 的get请求
     * @param map
     * @param url
     */
    public static Observable<String> getRequestObservable(String url,Map<String,String> map)
    {
        RequestServ rs = request();
        Observable<String> observable = rs.getObservable(url,map);

        return observable;
    }

    /**
     * Retrofit and Rxjava 的post请求
     * @param map
     * @param url
     */
    public static Observable<String> postRequestObservable(String url,Map<String,String> map)
    {
        RequestServ rs = request();
        Observable<String> observable = rs.postObservable(url,map);

        return observable;
    }

    /**
     * Retrofit 的get请求
     * @param callback
     * @param map
     * @param url
     */
    public static void getRequest(Callback<String> callback,Map<String,String> map,String url)
    {
        RequestServ rs = request();
        Call<String> call =  rs.get(url,map);
        call.enqueue(callback);
    }

    /**
     * Retrofit 的post请求
     * @param callback
     * @param map
     * @param url
     */
    public static void postRequest(Callback<String> callback,Map<String,String> map,String url)
    {
        RequestServ rs = request();
        Call<String> call =  rs.post(url,map);
        call.enqueue(callback);
    }

    /**
     * Retrofit 的get请求,可添加Header参数，如Accept: text/plain and Accept-Charset: utf-8
     * @param callback
     * @param queryMap
     * @param headMap
     * @param url
     */
    public static void getRequest(Callback<String> callback,Map<String,String> queryMap,Map<String,String> headMap,String url)
    {
        RequestServ rs = request();
        Call<String> call =  rs.get(url,queryMap,headMap);
        call.enqueue(callback);
    }

    /**
     * Retrofit 的post请求,可添加Header参数，如Accept: text/plain and Accept-Charset: utf-8
     * @param callback
     * @param fieldMap
     * @param headMap
     * @param url
     */
    public static void postRequest(Callback<String> callback,Map<String,String> fieldMap,Map<String,String> headMap,String url)
    {
        RequestServ rs = request();
        Call<String> call =  rs.post(url,fieldMap,headMap);
        call.enqueue(callback);
    }

    public static RequestServ request()
    {
        //retrofit底层用的okHttp,所以设置超时还需要okHttp
        if (retrofit == null)
        {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(Net.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                    .writeTimeout(Net.WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                    .readTimeout(Net.READ_TIMEOUT, TimeUnit.MILLISECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(Net.domain)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();
        }
        RequestServ rs = retrofit.create(RequestServ.class);

        return rs;
    }
}
