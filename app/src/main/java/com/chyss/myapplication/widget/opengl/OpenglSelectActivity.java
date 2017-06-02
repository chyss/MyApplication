package com.chyss.myapplication.widget.opengl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.chyss.myapplication.BaseActivity;
import com.chyss.myapplication.R;

/**
 * @author chyss 2017-05-05
 */

public class OpenglSelectActivity extends BaseActivity
{
    OpenglSurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opengl_selector);
        setTitle("OpenGL ES");

        surfaceView = (OpenglSurfaceView)findViewById(R.id.opengl_glsurface);

        findViewById(R.id.opengl_triangle).setOnClickListener(onClickListener);
        findViewById(R.id.opengl_cube).setOnClickListener(onClickListener);
        findViewById(R.id.opengl_circle).setOnClickListener(onClickListener);
        findViewById(R.id.opengl_cone).setOnClickListener(onClickListener);
        findViewById(R.id.opengl_Cylinder).setOnClickListener(onClickListener);
        findViewById(R.id.opengl_ball).setOnClickListener(onClickListener);
        findViewById(R.id.opengl_balllight).setOnClickListener(onClickListener);
        findViewById(R.id.opengl_cubetexture).setOnClickListener(onClickListener);
        findViewById(R.id.opengl_square).setOnClickListener(onClickListener);
        findViewById(R.id.opengl_cubesquaretexture).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.opengl_triangle:
                    stepNext(OpenglActivity.class,1);
                    break;
                case R.id.opengl_cube:
                    stepNext(OpenglActivity.class,2);
                    break;
                case R.id.opengl_circle:
                    stepNext(OpenglActivity.class,3);
                    break;
                case R.id.opengl_cone:
                    stepNext(OpenglActivity.class,4);
                    break;
                case R.id.opengl_Cylinder:
                    stepNext(OpenglActivity.class,5);
                    break;
                case R.id.opengl_ball:
                    stepNext(OpenglActivity.class,6);
                    break;
                case R.id.opengl_balllight:
                    stepNext(OpenglActivity.class,7);
                    break;
                case R.id.opengl_cubetexture:
                    stepNext(OpenglActivity.class,8);
                    break;
                case R.id.opengl_square:
                    stepNext(OpenglActivity.class,9);
                    break;
                case R.id.opengl_cubesquaretexture:
                    stepNext(OpenglActivity.class,10);
                    break;
            }
        }
    };

    private <T> void stepNext(Class<T> t,int type)
    {
        Intent intent = new Intent(this, t);
        intent.putExtra("type",type);
        startActivity(intent);
    }
}
