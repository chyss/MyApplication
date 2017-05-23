package com.chyss.myapplication.widget.opengl.shape;

import android.opengl.GLES20;
import com.chyss.myapplication.widget.opengl.utils.BufferUtil;
import com.chyss.myapplication.widget.opengl.utils.ShaderUtil;
import java.nio.FloatBuffer;

/**
 * @author chyss 2017-05-05
 */

public class Triangle
{
    private final int mProgram;

    private FloatBuffer vertexBuffer;

    //设置每个顶点的坐标数
    static final int COORDS_PER_VERTEX = 3;
    //设置三角形顶点数组
    static float triangleCoords[] = {   //默认按逆时针方向绘制
            0.0f,  1.0f, 0.0f, // 顶点
            -1.0f, 0.0f, 0.0f, // 左下角
            1.0f, 0.0f, 0.0f  // 右下角
    };

    // 设置三角形颜色和透明度（r,g,b,a）
    float color[] = {0.0f, 1.0f, 0f, 1.0f};//绿色不透明

    public Triangle() {
        // 初始化顶点字节缓冲区
        vertexBuffer = BufferUtil.getFloatBuffer(triangleCoords);

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

    private int mPositionHandle;
    private int mColorHandle;

    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    public void draw() {
        // 添加program到OpenGL ES环境中
        GLES20.glUseProgram(mProgram);

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
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        //glDrawElements 可以重新定义顶点的顺序，顶点的顺序由indices Buffer 指定
        //GLES20.glDrawElements(GLES20.GL_TRIANGLES,indices.length,GLES20.GL_UNSIGNED_INT,indexBuffer);

        // 禁用指向三角形的顶点数组
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}
