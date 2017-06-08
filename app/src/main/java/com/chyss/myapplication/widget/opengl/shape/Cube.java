package com.chyss.myapplication.widget.opengl.shape;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.chyss.myapplication.widget.opengl.utils.BufferUtil;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author chyss 2017-05-05
 */

public class Cube extends Shape
{
    private final String vertexShaderCode =
            "attribute vec4 vPosition;"+
                    "uniform mat4 vMatrix;"+
                    "varying vec4 vColor;"+
                    "attribute vec4 aColor;"+
                    "void main(){"+
                    "gl_Position = vMatrix*vPosition;"+
                    "vColor = aColor;"+
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;"+
                    "varying vec4 vColor;"+
                    "void main(){"+
                    "gl_FragColor = vColor;"+
                    "}";

    private float[] projectMatrix = new float[16];
    private float[] viewMatrix = new float[16];
    private float[] mVMatrix = new float[16];

    private int mMatrixHandle;
    private int mPositionHandle;
    private int mColorHandle;

    private FloatBuffer vertexBuffer;
    private ShortBuffer indexBuffer;
    private FloatBuffer colorBuffer;
    private int program;

    private int vertexShader,fragmentShader;

    private float s = 0.8f;
    private float[] coords = {
            -s,s,-s,    // 0
            -s,s,s,     // 1
            s,s,s,      // 2
            s,s,-s,     // 3
            -s,-s,-s,   // 4
            -s,-s,s,    // 5
            s,-s,s,     // 6
            s,-s,-s    // 7
    };

    private short[] index = {

            0,2,3,
            0,1,2,
            1,5,2,
            2,5,6,
            2,6,7,
            2,7,3,
            3,7,4,
            3,4,0,
            0,4,1,
            1,4,5,
            5,4,6,
            6,4,7
    };

    private float[] color = {
            0f,0f,1f,1.0f,
            0f,1f,0f,1.0f,
            0f,1f,0f,1.0f,
            0f,0f,1f,1.0f,
            0f,0f,1f,1.0f,
            1.0f,0f,0f,1.0f,
            1.0f,0f,0f,1.0f,
            0f,0f,1f,1.0f

    };

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config)
    {
        GLES20.glClearColor(1f,1f,1f,1.0f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        vertexBuffer = BufferUtil.getFloatBuffer(coords);
        indexBuffer = BufferUtil.getShortBuffer(index);
        colorBuffer = BufferUtil.getFloatBuffer(color);

        vertexShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        GLES20.glShaderSource(vertexShader,vertexShaderCode);
        GLES20.glCompileShader(vertexShader);

        fragmentShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        GLES20.glShaderSource(fragmentShader,fragmentShaderCode);
        GLES20.glCompileShader(fragmentShader);

        program = GLES20.glCreateProgram();
        GLES20.glAttachShader(program,vertexShader);
        GLES20.glAttachShader(program,fragmentShader);
        GLES20.glLinkProgram(program);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height)
    {
        GLES20.glViewport(0,0,width,height);

        float ratio = (float)width/height;
        Matrix.frustumM(projectMatrix,0,-ratio,ratio,-1,1,3,30);
        Matrix.setLookAtM(viewMatrix,0,5f,5f,10f,0f,0f,0f,0f,0f,1f);
        Matrix.multiplyMM(mVMatrix,0,projectMatrix,0,viewMatrix,0);
    }

    @Override
    public void onDrawFrame(GL10 gl)
    {
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT|GLES20.GL_COLOR_BUFFER_BIT);

        GLES20.glUseProgram(program);
        mMatrixHandle = GLES20.glGetUniformLocation(program,"vMatrix");
        GLES20.glUniformMatrix4fv(mMatrixHandle,1,false,mVMatrix,0);

        mPositionHandle = GLES20.glGetAttribLocation(program,"vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle,3,GLES20.GL_FLOAT,false,0,vertexBuffer);

//        mColorHandle = GLES20.glGetUniformLocation(program,"vColor");
//        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        mColorHandle = GLES20.glGetAttribLocation(program,"aColor");
        GLES20.glEnableVertexAttribArray(mColorHandle);
        GLES20.glVertexAttribPointer(mColorHandle,4,GLES20.GL_FLOAT,false,0,colorBuffer);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES,index.length,GLES20.GL_UNSIGNED_SHORT,indexBuffer);

        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}
