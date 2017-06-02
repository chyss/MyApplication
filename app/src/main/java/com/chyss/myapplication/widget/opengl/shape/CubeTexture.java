package com.chyss.myapplication.widget.opengl.shape;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.view.View;

import com.chyss.myapplication.R;
import com.chyss.myapplication.widget.opengl.utils.BufferUtil;
import com.chyss.myapplication.widget.opengl.utils.TextureUtil;

import java.nio.FloatBuffer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author chyss 2017-05-05
 */

public class CubeTexture extends Shape
{
    private final String vertexShaderCode =
            "attribute vec4 vPosition;"+
                    "uniform mat4 vMatrix;"+
                    "attribute vec2 a_TextureCoordinates;"+
                    "varying vec2 v_TextureCoordinates;"+
                    "void main(){"+
                    "gl_Position = vMatrix*vPosition;"+
                    "v_TextureCoordinates = a_TextureCoordinates;"+
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;"+
                    "uniform sampler2D u_TextureUnit;"+
                    "varying vec2 v_TextureCoordinates;"+
                    "void main(){"+
                    "gl_FragColor = texture2D(u_TextureUnit, v_TextureCoordinates);"+
                    "}";

    private float[] projectMatrix = new float[16];
    private float[] viewMatrix = new float[16];
    private float[] mVMatrix = new float[16];

    private int mMatrixHandle;
    private int mPositionHandle;
    private int mTextureHandle;
    private int mTextureCoordHandle;

    private FloatBuffer vertexBuffer;
    private FloatBuffer textureCoordBuffer;
    private int program;
    private int texture;

    private View view;
    private float rotate = 0f;
    private float scale = 1f;
    private float trans = 0f;

    private int vertexShader,fragmentShader;

    private float s = 0.8f;
    private float[] coords = {
            -s,s,-s,s,s,s,s,s,-s,       //023
            -s,s,-s,-s,s,s,s,s,s,       //012
            -s,s,s,-s,-s,s,s,s,s,       //152
            s,s,s,-s,-s,s,s,-s,s,       //256
            s,s,s,s,-s,s,s,-s,-s,       //267
            s,s,s,s,-s,-s,s,s,-s,       //273
            s,s,-s,s,-s,-s,-s,-s,-s,    //374
            s,s,-s,-s,-s,-s,-s,s,-s,    //340
            -s,s,-s,-s,-s,-s,-s,s,s,    //041
            -s,s,s,-s,-s,-s,-s,-s,s,    //145
            -s,-s,s,-s,-s,-s,s,-s,s,    //546
            s,-s,s,-s,-s,-s,s,-s,-s     //647

//            -s,s,-s,    // 0
//            -s,s,s,     // 1
//            s,s,s,      // 2
//            s,s,-s,     // 3
//            -s,-s,-s,   // 4
//            -s,-s,s,    // 5
//            s,-s,s,     // 6
//            s,-s,-s,    // 7
    };;

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

    private float[] textureCoord = {

            0,1,1,0,1,1,
            0,1,0,0,1,0,
            0,1,0,0,1,1,
            1,1,0,0,1,0,
            0,1,0,0,1,0,
            0,1,1,0,1,1,
            0,1,0,0,1,0,
            0,1,1,0,1,1,
            0,1,0,0,1,1,
            1,1,0,0,1,0,
            0,1,0,0,1,1,
            1,1,0,0,1,0
    };

    public CubeTexture(View view)
    {
        this.view = view;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config)
    {
        GLES20.glClearColor(1f,1f,1f,1.0f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        GLES20.glFrontFace(GLES20.GL_CCW);
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glCullFace(GLES20.GL_BACK);

        vertexBuffer = BufferUtil.getFloatBuffer(coords);
        textureCoordBuffer = BufferUtil.getFloatBuffer(textureCoord);

        vertexShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        GLES20.glShaderSource(vertexShader,vertexShaderCode);
        GLES20.glCompileShader(vertexShader);

        fragmentShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        GLES20.glShaderSource(fragmentShader,fragmentShaderCode);
        GLES20.glCompileShader(fragmentShader);

        //texture = new int[resIds.length];
        texture = TextureUtil.loadTexture(view.getContext(), R.mipmap.ic_launcher);

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

        //绕Y轴旋转
        Matrix.rotateM(mVMatrix,0, rotate,0f, 1f, 0f);
        //translate
        Matrix.translateM(mVMatrix,0,trans,0,0);
        //scale
        Matrix.scaleM(mVMatrix,0,scale,scale,scale);

        GLES20.glUseProgram(program);
        mMatrixHandle = GLES20.glGetUniformLocation(program,"vMatrix");
        GLES20.glUniformMatrix4fv(mMatrixHandle,1,false,mVMatrix,0);

        mTextureHandle = GLES20.glGetUniformLocation(program,"u_TextureUnit");
        //激活纹理单元，GL_TEXTURE0代表纹理单元0，GL_TEXTURE1代表纹理单元1，以此类推。OpenGL使用纹理单元来表示被绘制的纹理
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        //绑定纹理到这个纹理单元
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture);
        //把选定的纹理单元传给片段着色器中的u_TextureUnit，
        GLES20.glUniform1i(mTextureHandle, 0);

        mPositionHandle = GLES20.glGetAttribLocation(program,"vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle,3,GLES20.GL_FLOAT,false,0,vertexBuffer);

        mTextureCoordHandle = GLES20.glGetAttribLocation(program,"a_TextureCoordinates");
        GLES20.glEnableVertexAttribArray(mTextureCoordHandle);
        GLES20.glVertexAttribPointer(mTextureCoordHandle,2,GLES20.GL_FLOAT,false,0,textureCoordBuffer);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,coords.length/3);

        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }

    public void setInfo(float rotate,float trans,float scale)
    {
        this.rotate = rotate;
        this.trans = trans;
        this.scale = scale;
    }
}
