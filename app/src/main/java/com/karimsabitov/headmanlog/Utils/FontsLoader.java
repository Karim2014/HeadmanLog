package com.karimsabitov.headmanlog.Utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by User on 17.11.2018.
 */

public class FontsLoader {
    private static FontsLoader ourInstance;

    public static final int ROBOTO_MEDIUM_0 = 0;
    public static final int ROBOTO_REGULAR =   1;
    public static final int ROBOTO_BOLD_0 =   2;
    public static final int ROBOTO_LIGHT =   3;
    public static final int ROBOTO_MEDIUM_ITALIC =   4;

    private Context mContext;

    private String[] fontPath = {
            "fonts/Roboto-Medium_0.ttf",
            "fonts/Roboto-Regular.ttf",
            "fonts/Roboto-Bold_0.ttf",
            "fonts/Roboto-Light_0.ttf",
            "fonts/Roboto-MediumItalic_0.ttf",
            "fonts/Roboto-Italic_0.ttf"
    };

    private Typeface[] fonts = new Typeface[fontPath.length];

    public static FontsLoader getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new FontsLoader(context);
        }
        return ourInstance;
    }

    public Typeface getTypeface(int fontId){
        if (fontId < 0 || fontId > fontPath.length) fontId = 1;
        return fonts[fontId];
    }

    private FontsLoader(Context context) {
        mContext = context;
        loadFonts();
    }

    private void loadFonts() {
        for (int i = 0; i < fonts.length; i++) {
            fonts[i] = Typeface.createFromAsset(mContext.getAssets(), fontPath[i]);
        }
    }
}
