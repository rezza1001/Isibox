package com.rzc.isibox.presentation.orders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rzc.isibox.R;
import com.rzc.isibox.presentation.component.option.OptionData;

import java.util.ArrayList;

public class OrderCancelAdapter extends RecyclerView.Adapter<OrderCancelAdapter.AdapterView> {
    public  OnElementClick listener;
    ArrayList<OptionData> listOption;

    public OrderCancelAdapter(ArrayList<OptionData> listOption) {
        this.listOption = listOption;
    }

    public void setListener(OnElementClick listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdapterView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_cancel_order_adapter,parent,false);
        return new AdapterView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterView holder, int position) {
        OptionData data = listOption.get(position);
        holder.tv_value.setText(data.getValue());
        if(data.isSelected()){
            holder.iv_value.setImageResource(R.drawable.check_box_checked);
        }
        holder.ln_body.setOnClickListener(v ->{
            if (listener != null){
                data.setSelected(!data.isSelected());
                listener.OnElementClickListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOption.size();
    }

    static class AdapterView extends RecyclerView.ViewHolder{
        ImageView iv_value;
        TextView tv_value;
        LinearLayout ln_body;
        public AdapterView(@NonNull View itemView) {
            super(itemView);
            tv_value = itemView.findViewById(R.id.tv_value);
            iv_value = itemView.findViewById(R.id.iv_value);
            ln_body = itemView.findViewById(R.id.ln_body);

        }
    }

    public interface OnElementClick{
        void OnElementClickListener(Integer posisi);
    }
}
