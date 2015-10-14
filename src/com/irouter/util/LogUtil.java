package com.irouter.util;

/**
 * Created by whole on 15-4-1.
 * <p/>
 * 可以显示行号的Android Log 工具
 * Android 的Log 类只能以TAG的方式却分不同类的信息，
 * 利用反射的方式，将Log所在的行号和类名抓出来
 * 转交测试的时候如果看到log的消息就可以快速定位软件的bug
 */

import android.util.Log;

public class LogUtil {
    private static final boolean DEBUG = true;
    private static final String TAG = LogUtil.class.getSimpleName();

    public static void e(String... messages) {
        if (DEBUG) {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            if (elements.length < 4) {
                Log.e(TAG, "Stack to shallow");
            } else {
                String concreteTag = getConcreteTAG(elements);

                Log.e(concreteTag, concatMessage(messages));
            }
        }
    }

    public static void d(String... messages) {
        if (DEBUG) {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            if (elements.length < 3) {
                Log.e(TAG, "Stack to shallow");
            } else {
                String concreteTag = getConcreteTAG(elements);
                Log.d(concreteTag, concatMessage(messages));
            }
        }
    }

    public static void i(String... messages) {
        if (DEBUG) {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            if (elements.length < 3) {
                Log.e(TAG, "Stack to shallow");
            } else {
                String concreteTag = getConcreteTAG(elements);
                Log.i(concreteTag, concatMessage(messages));
            }
        }
    }

    public static void w(String... messages) {
        if (DEBUG) {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            if (elements.length < 3) {
                Log.e(TAG, "Stack to shallow");
            } else {
                String concreteTag = getConcreteTAG(elements);
                Log.w(concreteTag, concatMessage(messages));
            }
        }
    }

    public static void v(String... messages) {
        if (DEBUG) {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            if (elements.length < 3) {
                Log.e(TAG, "Stack to shallow");
            } else {
                String concreteTag = getConcreteTAG(elements);
                Log.v(concreteTag, concatMessage(messages));
            }
        }
    }

    private static String getConcreteTAG(StackTraceElement[] elements) {
        StringBuffer sb = new StringBuffer();

        String fullClassName = elements[3].getClassName();
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        String methodName = elements[3].getMethodName();
        int lineNumber = elements[3].getLineNumber();

        sb.append(className).append(".").append(methodName).append("():").append(lineNumber);
        return sb.toString();
    }

    private static String concatMessage(String... messages) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String msg : messages) {
            stringBuffer.append(msg).append(",");
        }
        return stringBuffer.toString();
    }
}