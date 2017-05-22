package com.chyss.myapplication.widget.opengl.utils;

import android.opengl.GLES20;

import com.chyss.myapplication.widget.opengl.MglRenderer;

/**
 * @author chyss 2017-05-05
 */

public class ShaderUtil
{
    public static final String position = "vPosition";
    public static final String color = "vColor";

    private static final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = vPosition;" +
                    "}";

    private static final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    /**
     * 编译shader代码
     * @return
     */
    public static int getVertexShader()
    {
        return loadShader(GLES20.GL_VERTEX_SHADER,vertexShaderCode);
    }

    /**
     * 编译shader代码
     * @return
     */
    public static int getFragmentShader()
    {
        return loadShader(GLES20.GL_FRAGMENT_SHADER,fragmentShaderCode);
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
