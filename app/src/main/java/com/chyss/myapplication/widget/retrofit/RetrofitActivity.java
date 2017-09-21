package com.chyss.myapplication.widget.retrofit;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chyss.myapplication.BaseActivity;
import com.chyss.myapplication.R;
import com.chyss.myapplication.data.Net;
import com.chyss.myapplication.data.ResultDesc;
import com.chyss.myapplication.data.response.Version;
import com.chyss.myapplication.utils.Logg;
import com.chyss.myapplication.widget.retrofit.util.RetrofitUtils;
import com.chyss.myapplication.utils.analysis.DataImpl;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
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
        setTitle("Retrofit");
        initView();
    }

    private void initView()
    {
        findViewById(R.id.retrofit_form).setOnClickListener(onClickListener);
        findViewById(R.id.retrofit_json).setOnClickListener(onClickListener);
        retrofit_response_notes = (TextView)findViewById(R.id.retrofit_response_notes);
    }

    View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
            	case R.id.retrofit_form:
                    Map<String,String> map = new HashMap<>();
                    map.put("version",Net.VERSIONCODE);
                    map.put("platform",Net.PLATFORM);
                    RetrofitUtils.postRequest(callback,map, Net.version);
            	    break;
                case R.id.retrofit_json:
                    Map<String,String> paramsMap = new HashMap<>();
                    paramsMap.put("version",Net.VERSIONCODE);
                    paramsMap.put("platform",Net.PLATFORM);
                    Gson gson=new Gson();
                    String strEntity = gson.toJson(paramsMap);
                    RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);
                    RetrofitUtils.postRequest(callback,body, Net.version);
                    break;
            }
        }
    };

    Callback<String> callback = new Callback<String>()
    {
        @Override
        public void onResponse(Call<String> call, Response<String> response)
        {
            Logg.e(Net.TAG,"response----- : "+ response.body());
            ResultDesc<Version> resultDesc = DataImpl.getData(response.body(),Version.class);

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
