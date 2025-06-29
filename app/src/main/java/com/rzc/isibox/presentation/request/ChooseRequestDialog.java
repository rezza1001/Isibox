package com.rzc.isibox.presentation.request;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;

import com.rzc.isibox.R;
import com.rzc.isibox.data.BidStatus;
import com.rzc.isibox.master.MyDialog;
import com.rzc.isibox.presentation.component.KeyValueView;
import com.rzc.isibox.presentation.component.MyButton;
import com.rzc.isibox.presentation.component.MyRelativeLayout;
import com.rzc.isibox.presentation.request.model.UserBidding;
import com.rzc.isibox.tools.MyCurrency;
import com.rzc.isibox.tools.OperatorPrefix;
import com.rzc.isibox.tools.Utility;

import java.util.Date;

public class ChooseRequestDialog extends MyDialog {

    MyRelativeLayout rv_body;
    MyButton btn_send,btn_cancel;
    KeyValueView kv_status,kv_date,kv_price,kv_note, kv_name, kv_email,kv_phone,kv_join;

    UserBidding detailModel;

    public ChooseRequestDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int setLayout() {
        return R.layout.request_dialog_choose;
    }

    @Override
    protected void initLayout(View view) {
        rv_body = view.findViewById(R.id.rv_body);
        rv_body.setVisibility(View.INVISIBLE);


        btn_send = view.findViewById(R.id.btn_send);
        btn_send.create(MyButton.TYPE.GREEN,"Hubungi via WA");
        btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_cancel.create(MyButton.TYPE.PRIMARY,"Terima Penawaran");

        kv_status = view.findViewById(R.id.kv_status);
        kv_status.create("Status","-");
        kv_status.setValueGravity(Gravity.END);

        kv_date = view.findViewById(R.id.kv_date);
        kv_date.create("Diajukan pada","-");
        kv_date.setValueGravity(Gravity.END);

        kv_price = view.findViewById(R.id.kv_price);
        kv_price.create("Harga Ditawarkan", "-");
        kv_price.setValueGravity(Gravity.END);

        kv_note = view.findViewById(R.id.kv_note);
        kv_note.create("Catatan", "-");
        kv_note.setValueGravity(Gravity.END);

        kv_name = view.findViewById(R.id.kv_name);
        kv_name.create("Nama", "-");
        kv_name.setValueGravity(Gravity.END);

        kv_email = view.findViewById(R.id.kv_email);
        kv_email.create("Email", "-");
        kv_email.setValueGravity(Gravity.END);

        kv_phone = view.findViewById(R.id.kv_phone);
        kv_phone.create("Nomor WA", "-");
        kv_phone.setValueGravity(Gravity.END);

        kv_join = view.findViewById(R.id.kv_join);
        kv_join.create("Bergabung", "-");
        kv_join.setValueGravity(Gravity.END);

        view.findViewById(R.id.iv_close).setOnClickListener(v -> dismiss());
    }

    public void show(UserBidding mainModel){
        show();

        detailModel = mainModel;

        BidStatus status = BidStatus.GetStatusById(Integer.parseInt(mainModel.getBidStatus()));
        kv_status.setValue(status.getName());

        Date created = Utility.convert2Date(mainModel.getBidDate(),"yyyy-MM-dd HH:mm");
        kv_date.setValue(Utility.getDateString(created, "dd MMMM yyyy, HH:mm"));

        kv_price.setValue(MyCurrency.toCurrnecy("Rp", mainModel.getBidAmount()));

        kv_note.setValue(mainModel.getBidComment());

        kv_name.setValue(mainModel.getName());
        kv_email.setValue(mainModel.getEmail());
        kv_phone.setValue(mainModel.getPhone());
        Date join = Utility.convert2Date(mainModel.getJoinDate(),"yyyy-MM-dd HH:mm");
        kv_join.setValue(Utility.getDateString(join, "dd MMMM yyyy"));

        btn_cancel.setOnMyClickListener(view -> {
            if (onActionListener != null){
                onActionListener.OnCancel();
                dismiss();
            }
        });

        btn_send.setOnMyClickListener(view -> toWhatsapp());
    }

    @Override
    public void show() {
        super.show();

        rv_body.setVisibility(View.VISIBLE);
        rv_body.startAnimation(AnimationUtils.loadAnimation(mActivity, R.anim.push_up_in));
    }



    public void toWhatsapp(){
        OperatorPrefix prefix = new OperatorPrefix();
        prefix.getInfo(detailModel.getPhone());
        String message = "Halo, saya tertarik dengan penawaran Anda!";
        String url = "https://api.whatsapp.com/send?phone=" + prefix.getPhoneIndonesia() + "&text=" + Uri.encode(message);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        try {
            mActivity.startActivity(intent);
        } catch (Exception e) {

            Utility.showToastError(mActivity,"WhatsApp tidak ditemukan");
        }
    }

    private OnActionListener onActionListener;
    public void setOnActionListener(OnActionListener onActionListener){
        this.onActionListener = onActionListener;
    }


    public interface OnActionListener{
        void OnCancel();
    }
}
