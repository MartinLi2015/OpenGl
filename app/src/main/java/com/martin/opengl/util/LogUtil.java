package com.martin.opengl.util;

import android.util.Log;

/**
 * Created by martin on 2017/9/21.
 * print log
 */

public class LogUtil {
    public static final boolean ON = true;

    public static void w(String tag,String msg){
        if(ON){
            Log.w(tag, msg);
        }
    }
    public static void v(String tag,String msg){
        if(ON){
            Log.v(tag, msg);
        }
    }


}
