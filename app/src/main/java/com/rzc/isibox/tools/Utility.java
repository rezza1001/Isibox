package com.rzc.isibox.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;

import com.rzc.isibox.R;
import com.rzc.isibox.presentation.component.AlertDialog;
import com.rzc.isibox.presentation.component.MyToast;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utility {

    public static void expand(final View v) {
        int matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) v.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
        int wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(matchParentMeasureSpec, wrapContentMeasureSpec);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Expansion speed of 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Collapse speed of 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static Date convert2Date(String date, String format){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format,new Locale("id"));
        Date mDate = new Date();
        try {
            mDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mDate;
    }

    public static String getDateString(Date pDate, String format){
        DateFormat format1 = new SimpleDateFormat(format,new Locale("id"));
        return format1.format(pDate);
    }

    public static SpannableString BoldText(Context pContext, String pText, int start, int end, int color){
        SpannableString content = new SpannableString(pText);
        Typeface font =  ResourcesCompat.getFont(pContext, R.font.roboto_bold);
        content.setSpan(new CustomTypefaceSpan("", font), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        content.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return content;
    }

    public static SpannableString BoldText(Context context, String allText, String boldText, int color){

        int startName   = allText.toUpperCase().indexOf(boldText.toUpperCase());
        int endName     = startName+ (boldText.length());
        return BoldText(context, allText, startName, endName, color);
    }



    public static void LogDbug(String tag, String message){
        Log.d(tag, message);
    }

    public static void showToastError(Context context, String message){
        MyToast myToast = new MyToast(context, null);
        myToast.setIcon(R.drawable.ic_error, Color.parseColor("#B71C1C"));
        myToast.setBackground(Color.parseColor("#CAD3DF"));
        myToast.setTextColor(Color.parseColor("#7F0000"));
        myToast.setMessage(message);
        myToast.show(Toast.LENGTH_LONG, Gravity.CENTER, 0,0);
    }

    public static void showAlertError(Context context, String message){
        AlertDialog dialog = new AlertDialog(context);
        dialog.showFailed(message);
    }
    public static void showToastSuccess(Context context, String message){
        MyToast myToast = new MyToast(context, null);
        myToast.setIcon(R.drawable.ic_success, Color.parseColor("#1BA13E"));
        myToast.setBackground(Color.parseColor("#E5F9E5"));
        myToast.setTextColor(Color.parseColor("#1BA13E"));
        myToast.setMessage(message);
        myToast.show(Toast.LENGTH_LONG, Gravity.CENTER, 0,0);
    }

    public static void showKeyboard(Context context, EditText editText){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void hideKeyboard(Context context, EditText editText){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }


    public static int toUnitDP(Context context, int value){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }

    public static int toUnitDP(Context context, float value){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }
    public static int toUnitSP(Context context, int value){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, context.getResources().getDisplayMetrics());
    }

    @SuppressLint("HardwareIds")
    public static String getDeviceId(Context ctx) {
        return Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static Typeface getFontFamily(Context context,int font ){
        return   ResourcesCompat.getFont(context, font);
    }

    public static String getRequiredMessage(Context context, String value){
        String res = context.getResources().getString(R.string.is_required);
        res = res.replace("VALUE", value);
        return res;
    }

    public static String ConvertPassword(String inputPassword){
        StringBuilder sEncryption = new StringBuilder();
        for (int x = 0; x < inputPassword.length(); x++) {
            sEncryption.append(inputPassword.charAt(x) + 13);
        }
        return sEncryption.toString();
    }

    public static ShapeDrawable getOvalBackground(int color){
        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.getPaint().setColor(color);
        return shapeDrawable;
    }
    public static String getBundleString(Bundle bundle, String key){
        if (bundle.getString(key) == null){
            return "";
        }
        else {
            return bundle.getString(key);
        }
    }

    public static String loadJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @SuppressLint("DiscouragedApi")
    public static int getImageResourceId(Context context, String imageName) {
        return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }

}
