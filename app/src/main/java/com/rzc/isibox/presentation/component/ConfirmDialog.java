package com.rzc.isibox.presentation.component;



import android.content.Context;
import android.text.SpannableString;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyDialog;
import com.rzc.isibox.tools.Utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfirmDialog extends MyDialog {
    public enum TYPE {
        ORANGE, RED, GREEN
    }
    CardView card_body;
    ImageView iv_icon;
    TextView tv_title,tv_description;
    View vw_line;
    MyEdiText et_note;
    MyButton btn_cancel,btn_process;

    boolean requiredNote = false;
    public ConfirmDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int setLayout() {
        return R.layout.component_dialog_confirm;
    }

    @Override
    protected void initLayout(View view) {
        iv_icon = view.findViewById(R.id.iv_icon);
        tv_title = view.findViewById(R.id.tv_title);
        card_body = view.findViewById(R.id.card_body);
        vw_line = view.findViewById(R.id.vw_line);
        tv_description = view.findViewById(R.id.tv_description);
        et_note = view.findViewById(R.id.et_note);

        et_note.create(MyEdiText.TYPE.MULTILINE, "Keterangan");
        et_note.setMaxLength(100);
        et_note.setFont(R.font.quicksand_regular);
        et_note.setTextInputSize(14);

        btn_cancel = findViewById(R.id.btn_cancel);
        btn_process = findViewById(R.id.btn_process);
        btn_cancel.create(MyButton.TYPE.GRAY, "Batal");
        btn_cancel.setCardCfg(1, Utility.toUnitDP(mActivity, 4));

        card_body.setVisibility(View.INVISIBLE);
    }

    @Override
    public void show() {
        super.show();
        et_note.setVisibility(View.GONE);
        card_body.setVisibility(View.VISIBLE);
        card_body.startAnimation(AnimationUtils.loadAnimation(mActivity, R.anim.zoom_in));

        btn_cancel.setOnMyClickListener(view -> dismiss());
        btn_process.setOnMyClickListener(view -> {
            if (requiredNote){
                if (et_note.getValue().isEmpty()){
                    Utility.showToastError(mActivity,et_note.getErrorMessage());
                    return;
                }
            }

            if (onActionListener != null){
                String note = et_note.getValue();
                String errorChar = containsSpecialCharacters(note);
                if(!errorChar.isEmpty()){
                    Utility.showToastError(mActivity,"Note mengandung special character berikut "+errorChar +" silahkan cek kembali !");
                    return;

                }
                onActionListener.onProcess(note);
            }
            dismiss();
        });
    }
    public void show(TYPE type, String title, String description, int icon){
        show();
        tv_title.setText(title);
        tv_description.setText(description);
        if (icon != 0){
            iv_icon.setImageResource(icon);
        }
        configUI(type);

    }

    public void show2(TYPE type, String title, String description, int icon){
        show();
        tv_title.setText(title);
        tv_description.setText(description);
        btn_process.setTitle("Lanjutkan");
        btn_cancel.setTitle("Ganti Nomor");
        btn_cancel.setOnMyClickListener(v->{
            if(onActionListener != null){
                onActionListener.onCancel();
            }
        });
        if (icon != 0){
            iv_icon.setImageResource(icon);
        }
        configUI(type);

    }

    public void setTextButton(String yes, String no){
        btn_cancel.setTitle(no);
        btn_process.setTitle(yes);
    }

    public void show(TYPE type, String title, SpannableString description, int icon){
        show();
        tv_title.setText(title);
        tv_description.setText(description);
        if (icon != 0){
            iv_icon.setImageResource(icon);
        }
        configUI(type);
    }

    public void setActionButton(String positive, String negative){
        btn_cancel.setTitle(negative);
        btn_process.setTitle(positive);
    }


    private void configUI(TYPE type){
        switch (type){
            case RED:
                iv_icon.setColorFilter(ContextCompat.getColor(mActivity, R.color.red));
                vw_line.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.red));
                configButton(MyButton.TYPE.RED);
                break;
            case GREEN:
                iv_icon.setColorFilter(ContextCompat.getColor(mActivity, R.color.green2));
                vw_line.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.green2));
                configButton(MyButton.TYPE.GREEN);
                break;
            default:
                iv_icon.setColorFilter(ContextCompat.getColor(mActivity, R.color.orange));
                vw_line.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.orange));
                configButton(MyButton.TYPE.ORANGE);
                break;
        }
    }

    public void showInputNote(){
        et_note.setVisibility(View.VISIBLE);
    }
    public void showInputNote(String title){
        et_note.setVisibility(View.VISIBLE);
//        et_note.setTitle(title);
        et_note.setHint("Masukan "+title);
    }

    public void setNote(String note){
        et_note.setValue(note);
    }

    public void setMaxLengthNote(int lengthNote){
        et_note.setMaxLength(lengthNote);
    }

    public void setRequiredNote(){
        requiredNote = true;
    }

    public void setTextAction(String negative, String positive){
        btn_process.setTitle(positive);
        btn_cancel.setTitle(negative);
    }

    public void setHint(String Hint){
        if(Hint.toLowerCase().contains("nomor")){
            et_note.setType(MyEdiText.TYPE.NUMBER);
            et_note.setMaxLength(15);
        }
        et_note.setHint(Hint);
    }
    private void configButton(MyButton.TYPE type){
        btn_process.create(type,"Proses");
        btn_process.setCardCfg(1, Utility.toUnitDP(mActivity, 4));
    }
    private String containsSpecialCharacters(String input) {
        Pattern pattern = Pattern.compile("[`~{}';\"|\\\\]");
        Matcher matcher = pattern.matcher(input);
        StringBuilder disallowedCharacters = new StringBuilder();
        while (matcher.find()) {
            disallowedCharacters.append(matcher.group());
        }
        return disallowedCharacters.toString();
    }


    private OnActionListener onActionListener;
    public void setOnActionListener(OnActionListener onActionListener){
        this.onActionListener = onActionListener;
    }
    public interface OnActionListener{
        void onProcess(String note);
        void onCancel();
    }
}
