package com.karimsabitov.headmanlog.UI;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.karimsabitov.headmanlog.R;
import com.karimsabitov.headmanlog.Utils.FontsLoader;

/**
 * Created by User on 25.01.2019.
 */

public class EditTextFS extends AppCompatEditText {

    public EditTextFS(Context context) {
        super(context);
    }

    public EditTextFS(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public EditTextFS(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TextViewFontSupport);
        int font = array.getInt(R.styleable.TextViewFontSupport_customFont, 0);
        Typeface typeface = FontsLoader.getInstance(context).getTypeface(font);
        setTypeface(typeface);
        array.recycle();
    }


}
