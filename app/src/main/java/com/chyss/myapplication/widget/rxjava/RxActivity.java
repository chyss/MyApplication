package com.chyss.myapplication.widget.rxjava;

import android.os.Bundle;
import android.widget.TextView;

import com.chyss.myapplication.BaseActivity;
import com.chyss.myapplication.R;
import com.chyss.myapplication.data.Net;
import com.chyss.myapplication.data.ResultDesc;
import com.chyss.myapplication.data.response.Version;
import com.chyss.myapplication.utils.Logg;
import com.chyss.myapplication.utils.analysis.DataImpl;
import com.chyss.myapplication.widget.retrofit.util.RetrofitUtils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/6.
 */

public class RxActivity extends BaseActivity
{
    TextView rxjava_response_notes;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        missTitleView();

        //getVersionsInfo();
        rxjava_response_notes = (TextView)findViewById(R.id.rxjava_response_notes);
    }

    @Override
    protected void onResume()
    {
        getVersionsInfo();
//        getVersionsInfoOld();
        super.onResume();
    }

    /**
     * Flowable为Rxjava 2.0 版本新增的api，解决了背压情况
     */
    private void getVersionsInfo()
    {
        Map<String, String> map = new HashMap<>();
        map.put("version", Net.VERSIONCODE);
        map.put("platform", Net.PLATFORM);

        RetrofitUtils.postRequestFlowable(Net.version,map)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())   //处理数据线程的切换
                .map(new Function<String, ResultDesc<Version>>()
                {
                    @Override
                    public ResultDesc<Version> apply(String json) throws Exception
                    {
                        Logg.e(Net.TAG,"map ： "+Thread.currentThread()+" ---- "+json);
                        return DataImpl.getData(json,Version.class);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())  //处理数据线程的切换
                .subscribe(subscriber);
    }

    Subscriber<ResultDesc<Version>> subscriber = new Subscriber<ResultDesc<Version>>()
    {
        @Override
        public void onSubscribe(Subscription s)
        {
            Logg.e(Net.TAG,"onSubscribe ！"+Thread.currentThread()+" ---- ");
            //这一步是必须，我们通常可以在这里做一些初始化操作，调用request()方法表示初始化工作已经完成
            //调用request()方法，会立即触发onNext()方法
            //在onComplete()方法完成，才会再执行request()后边的代码
            s.request(Long.MAX_VALUE);
        }

        @Override
        public void onNext(ResultDesc<Version> resultDesc)
        {
            Logg.e(Net.TAG,"onNext ： "+Thread.currentThread()+" ---- "+resultDesc.toString());

            rxjava_response_notes.setText(resultDesc.toString());
        }

        @Override
        public void onError(Throwable t)
        {
            Logg.e(Net.TAG,"onError! "+t.toString());
        }

        @Override
        public void onComplete()
        {
            Logg.e(Net.TAG,"onComplete! "+Thread.currentThread()+" ---- ");
        }
    };

    /**
     * Observable与Observer在Rxjava 1.0版本存在背压问题，2.0版本后改为不考虑背压情况
     * @deprecated
     */
    private void getVersionsInfoOld()
    {
        Map<String, String> map = new HashMap<>();
        map.put("version", Net.VERSIONCODE);
        map.put("platform", Net.PLATFORM);

        RetrofitUtils.postRequestObservable(Net.version,map)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Function<String, ResultDesc<Version>>()
                {
                    @Override
                    public ResultDesc<Version> apply(String json) throws Exception
                    {
                        Logg.e(Net.TAG,"map ： "+Thread.currentThread()+" ---- "+json);
                        return DataImpl.getData(json,Version.class);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    Observer<ResultDesc<Version>> observer = new Observer<ResultDesc<Version>>()
    {
        @Override
        public void onSubscribe(Disposable d)
        {
            Logg.d(Net.TAG,"onSubscribe ！");
        }

        @Override
        public void onNext(ResultDesc<Version> resultDesc)
        {
            Logg.d(Net.TAG,"onNext ： "+resultDesc.toString());

            rxjava_response_notes.setText(resultDesc.toString());
        }

        @Override
        public void onError(Throwable e)
        {
            Logg.e(Net.TAG,"onError! ");
        }

        @Override
        public void onComplete()
        {
            Logg.d(Net.TAG,"onComplete! ");
        }
    };
}
