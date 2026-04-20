package com.sentox.mobiletest.base.log;

import android.util.Log;

import com.sentox.mobiletest.BuildConfig;

import java.util.Arrays;


/**
 * Log封装类
 *
 * @author skye
 */
public class L {

    public static boolean sDebug = BuildConfig.LOG_DEBUG;

    public static void setDEBUG(boolean debug) {
        sDebug = debug;
    }

    /**
     * @param tag 标识符
     * @param msg 打印信息
     */
    public static void v(String tag, String msg) {
        if (sDebug) {
            Log.v(tag, msg);
        }
    }

    /**
     * @param tag 标识符
     * @param msg 打印信息
     * @param tr  抛出的异常
     */
    public static void v(String tag, String msg, Throwable tr) {
        if (sDebug) {
            Log.v(tag, msg, tr);
        }
    }

    /**
     * @param tag 标识符
     * @param msg 打印信息
     */
    public static void v(String tag, Object... msg) {
        if (sDebug) {
            Log.v(tag, Arrays.toString(msg));
        }
    }

    /**
     * @param tag 标识符
     * @param msg 打印信息
     */
    public static void d(String tag, String msg) {
        if (sDebug) {
            Log.d(tag, msg);
        }
    }

    /**
     * @param tag 标识符
     * @param msg 打印信息
     * @param tr  抛出的异常
     */
    public static void d(String tag, String msg, Throwable tr) {
        if (sDebug) {
            Log.d(tag, msg, tr);
        }
    }

    /**
     * @param tag 标识符
     * @param msg 打印信息
     */
    public static void d(String tag, Object... msg) {
        if (sDebug) {
            Log.d(tag, Arrays.toString(msg));
        }
    }

    /**
     * @param tag 标识符
     * @param msg 打印信息
     */
    public static void info(String tag, String msg) {
        if (sDebug) {
            Log.i(tag, msg);
        }
    }

    /**
     * @param tag 标识符
     * @param msg 打印信息
     */
    public static void info(String tag, Object... msg) {
        if (sDebug) {
            Log.i(tag, Arrays.toString(msg));
        }
    }

    /**
     * @param tag 标识符
     * @param msg 打印信息
     * @param tr  抛出的异常
     */
    public static void info(String tag, String msg, Throwable tr) {
        if (sDebug) {
            Log.i(tag, msg, tr);
        }
    }

    /**
     * @param tag 标识符
     * @param msg 打印信息
     */
    public static void w(String tag, String msg) {
        if (sDebug) {
            Log.w(tag, msg);
        }
    }

    /**
     * @param tag 标识符
     * @param msg 打印信息
     */
    public static void w(String tag, Object... msg) {
        if (sDebug) {
            Log.w(tag, Arrays.toString(msg));
        }
    }

    /**
     * @param tag 标识符
     * @param msg 打印信息
     * @param tr  抛出的异常
     */
    public static void w(String tag, String msg, Throwable tr) {
        if (sDebug) {
            Log.w(tag, msg, tr);
        }
    }

    /**
     * @param tag 标识符
     * @param msg 打印信息
     */
    public static void e(String tag, String msg) {
        if (sDebug) {
            Log.e(tag, msg);
        }
    }

    /**
     * @param tag 标识符
     * @param msg 打印信息
     */
    public static void e(String tag, Object... msg) {
        if (sDebug) {
            Log.e(tag, Arrays.toString(msg));
        }
    }

    /**
     * @param tag 标识符
     * @param msg 打印信息
     * @param tr  抛出的异常
     */
    public static void e(String tag, String msg, Throwable tr) {
        if (sDebug) {
            Log.e(tag, msg, tr);
        }
    }
}
