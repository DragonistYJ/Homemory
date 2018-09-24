package com.example.dragonist.homemory.Utils;

import android.content.Context;
import android.util.TypedValue;
import android.widget.CalendarView;

public class Screen {
    public static float dp2px(Context context, int dpVal) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    public static float sp2px(CalendarView context, float spVal) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    public static int getDisplayWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getDisplayHeight(Context context) {
        return  context.getResources().getDisplayMetrics().heightPixels;
    }
}
