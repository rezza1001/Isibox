package com.rzc.isibox.presentation.component;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.rzc.isibox.R;
import com.rzc.isibox.tools.Utility;


public class MyRelativeLayout extends RelativeLayout {

    private static final int     DEFAULT_COLOR      = 0;
    private int color = 0;
    private int lineColor = 0;
    private int lineSize = 0;
    private float radius = 0;
    private float topLeftRadius = 0;
    private float topRightRadius = 0;
    private float bottomRightRadius = 0;
    private float bottomLeftRadius = 0;

    protected AppCompatActivity mActivity;
    private RelativeLayout rv_01;


    public MyRelativeLayout(@NonNull Context context) {
        super(context);
    }

    public MyRelativeLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        @SuppressLint({"Recycle", "CustomViewStyleable"})
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyView);
        color = typedArray.getColor(R.styleable.MyView_m_color, DEFAULT_COLOR);
        lineColor = typedArray.getColor(R.styleable.MyView_mLineColor, DEFAULT_COLOR);
        lineSize = typedArray.getInt(R.styleable.MyView_mLineSize, 0);
        radius = typedArray.getFloat(R.styleable.MyView_m_radius, 0);
        radius = Utility.toUnitDP(context, radius);

        topLeftRadius = typedArray.getFloat(R.styleable.MyView_m_radiusLeftTop, 0);
        topLeftRadius = Utility.toUnitDP(context, topLeftRadius);

        topRightRadius = typedArray.getFloat(R.styleable.MyView_m_radiusRightTop, 0);
        topRightRadius = Utility.toUnitDP(context, topRightRadius);

        bottomRightRadius = typedArray.getFloat(R.styleable.MyView_m_radiusRightBottom, 0);
        bottomRightRadius = Utility.toUnitDP(context, bottomRightRadius);

        bottomLeftRadius = typedArray.getFloat(R.styleable.MyView_m_radiusLeftBottom, 0);
        bottomLeftRadius = Utility.toUnitDP(context, bottomLeftRadius);


        rv_01 = this;
        build();
    }

    private void build(){
        if (radius == 0){
            setBackgroundColor(color);
        }
        else {
            setBackground(getRectBackground(radius));
        }

        if (topLeftRadius != 0 || topRightRadius != 0 || bottomLeftRadius != 0 || bottomRightRadius != 0){
            setBackground(getRectBackground(topLeftRadius,topRightRadius,bottomRightRadius,bottomLeftRadius));
        }

        if (lineSize > 0){
            setBackground(getRectBackgroundLine(color, (int) radius,lineSize,lineColor ));
        }
    }


    public MyRelativeLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyRelativeLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setColor(int color) {
        this.color = color;
        build();
    }

    public void setLineColor(int lineColor){
        this.lineColor = lineColor;
        build();
    }

    public void setLineSize(int size){
        this.lineSize = size;
        build();
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

    private ShapeDrawable getRectBackground( float leftTop, float rightTop, float rightBottom, float leftBottom){
        RoundRectShape roundRectShape = new RoundRectShape(new float[]{
                leftTop, leftTop, rightTop, rightTop,
                rightBottom, rightBottom, leftBottom, leftBottom}, null, null);

        ShapeDrawable shapeDrawable = new ShapeDrawable(roundRectShape);
        shapeDrawable.getPaint().setColor(color);
        return shapeDrawable;
    }

    private GradientDrawable getRectBackgroundLine(int color, int radius, int lineSize, int lineColor){
        lineSize = Utility.toUnitDP(getContext(), lineSize);
        GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(getContext(), R.drawable.default_shape);
        if (drawable == null){
            return null;
        }
        drawable.setCornerRadius(radius);
        drawable.setColor(color);
        drawable.setStroke(lineSize, lineColor);
        return drawable;
    }
}
