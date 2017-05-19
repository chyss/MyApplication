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

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.chyss.myapplication.utils.Logg;
import com.chyss.myapplication.widget.opengl.shape.Triangle;

public class MglRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "MglRenderer";

    private Triangle mTriangle;

    /**
     * 仅调用一次，用于设置view的OpenGLES环境
     * @param unused
     * @param config
     */
    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {

        Logg.e(TAG,"render onSurfaceCreated");
        //设置屏幕背景色RGBA
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        //缓冲区深度的设置
        GLES20.glClearDepthf(1.0f);
        //启用深度测试
        GLES20.glEnable(GL10.GL_DEPTH_TEST);
        //深度测试的类型
        GLES20.glDepthFunc(GL10.GL_LEQUAL);
        //良好的透视计算
        GLES20.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);


        //创建三角形对象
        mTriangle = new Triangle();
        //打开深度检测
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
    }

    /**
     * 每次View被重绘时被调用
     * @param unused
     */
    @Override
    public void onDrawFrame(GL10 unused) {
        Logg.e(TAG,"render onDrawFrame");
        //清除深度缓冲与颜色缓冲
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT|GLES20.GL_DEPTH_BUFFER_BIT);
        //绘制三角形
        mTriangle.draw();

    }

    /**
     * 如果view的几何形状发生变化就调用，例如当竖屏变为横屏时
     * @param unused
     * @param width
     * @param height
     */
    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        Logg.e(TAG,"render onSurfaceChanged");
        //设置视窗大小及位置
        GLES20.glViewport(0, 0, width, height);

    }

    public static int loadShader(int type, String shaderCode){

        //创建一个vertex shader类型(GLES20.GL_VERTEX_SHADER)
        //或一个fragment shader类型(GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // 将源码添加到shader并编译它
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
}