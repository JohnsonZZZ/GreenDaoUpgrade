package com.github.mhlistener.greendaoupgradelib;

import android.util.Log;

/**
 * Log信息统一管理类
 */
public class LogUtil {
    private static final String TAG = "LogUtil";
	private static boolean isDebug = true;

	public static void setDebug(boolean debug){
		isDebug = debug;
	}

    public static void e(String tag, String msg){
        if(isDebug){
            Log.e(tag, msg);
        }
    }
    public static void d(String tag, String msg){
	    if(isDebug){
		    Log.d(tag, msg);
	    }
    }
    public static void v(String tag, String msg){
	    if(isDebug){
		    Log.v(tag, msg);
	    }
    }
    public static void w(String tag, String msg){
	    if(isDebug){
		    Log.w(tag, msg);
	    }
    }
    public static void i(String tag, String msg){
	    if(isDebug){
		    Log.i(tag, msg);
	    }
    }

    public static void e(String msg){
	    if(isDebug){
		    Log.e(TAG, msg);
	    }
    }
    public static void d(String msg){
	    if(isDebug){
		    Log.d(TAG, msg);
	    }
    }
    public static void v(String msg){
	    if(isDebug){
		    Log.v(TAG, msg);
	    }
    }
    public static void w(String msg){
	    if(isDebug){
		    Log.w(TAG, msg);
	    }
    }
    public static void i(String msg){
	    if(isDebug){
		    Log.i(TAG, msg);
	    }
    }

}
