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

public class CubeSquareTexture extends Shape
{
    private float[] projectMatrix = new float[16];
    private float[] viewMatrix = new float[16];
    private float[] mVMatrix = new float[16];

    private float rotate = 0f;
    private float scale = 1f;
    private float trans = 0f;
    private float x = 0f,y = 1f;

    private View view;
    private float s = 0.8f;
    private float[][] coords = {
            {
                    -s,s,-s,s,s,s,s,s,-s,       //023
                    -s,s,-s,-s,s,s,s,s,s       //012
            },{
                    -s,s,s,-s,-s,s,s,s,s,       //152
                    s,s,s,-s,-s,s,s,-s,s       //256
            }, {
                    s,s,s,s,-s,s,s,-s,-s,       //267
                    s,s,s,s,-s,-s,s,s,-s,       //273
            }, {
                    s,s,-s,s,-s,-s,-s,-s,-s,    //374
                    s,s,-s,-s,-s,-s,-s,s,-s,    //340
            }, {
                    -s,s,-s,-s,-s,-s,-s,s,s,    //041
                    -s,s,s,-s,-s,-s,-s,-s,s,    //145
            }, {
                    -s,-s,s,-s,-s,-s,s,-s,s,    //546
                    s,-s,s,-s,-s,-s,s,-s,-s     //647
            }
    };

    private float[][] textureCoord = {
            {
                    0,1,1,0,1,1,
                    0,1,0,0,1,0
            }, {
                    0,1,0,0,1,1,
                    1,1,0,0,1,0
            }, {
                    0,1,0,0,1,0,
                    0,1,1,0,1,1
            }, {
                    0,1,0,0,1,0,
                    0,1,1,0,1,1
            },{
                    0,1,0,0,1,1,
                    1,1,0,0,1,0
            },{
                    0,1,0,0,1,1,
                    1,1,0,0,1,0
            }
    };

    private int[] resIds = {R.mipmap.ic_launcher,R.mipmap.timg,R.mipmap.ic_launcher,R.mipmap.timg,R.mipmap.ic_launcher,R.mipmap.timg};

    private Square[] square;

    public CubeSquareTexture(View view)
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

        square = new Square[coords.length];
        for (int i = 0; i < coords.length; i++)
        {
            square[i] = new Square(view,coords[i],textureCoord[i],resIds[i]);
            square[i].onSurfaceCreated(gl,config);
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height)
    {
        GLES20.glViewport(0,0,width,height);

        float ratio = (float)width/height;
        Matrix.frustumM(projectMatrix,0,-ratio,ratio,-1,1,3,30);
        Matrix.setLookAtM(viewMatrix,0,0f,0f,10f,0f,0f,0f,0f,1f,0f);
        Matrix.multiplyMM(mVMatrix,0,projectMatrix,0,viewMatrix,0);
    }

    @Override
    public void onDrawFrame(GL10 gl)
    {
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT|GLES20.GL_COLOR_BUFFER_BIT);

        //绕Y轴旋转
        Matrix.rotateM(mVMatrix,0, rotate,x, y, 0f);
        //translate
        //Matrix.translateM(mVMatrix,0,trans,0,0);
        //scale
        //Matrix.scaleM(mVMatrix,0,scale,scale,scale);

        for (int i = 0; i < coords.length; i++)
        {
            square[i].setvPMatrix(mVMatrix);
            square[i].onDrawFrame(gl);
        }
    }

    public void setInfo(float rotate,float trans,float scale,float x,float y)
    {
        this.rotate = rotate;
        this.trans = trans;
        this.scale = scale;
        this.x = x;
        this.y = y;
    }
}
