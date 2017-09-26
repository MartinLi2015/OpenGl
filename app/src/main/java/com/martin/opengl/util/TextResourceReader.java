package com.martin.opengl.util;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 */

public class TextResourceReader {

    public static String readTextFileFromeResource(Context context, int resourceId) {

        StringBuilder body = new StringBuilder();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            inputStream = context.getResources().openRawResource(resourceId);
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            String nexLine;
            while ((nexLine = bufferedReader.readLine()) != null) {
                body.append(nexLine);
                body.append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not open resource: " + resourceId, e);
        } catch (Resources.NotFoundException nfe) {
            throw new RuntimeException("Resource not found: " + resourceId, nfe);
        } finally {
            closeIO(bufferedReader, inputStreamReader, inputStream);
        }
        return body.toString();
    }

    public static void closeIO(Closeable... closeables) {

        try {
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    closeable.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
