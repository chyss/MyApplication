/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.chyss.myapplication.widget.opengl;

import android.content.Context;
import android.content.res.TypedArray;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.chyss.myapplication.R;
import com.chyss.myapplication.widget.opengl.shape.Ball;
import com.chyss.myapplication.widget.opengl.shape.BallLight;
import com.chyss.myapplication.widget.opengl.shape.Circle;
import com.chyss.myapplication.widget.opengl.shape.Cone;
import com.chyss.myapplication.widget.opengl.shape.Cube;
import com.chyss.myapplication.widget.opengl.shape.Cylinder;
import com.chyss.myapplication.widget.opengl.shape.Triangle;


public class OpenglSurfaceView extends GLSurfaceView {

    Renderer render;

    private int dex = 0;
    private float rotate = 0f;
    private float scale = 1f;
    private float trans = 0f;

    public OpenglSurfaceView(Context context,int type) {
        super(context);
        init(type);
    }

    public OpenglSurfaceView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.OpenglSurfaceView);
        int type = array.getInteger(R.styleable.OpenglSurfaceView_renderType,0);
        array.recycle();

        init(type);
    }

    private void init(int type)
    {
        // 使用OpenGLES 2.0
        setEGLContextClientVersion(2);

        // 设置Renderer到GLSurfaceView
        switch (type)
        {
            case 1:
                render = new Triangle();
                break;
            case 2:
                render = new Cube();
                break;
            case 3:
                render = new Circle();
                break;
            case 4:
                render = new Cone();
                break;
            case 5:
                render = new Cylinder();
                break;
            case 6:
                render = new Ball();
                break;
            case 7:
                render = new BallLight(this);
                break;
            default:
                render = new Cube();
                break;
        }
        setRenderer(render);

        // render模式为只在绘制数据发生改变时才绘制view,此设置会阻止绘制GLSurfaceView的帧，直到你调用了requestRender().
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        //以下模式会不断绘制，我的手机上大概5--6 毫秒绘制一次
        //setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(render instanceof Cylinder)
        {
            //圆柱的平移、旋转、缩放
            if(((int)(dex/100))%2 == 0)
            {
                scale = 1.01f;
                trans = 0.03f;
            }
            else
            {
                scale = 0.99f;
                trans = -0.03f;
            }
            rotate = 3f;
            dex++;
            ((Cylinder)render).setInfo(rotate,trans,scale);
            requestRender();//重绘画面
        }
        return true;
    }
}
