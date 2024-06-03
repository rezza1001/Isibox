package com.rzc.isibox.presentation.component;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rzc.isibox.R;
import com.rzc.isibox.tools.Utility;

public class MyLinearLayout extends LinearLayout {

    private static final int     DEFAULT_COLOR      = 0;
    private int color = 0;
    private float radius = 0;

    protected AppCompatActivity mActivity;
    private LinearLayout rv_01;


    public MyLinearLayout(@NonNull Context context) {
        super(context);
    }

    public MyLinearLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        @SuppressLint({"Recycle", "CustomViewStyleable"})
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyView);
        color = typedArray.getColor(R.styleable.MyView_m_color, DEFAULT_COLOR);
        radius = typedArray.getFloat(R.styleable.MyView_m_radius, 0);
        radius = Utility.toUnitDP(context, (int) radius);
        rv_01 = this;

        if (radius == 0){
            setBackgroundColor(color);
        }
        else {
            setBackground(getRectBackground(radius));
        }
    }


    public MyLinearLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyLinearLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setColor(int color) {
        this.color = color;
        rv_01.setBackgroundColor(color);
    }

    public void create(){
        mActivity = scanForActivity(getContext());
    }


    protected AppCompatActivity scanForActivity(Context context) {
        if (context == null)
            return null;
        else if (context instanceof Activity)
            return (AppCompatActivity)context;
        else if (context instanceof ContextWrapper)
            return scanForActivity(((ContextWrapper)context).getBaseContext());

        return null;
    }

    private ShapeDrawable getRectBackground(float radius){
        RoundRectShape roundRectShape = new RoundRectShape(new float[]{
                radius, radius, radius, radius,
                radius, radius, radius, radius}, null, null);

        ShapeDrawable shapeDrawable = new ShapeDrawable(roundRectShape);
        shapeDrawable.getPaint().setColor(color);
        return shapeDrawable;
    }
}
