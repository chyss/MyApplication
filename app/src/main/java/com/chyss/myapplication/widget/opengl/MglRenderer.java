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
import android.opengl.Matrix;

import com.chyss.myapplication.utils.Logg;
import com.chyss.myapplication.widget.opengl.shape.Triangle;
import com.chyss.myapplication.widget.opengl.utils.BufferUtil;
import com.chyss.myapplication.widget.opengl.utils.ShaderUtil;

import java.nio.FloatBuffer;

public class MglRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "MglRenderer";

    private Triangle mTriangle;


    private float[] mViewMatrix=new float[16];
    private float[] mProjectMatrix=new float[16];
    private float[] mMVPMatrix=new float[16];

    private int mProgram;

    private FloatBuffer vertexBuffer;

    //设置每个顶点的坐标数
    static final int COORDS_PER_VERTEX = 3;
    //设置三角形顶点数组
    static float coords[] = {   //默认按逆时针方向绘制
            -1f,  1f, 0.0f,
            -0.9f, 0.9f, 0.0f,
            -1f, -1f, 0.0f,
            -0.9f, -0.9f, 0.0f,
            1f, -1f, 0.0f,
            0.9f, -0.9f, 0.0f,
            1f, 1f, 0.0f,
            0.9f,0.9f,0.0f,
            -1f,  1f, 0.0f,
            -0.9f, 0.9f, 0.0f
    };

    // 设置三角形颜色和透明度（r,g,b,a）
    float color[] = {0.0f, 1.0f, 0f, 1.0f};//绿色不透明

    private int mPositionHandle;
    private int mColorHandle;
    private int mMatrixHandler;

    private final int vertexCount = coords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    /**
     * 仅调用一次，用于设置view的OpenGLES环境
     * @param gl
     * @param config
     */
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        Logg.e(TAG,"render onSurfaceCreated");
        //设置屏幕背景色RGBA
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        //缓冲区深度的设置
        GLES20.glClearDepthf(10f);
        //启用深度测试GL_DEPTH_TEST
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        //深度测试的类型
        //GLES20.glDepthFunc(GLES20.GL_LEQUAL);

        //良好的透视计算
        GLES20.glHint(GLES20.GL_GENERATE_MIPMAP_HINT, GLES20.GL_NICEST);

        GLES20.glEnable(GLES20.GL_CULL_FACE);//打开背面剪裁
        //GLES20.glDisable(GLES20.GL_CULL_FACE);//关闭背面剪裁
//        GLES20.glFrontFace(GLES20.GL_CCW);//设置逆时针卷绕为正面,默认设置如此,所以一般不需要明确设置
        GLES20.glFrontFace(GLES20.GL_CW);//设置顺时针卷染为正面.


        // 初始化顶点字节缓冲区
        vertexBuffer = BufferUtil.getFloatBuffer(coords);

        // 编译shader代码
        int vertexShader = ShaderUtil.getVertexShader();
        int fragmentShader = ShaderUtil.getFragmentShader();

        // 创建空的OpenGL ES Program
        mProgram = GLES20.glCreateProgram();

        // 将vertex shader添加到program
        GLES20.glAttachShader(mProgram, vertexShader);

        // 将fragment shader添加到program
        GLES20.glAttachShader(mProgram, fragmentShader);

        // 创建可执行的 OpenGL ES program
        GLES20.glLinkProgram(mProgram);
    }

    /**
     * 每次View被重绘时被调用
     * @param gl
     */
    @Override
    public void onDrawFrame(GL10 gl) {
        Logg.e(TAG,"render onDrawFrame");
        //清除深度缓冲与颜色缓冲
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT|GLES20.GL_DEPTH_BUFFER_BIT);

        // 添加program到OpenGL ES环境中
        GLES20.glUseProgram(mProgram);

        //获取变换矩阵vMatrix成员句柄
        mMatrixHandler= GLES20.glGetUniformLocation(mProgram,"vMatrix");
        //指定vMatrix的值
        GLES20.glUniformMatrix4fv(mMatrixHandler,1,false,mMVPMatrix,0);

        // 获取指向vertex shader的成员vPosition的handle
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // 启用一个指向三角形的顶点数组的handle
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        //准备三角形的坐标数据
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // 获取指向fragment shader的成员vColor的handle
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        //  绘制三角形
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        // glDrawArrays 使用VetexBuffer 来绘制，顶点的顺序由vertexBuffer中的顺序指定。
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, vertexCount);

        //glDrawElements 可以重新定义顶点的顺序，顶点的顺序由indices Buffer 指定
        //GLES20.glDrawElements(GLES20.GL_TRIANGLES,indices.length,GLES20.GL_UNSIGNED_INT,indexBuffer);

        // 禁用指向三角形的顶点数组
        GLES20.glDisableVertexAttribArray(mPositionHandle);
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

        //计算宽高比
        float ratio=(float)width/height;
        //设置透视投影
        Matrix.frustumM(mProjectMatrix, 0, -ratio, ratio, -1, 1, 3, 20);
        //设置相机位置
        Matrix.setLookAtM(mViewMatrix, 0, 5.0f, 5.0f, 10.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        //计算变换矩阵
        Matrix.multiplyMM(mMVPMatrix,0,mProjectMatrix,0,mViewMatrix,0);
    }
}