package com.rzc.isibox.presentation.quots.dialog;

import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyDialog;
import com.rzc.isibox.presentation.component.MyButton;
import com.rzc.isibox.presentation.component.MyEdiText;
import com.rzc.isibox.presentation.component.MyRelativeLayout;
import com.rzc.isibox.presentation.main.MainModel;
import com.rzc.isibox.presentation.request.model.MyRequestDetailModel;

public class BidsDialog extends MyDialog {

    MyRelativeLayout rv_body;
    MyEdiText et_price,et_note;
    MyButton btn_send;
    public BidsDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int setLayout() {
        return R.layout.quots_dialog_quotation;
    }

    @Override
    protected void initLayout(View view) {
        rv_body = view.findViewById(R.id.rv_body);
        rv_body.setVisibility(View.INVISIBLE);

        et_price = view.findViewById(R.id.et_price);
        et_price.create(MyEdiText.TYPE.CURRENCY,"Harga Penawaran");

        et_note = view.findViewById(R.id.et_note);
        et_note.create(MyEdiText.TYPE.MULTILINE,"Catatan");
        et_note.setMinHeight(5);
        et_note.setMaxLine(10);

        btn_send = view.findViewById(R.id.btn_send);
        btn_send.create(MyButton.TYPE.PRIMARY,"Kirim");

        view.findViewById(R.id.iv_close).setOnClickListener(v -> dismiss());
    }

    public void show(MyRequestDetailModel mainModel){
        show();

        et_price.setValue(mainModel.getTargetPrice());

        btn_send.setOnMyClickListener(view -> {
            if (onActionListener  != null){
                dismiss();
                action();

            }
        });
    }

    @Override
    public void show() {
        super.show();

        rv_body.setVisibility(View.VISIBLE);
        rv_body.startAnimation(AnimationUtils.loadAnimation(mActivity, R.anim.push_up_in));
    }

    private void action(){
        String note = et_note.getValue();
        long price = Long.parseLong(et_price.getValue());
        onActionListener.OnAction(price, note);
    }

    private OnActionListener onActionListener;
    public void setOnActionListener(OnActionListener onActionListener){
        this.onActionListener = onActionListener;
    }


    public interface OnActionListener{
        void OnAction(long offering, String note);
    }
}
