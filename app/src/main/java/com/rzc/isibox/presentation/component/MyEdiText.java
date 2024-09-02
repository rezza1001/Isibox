package com.rzc.isibox.presentation.component;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyView;
import com.rzc.isibox.tools.MyCurrency;

public class MyEdiText extends MyView {
    public enum TYPE {
        TEXT, MULTILINE, NUMBER,PHONE, CURRENCY, SELECT, PIN,PASSWORD
    }

    private TextView tv_counter,tv_input;
    private EditText et_input;
    private TYPE type;
    private RelativeLayout rv_selectInp,rv_icRight;
    private ImageView iv_iconSelect,iv_iconRight;

    private String mNominal ;
    private String mTitle = "";

    private int pMaxLength = 0;
    private Object object;

    public MyEdiText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int setLayout() {
        return R.layout.component_view_editext;
    }

    @Override
    protected void initLayout() {
        et_input = findViewById(R.id.et_name);
        tv_counter = findViewById(R.id.tv_counter);
        rv_selectInp = findViewById(R.id.rv_selectInp);
        tv_input = findViewById(R.id.tv_input);
        iv_iconSelect = findViewById(R.id.iv_iconSelect);
        rv_icRight = findViewById(R.id.rv_icRight);
        iv_iconRight = findViewById(R.id.iv_iconRight);
        tv_counter.setVisibility(GONE);
        rv_selectInp.setVisibility(GONE);
        rv_icRight.setVisibility(GONE);
        rv_icRight.setTag(0);

        et_input.setBackgroundResource(R.drawable.bkg_editext);
    }

    @Override
    protected void initListener() {
        et_input.setOnFocusChangeListener((view, b) -> {
            if (b){
                et_input.setBackgroundResource(R.drawable.bkg_editext_focus);
            }
            else {
                et_input.setBackgroundResource(R.drawable.bkg_editext);
            }
        });

        rv_selectInp.setOnClickListener(view -> {
            if (onActionListener != null){
                onActionListener.onSelect(this);
            }
        });

        rv_icRight.setOnClickListener(v -> {
            if (type == TYPE.PASSWORD){
                int tag = Integer.parseInt(rv_icRight.getTag().toString());
                if (tag == 0){
                    rv_icRight.setTag(1);
                    et_input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                    setRightIcon(R.drawable.ic_eye_on, ContextCompat.getColor(mActivity, R.color.primary));
                }
                else {
                    rv_icRight.setTag(0);
                    et_input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    setRightIcon(R.drawable.ic_eye_off, ContextCompat.getColor(mActivity, R.color.grey2));
                }
                et_input.setSelection(getValue().length());
            }
        });
    }

    public void create(TYPE type, String title) {
        super.create();
        this.type = type;
        mTitle = title;
        String hint = mActivity.getResources().getString(R.string.insert) + " "+ title+"..";
        et_input.setHint(hint);
        if (type == TYPE.SELECT){
            hint = mActivity.getResources().getString(R.string.choose) + " "+ title+"..";
            setHint(hint);
        }
        setType(type);
    }

    public void setMaxLine(int max){
        et_input.setSingleLine(false);
        et_input.setMaxLines(max);
    }


    public void setType(TYPE pType){
        Log.d("MyEditext","type "+pType);
        type = pType;
        if (type == TYPE.TEXT){
            et_input.setSingleLine();
            et_input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        }
        else  if (type == TYPE.PHONE){
            et_input.setSingleLine();
            et_input.setInputType(InputType.TYPE_CLASS_PHONE | InputType.TYPE_TEXT_VARIATION_PHONETIC);
        }
        else  if (type == TYPE.NUMBER){
            et_input.setSingleLine();
            et_input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        }
        else  if (type == TYPE.PIN){
            et_input.setSingleLine();
            et_input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
            et_input.setLines(1);
            setMaxLength(6);
        }
        else  if (type == TYPE.PASSWORD){
            et_input.setSingleLine();
            et_input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            et_input.setLines(1);
            rv_icRight.setVisibility(VISIBLE);
            rv_icRight.setTag(0);
            setRightIcon(R.drawable.ic_eye_off, ContextCompat.getColor(mActivity, R.color.grey2));
        }
        else  if (type == TYPE.MULTILINE){
            et_input.setSingleLine(false);
            et_input.setMaxLines(3);
            et_input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) et_input.getLayoutParams();
            lp.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
            et_input.setLayoutParams(lp);
        }
        else  if (type == TYPE.CURRENCY){
            registerCurrency();
        }
        else  if (type == TYPE.SELECT){
            rv_selectInp.setVisibility(VISIBLE);
            et_input.setVisibility(INVISIBLE);
            et_input.setEnabled(false);
        }
    }

    public void setTextInputSize(int size){
        et_input.setTextSize(size);
        tv_input.setTextSize(size);
    }

    public void setDisable(){
        et_input.setBackgroundResource(R.drawable.bkg_editext_disable);
        et_input.setEnabled(false);
        et_input.setTextColor(ContextCompat.getColor(mActivity, R.color.black));
    }

    public void setEnable(){
        et_input.setBackgroundResource(R.drawable.bkg_editext);
        et_input.setEnabled(true);
        et_input.setTextColor(ContextCompat.getColor(mActivity, R.color.black));
    }

    public void setRightIcon(int icon, int color){
        iv_iconSelect.setImageResource(icon);
        iv_iconSelect.setColorFilter(color);
        iv_iconRight.setImageResource(icon);
        iv_iconRight.setColorFilter(color);
    }

    public void setHint(String hint){
        et_input.setHint(hint);
        tv_input.setHint(hint);
    }

    public String getValue(){
        if (type == TYPE.CURRENCY){
            String value =  et_input.getText().toString().replaceAll("\\.","");
            if (value.isEmpty()){
                return "0";
            }
            return value;
        }
        if (type == TYPE.SELECT){
            return tv_input.getText().toString();
        }
        return et_input.getText().toString().trim();
    }

    public String getErrorMessage(){
        return mTitle +" "+ getResources().getString(R.string.required);
    }

    public void setValue(String value){
        et_input.setText(value.trim());
        tv_input.setText(value.trim());
        if (onSetValueListener != null){
            onSetValueListener.onSetValue(value);
        }
    }

    public void setObject(Object object){
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public void setValue(long value){
        et_input.setText(String.valueOf(value));
        tv_input.setText(String.valueOf(value));
        if (onSetValueListener != null){
            onSetValueListener.onSetValue(String.valueOf(value));
        }
    }

    public void setFont(int font){
        Typeface typeface =  ResourcesCompat.getFont(mActivity,font);
        et_input.setTypeface(typeface);
        tv_input.setTypeface(typeface);
        tv_counter.setTypeface(typeface);
    }

    public void setFont(int fontInput, int fontTitle){
        Typeface typefaceInput =  ResourcesCompat.getFont(mActivity,fontInput);
        Typeface typefaceTitle =  ResourcesCompat.getFont(mActivity,fontTitle);
        et_input.setTypeface(typefaceInput);
        tv_input.setTypeface(typefaceInput);
        tv_counter.setTypeface(typefaceTitle);
    }

    public void setMaxLength(int maxLength){
        tv_counter.setVisibility(VISIBLE);
        this.pMaxLength = maxLength;
        String max = "0/"+MyEdiText.this.pMaxLength;
        tv_counter.setText(max);
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(maxLength);
        et_input.setFilters(filters);
        tv_counter.setVisibility(VISIBLE);
        et_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.toString().length();
                String max = length+"/"+MyEdiText.this.pMaxLength;
                tv_counter.setText(max);
            }
        });
    }

    public void setTextChangeListener (TextWatcher textChangeListener){
        et_input.addTextChangedListener(textChangeListener);
    }

    private void registerCurrency(){
        et_input.setSingleLine();
        et_input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        et_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                et_input.removeTextChangedListener(this);
                String cleanString = text.replaceAll("[.]", "");
                et_input.setTag(cleanString);
                String formatted = MyCurrency.toCurrnecy(cleanString);
                mNominal = formatted;
                et_input.setText(formatted);
                et_input.setSelection(formatted.length());
                if (formatted.equals("0")){
                    et_input.setText("");
                }
                et_input.addTextChangedListener(this);
            }
        });
    }


    private OnActionListener onActionListener;
    public void setOnActionListener(OnActionListener onActionListener){
        this.onActionListener = onActionListener;
    }

    public interface OnActionListener{
        void onSelect(MyEdiText view);
    }

    private OnSetValueListener onSetValueListener;
    public void setOnSetValueListener(OnSetValueListener onSetValueListener){
        this.onSetValueListener = onSetValueListener;
    }

    public interface OnSetValueListener{
        void onSetValue(String value);
    }
}
