package com.huangdali.utils;

import android.content.Context;
import android.content.res.Resources;

public class ScreenUtil {
    /**
     * 将dp转换成px
     * @param context
     * @param dpValue
     * @return
     */
    public static int dp2px(Context context, float dpValue){
        final float scale = context.getResources ().getDisplayMetrics ().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将像素转换成dp
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context,float pxValue){
        final float scale = context.getResources ().getDisplayMetrics ().density;
        return (int) (pxValue / scale + 0.5f);
    }
    }

