package com.chyss.myapplication.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.chyss.myapplication.BaseActivity;
import com.chyss.myapplication.R;
import com.chyss.myapplication.data.Net;
import com.chyss.myapplication.data.ResultDesc;
import com.chyss.myapplication.data.response.Version;
import com.chyss.myapplication.utils.Logg;
import com.chyss.myapplication.utils.net.NetUtils;
import com.chyss.myapplication.utils.analysis.DataImpl;

import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitActivity extends BaseActivity
{
    TextView retrofit_response_notes;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        missTitleView();
        retrofit_response_notes = (TextView)findViewById(R.id.retrofit_response_notes);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        Map<String,String> map = new HashMap<String, String>();
        map.put("version",Net.VERSIONCODE);
        map.put("platform",Net.PLATFORM);
        NetUtils.postRequest(callback,map, Net.version);
    }

    Callback<String> callback = new Callback<String>()
    {
        @Override
        public void onResponse(Call<String> call, Response<String> response)
        {
            Logg.e(Net.TAG,"response----- : "+response.body().toString());
            ResultDesc<Version> resultDesc = DataImpl.getData(response.body().toString(),Version.class);

            if(resultDesc.getStatus() == 0 && resultDesc.getData() != null)
            {
                Version version = resultDesc.getData();
                Logg.e(Net.TAG,"versions----- : "+version.toString());
                retrofit_response_notes.setText(version.toString());
            }
        }

        @Override
        public void onFailure(Call<String> call, Throwable t)
        {
            Logg.e(Net.TAG,"response : "+t.toString());
        }
    };
}
