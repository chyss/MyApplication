package com.chyss.myapplication.widget.opengl.shape;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.view.View;

import com.chyss.myapplication.widget.opengl.utils.AssetsUtil;
import com.chyss.myapplication.widget.opengl.utils.BufferUtil;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author chyss 2017-05-23
 */
public class BallLight extends Shape
{

    //定点的缓存
    private FloatBuffer vertexBuffer;
    //OpenGL ES Program
    private int program;
    //设置三角形顶点数组
    private float[] coords;

    private float r = 1f;
    private float unit = 2;


    private int positionHandle;
    private int vMatrixHandle;

    float[] projectMatrix = new float[16];
    float[] viewMatrix = new float[16];
    float[] vPMatrix = new float[16];

    private View view;

    public BallLight(View view)
    {
        this.view = view;
        initCoords();
        // 初始化顶点字节缓冲区
        vertexBuffer = BufferUtil.getFloatBuffer(coords);
    }

    private void initCoords()
    {
        List<Float> list = new ArrayList<Float>();
        float r1,r2;
        float h1,h2;
        float sin,cos;
        for (float i = -90; i < 90; i+=unit)
        {
            r1 = (float)(r*Math.cos(i*Math.PI/180f));
            r2 = ((float)(r*Math.cos((i+unit)*Math.PI/180f)));
            h1 = (float)(r*Math.sin(i*Math.PI/180f));
            h2 = (float)(r*Math.sin((i+unit)*Math.PI/180f));
            for (float j = 0; j <= 360; j+=unit)
            {
                sin = -(float)(Math.sin(j*Math.PI/180f));
                cos = (float)(Math.cos(j*Math.PI/180f));

                list.add(r2*cos);
                list.add(h2);
                list.add(r2*sin);

                list.add(r1*cos);
                list.add(h1);
                list.add(r1*sin);

//                list.add((float)(r*Math.cos(i*Math.PI/180f)*Math.cos(j*Math.PI/180f)));
//                list.add((float)(r*Math.cos(i*Math.PI/180f)*Math.sin(j*Math.PI/180f)));
//                list.add((float)(r*Math.sin(i*Math.PI/180f)));
//                list.add((float)(r*Math.cos((i+unit)*Math.PI/180f)*Math.cos((j+unit)*Math.PI/180f)));
//                list.add((float)(r*Math.cos((i+unit)*Math.PI/180f)*Math.sin((j+unit)*Math.PI/180f)));
//                list.add((float)(r*Math.sin((i+unit)*Math.PI/180f)));
            }
        }

        coords = new float[list.size()];
        for (int j = 0; j < coords.length; j++)
        {
            coords[j] = list.get(j);
        }
    }

    /**
     * 仅调用一次，用于设置view的OpenGLES环境
     */
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config)
    {
        //设置清屏背景色RGBA,白色不透明
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

//        GLES20.glFrontFace(GLES20.GL_CCW);//设置顺时针卷染为正面.
//        GLES20.glEnable(GLES20.GL_CULL_FACE);//打开背面剪裁
//        GLES20.glCullFace(GLES20.GL_BACK); //剪裁背面

        // 编译shader代码
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, AssetsUtil.loadFromAssetsFile("shader/vBallWithLight.sh",view.getResources()));
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER,AssetsUtil.loadFromAssetsFile("shader/cBallWithLight.sh",view.getResources()));

        // 创建空的OpenGL ES Program
        program = GLES20.glCreateProgram();

        // 将vertex shader添加到program
        GLES20.glAttachShader(program, vertexShader);

        // 将fragment shader添加到program
        GLES20.glAttachShader(program, fragmentShader);

        // 创建可执行的 OpenGL ES program
        GLES20.glLinkProgram(program);
    }

    /**
     * 如果view的几何形状发生变化就调用，例如当竖屏变为横屏时
     */
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height)
    {
        //设置视窗大小及位置
        GLES20.glViewport(0, 0, width, height);

        float radio = (float)width/height;

        //设置透视投影
        Matrix.frustumM(projectMatrix, 0, -radio, radio, -1, 1, 3, 20);
        //设置相机位置
        Matrix.setLookAtM(viewMatrix, 0, 0.0f, 0.0f, 10.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        //计算变换矩阵
        Matrix.multiplyMM(vPMatrix,0,projectMatrix,0,viewMatrix,0);

//        Matrix.frustumM(projectMatrix,0,-radio,radio,-1,1,1,10);
//        Matrix.setLookAtM(viewMatrix,0,3f,3f,0f,0f,0f,0f,0f,0f,-1f);
//        Matrix.multiplyMM(vPMatrix,0,projectMatrix,0,viewMatrix,0);
    }

    /**
     * 每次View被重绘时被调用
     */
    @Override
    public void onDrawFrame(GL10 gl)
    {
        //清除深度缓冲与颜色缓冲
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT|GLES20.GL_DEPTH_BUFFER_BIT);

        // 添加program到OpenGL ES环境中
        GLES20.glUseProgram(program);

        vMatrixHandle = GLES20.glGetUniformLocation(program,"vMatrix");
        GLES20.glUniformMatrix4fv(vMatrixHandle,1,false,vPMatrix,0);

        // 获取指向vertex shader的成员vPosition的handle
        positionHandle = GLES20.glGetAttribLocation(program, "vPosition");

        // 启用一个指向三角形的顶点数组的handle
        GLES20.glEnableVertexAttribArray(positionHandle);

        //准备三角形的坐标数据
        GLES20.glVertexAttribPointer(positionHandle,  //变量的内存位置
                3,                      //每一个坐标的数据个数
                GLES20.GL_FLOAT,        //数据的类型
                false,                  //converted to a range from -1 to 1 for signed data, or 0 to 1 for unsigned data
                3*4,                  //the spacing between elements,每个顶点占用的空间
                vertexBuffer);         //the array containing the data,顶点缓冲数组

        // glDrawArrays 使用VetexBuffer 来绘制，顶点的顺序由vertexBuffer中的顺序指定。
        // GLES20.GL_TRIANGLES 绘制的类型为3角形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, coords.length / 3);

        //glDrawElements 可以重新定义顶点的顺序，顶点的顺序由indices Buffer 指定
        //GLES20.glDrawElements(GLES20.GL_TRIANGLES,indices.length,GLES20.GL_UNSIGNED_INT,indexBuffer);

        // 禁用指向三角形的顶点数组
        GLES20.glDisableVertexAttribArray(positionHandle);
    }
}
