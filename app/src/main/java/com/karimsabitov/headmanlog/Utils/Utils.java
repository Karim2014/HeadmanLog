package com.karimsabitov.headmanlog.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;

import com.amulyakhare.textdrawable.TextDrawable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by User on 28.08.2018.
 */

public class Utils {

    public static boolean prefReadBool(Context ctx, String name, String param, boolean default_) {
        return ctx.getSharedPreferences(name, 0).getBoolean(param, default_);
    }

    public static void prefWriteBool(Context ctx, String name, String param, boolean val) {
        SharedPreferences.Editor ed = ctx.getSharedPreferences(name, 0).edit();
        ed.putBoolean(param, val);
        ed.apply();

    }

    public static String formatDate(String pattern, Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static TextDrawable roundTextIcon(String text){
        return TextDrawable.builder().beginConfig()
                .textColor(Color.WHITE)
                .useFont(Typeface.DEFAULT)
                .toUpperCase()
                .endConfig()
                .buildRound(text, Color.parseColor("#455a64"));
    }

    public static void showView(View view){
        view.animate().alpha(1).start();
    }

    public static void hideView(View view) {
        view.animate().alpha(0).start();
    }
}
