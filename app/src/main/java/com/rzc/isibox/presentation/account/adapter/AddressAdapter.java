package com.rzc.isibox.presentation.account.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.rzc.isibox.R;
import com.rzc.isibox.presentation.account.model.CustomerAddressModel;
import com.rzc.isibox.presentation.component.MyLinearLayout;
import com.rzc.isibox.presentation.component.MyRelativeLayout;

import java.util.ArrayList;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    ArrayList<CustomerAddressModel> list ;
    Context context;
    public AddressAdapter(ArrayList<CustomerAddressModel> list){
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_adapter_address,parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CustomerAddressModel data = list.get(position);
        holder.tv_address.setText(data.getAddress());
        holder.tv_label.setText(data.getLabel());
        holder.tv_phone.setText(data.getPhone());
        holder.tv_note.setText(data.getNote());

        if (data.getPrimary() == 1){
            holder.ln_main.setVisibility(View.VISIBLE);
            holder.rv_body.setLineColor(ContextCompat.getColor(context, R.color.primary));
            holder.rv_body.setLineSize(2);
        }
        else {
            holder.ln_main.setVisibility(View.INVISIBLE);
            holder.rv_body.setLineColor(ContextCompat.getColor(context, R.color.grey2));
            holder.rv_body.setLineSize(1);
        }


        holder.rv_change.setOnClickListener(view -> {
            if (onActionListener != null){
                onActionListener.onChange(data);
            }
        });
        holder.rv_more.setOnClickListener(view -> {
            if (onActionListener != null){
                onActionListener.omMore(data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_label,tv_phone, tv_address, tv_note;
        LinearLayout ln_label;
        MyRelativeLayout rv_body,rv_change,rv_more;
        MyLinearLayout ln_main;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_label = itemView.findViewById(R.id.tv_label);
            tv_phone = itemView.findViewById(R.id.tv_phone);
            tv_address = itemView.findViewById(R.id.tv_address);
            ln_label = itemView.findViewById(R.id.ln_label);
            rv_body = itemView.findViewById(R.id.rv_body);
            tv_note = itemView.findViewById(R.id.tv_note);
            rv_change = itemView.findViewById(R.id.rv_change);
            rv_more = itemView.findViewById(R.id.rv_more);
            ln_main = itemView.findViewById(R.id.ln_main);

        }
    }

    private OnActionListener onActionListener;
    public void setOnActionListener(OnActionListener onActionListener){
        this.onActionListener = onActionListener;
    }

    public interface OnActionListener{
        void onChange(CustomerAddressModel data);
        void omMore(CustomerAddressModel data);
    }
}
