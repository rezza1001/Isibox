package com.rzc.isibox.presentation.component.option;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyDialog;

import java.util.ArrayList;

public class OptionDialog extends MyDialog {

    private RecyclerView rcv_option;
    private TextView tv_title;
    private CardView card_body;

    private ArrayList<OptionData> optionData;
    private OptionAdapter adapter;

    private OnSelectedListener listener;

    public OptionDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int setLayout() {
        return R.layout.component_dialog_option;
    }

    @Override
    protected void initLayout(View view) {
        view.findViewById(R.id.iv_close).setOnClickListener(view1 -> dismiss());
        view.findViewById(R.id.rvly_root).setOnClickListener(view1 -> dismiss());

        rcv_option = view.findViewById(R.id.rcv_option);
        rcv_option.setLayoutManager(new LinearLayoutManager(mActivity));

        tv_title = view.findViewById(R.id.tv_title);
        card_body = view.findViewById(R.id.card_body);
        card_body.setVisibility(View.INVISIBLE);
    }


    public void show(String title) {
        super.show();
        tv_title.setText(title);

        optionData = new ArrayList<>();
        adapter = new OptionAdapter();
        rcv_option.setAdapter(adapter);

        card_body.setVisibility(View.VISIBLE);
        card_body.startAnimation(AnimationUtils.loadAnimation(mActivity, R.anim.zoom_in));
    }

    public void addOption(OptionData data){
        optionData.add(data);
        adapter.notifyItemInserted(optionData.size());
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setOptionData(ArrayList<OptionData> data){
        optionData.clear();
        optionData.addAll(data);
        adapter.notifyDataSetChanged();
    }

    class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.AdapterView> {

        @NonNull
        @Override
        public AdapterView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_adapter_option,parent, false);
            return new AdapterView(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull AdapterView holder, int position) {

            OptionData data = optionData.get(position);
            holder.tv_value.setText(data.getValue());
            if (data.getIcon() != 0){
                holder.iv_icon.setVisibility(View.VISIBLE);
                holder.iv_icon.setImageResource(data.getIcon());
            }
            else {
                holder.iv_icon.setVisibility(View.GONE);
            }

            if (optionData.size() == (position + 1)){
                holder.view_line.setVisibility(View.GONE);
            }
            else {
                holder.view_line.setVisibility(View.VISIBLE);
            }

            if (data.getInfo().isEmpty()){
                holder.tv_info.setVisibility(View.GONE);
            }
            else {
                holder.tv_info.setVisibility(View.VISIBLE);
                holder.tv_info.setText(data.getInfo());
            }

            holder.rvl_root.setOnClickListener(view -> {
                if (listener != null){
                    listener.onSelected(data);
                    dismiss();
                }
            });
        }

        @Override
        public int getItemCount() {
            return optionData.size();
        }

        class AdapterView extends RecyclerView.ViewHolder{

            TextView tv_value,tv_info;
            ImageView iv_icon;
            RelativeLayout rvl_root;
            View view_line;

            public AdapterView(@NonNull View itemView) {
                super(itemView);

                tv_value = itemView.findViewById(R.id.tv_value);
                rvl_root = itemView.findViewById(R.id.rvl_root);
                view_line = itemView.findViewById(R.id.view_line);
                iv_icon = itemView.findViewById(R.id.iv_icon);
                tv_info = itemView.findViewById(R.id.tv_info);
            }
        }
    }

    public void setOnSelectedListener(OnSelectedListener onSelectedListener){
        listener = onSelectedListener;
    }
    public interface OnSelectedListener{
        void onSelected(OptionData data);
    }
}
