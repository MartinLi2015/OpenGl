package com.martin.airhockey1;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.martin.airhockey1.util.LogUtil;
import com.martin.airhockey1.util.ShaderHelper;
import com.martin.airhockey1.util.TextResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;

public class AirHockeyRender implements GLSurfaceView.Renderer {


    public static final int POSITION_COMPONENT_COUNT = 2;
    public static final int BYTES_PER_FLOAT = 4;
    private static final String U_COLOR = "u_Color";
    private static final String A_POSITION = "a_Position";
    private final FloatBuffer vertexData;
    private final Context context;
    private int program;
    private int uColorLocation;
    private int aPositionLocation;

    public AirHockeyRender(Context context) {
        float[] tableVerticesWithTriangles = {
                // Triangle 1
                0f, 0f,
                9f, 14f,
                0f, 14f,

                // Triangle 2
                0f, 0f,
                9f, 0f,
                9f, 14f,

                // Line 1
                0f, 7f,
                9f, 7f,

                //Mallets
                4.5f, 2f,
                4.5f, 12f

        };

        /*float[] tableVerticesWithTriangles = {
                // Triangle 1
                -0.5f, -0.5f,
                0.5f, 0.5f,
                -0.5f, 0.5f,
                // Triangle 2
                -0.5f, -0.5f,
                0.5f, -0.5f,
                0.5f, 0.5f,
                // Line 1
                -0.5f, 0f,
                0.5f, 0f,
                // Mallets
                0f, -0.25f,
                0f, 0.25f
        };*/

        vertexData = ByteBuffer.allocateDirect(tableVerticesWithTriangles.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexData.put(tableVerticesWithTriangles);
        this.context = context;

    }


    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        String vertexShaderSource = TextResourceReader.readTextFileFromeResource(context, R.raw.simple_vertex_shader);
        String fragmentShaderSource = TextResourceReader.readTextFileFromeResource(context, R.raw.simple_fragment_shader);
        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);

        program = ShaderHelper.lindProgram(vertexShader, fragmentShader);

        if (LogUtil.ON) {
            ShaderHelper.validateProgram(program);
        }
        glUseProgram(program);

        uColorLocation = glGetUniformLocation(program, U_COLOR);
        aPositionLocation = glGetAttribLocation(program, A_POSITION);

        vertexData.position(0);
        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexData);

        glEnableVertexAttribArray(aPositionLocation);

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        //
        glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 0, 6);

        //Line 1
        glUniform4f(uColorLocation, 1.0f, 0f, 0f, 1.0f);
        glDrawArrays(GL_LINES, 6, 2);

        // Draw the first mallet blue.
        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        glDrawArrays(GL_POINTS, 8, 1);
        // Draw the second mallet red.
        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_POINTS, 9, 1);
    }
}