package com.rzc.isibox.presentation.request.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rzc.isibox.R;
import com.rzc.isibox.presentation.component.MyRelativeLayout;
import com.rzc.isibox.data.QuotesStatus;
import com.rzc.isibox.presentation.request.model.QuotesModel;
import com.rzc.isibox.tools.MyCurrency;

import java.util.ArrayList;

public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.ViewHolder>{

    Context context;

    ArrayList<QuotesModel> listProduct;

    public QuotesAdapter(ArrayList<QuotesModel> list){
        listProduct = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_adapter_quotes,parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuotesModel data = listProduct.get(position);
        Glide.with(context).load(data.getImage()).into(holder.iv_image);
        holder.tv_name.setText(data.getName());
        holder.tv_user.setText(data.getUser());
        holder.tv_location.setText(data.getAddress());
        holder.tv_qty.setText(MyCurrency.toCurrnecy(data.getQty()));
        holder.tv_price.setText(MyCurrency.toCurrnecy(data.getPriceTarget()));

        holder.tv_status.setText(data.getStatusName());
        QuotesStatus status = QuotesStatus.getById(data.getStatus());
        if (status == QuotesStatus.NEW){
            holder.tv_status.setTextColor(ContextCompat.getColor(context, R.color.yellow1));
            holder.rv_status.setLineColor(ContextCompat.getColor(context, R.color.yellow1));
        }
        else if (status == QuotesStatus.REJECTED){
            holder.tv_status.setTextColor(ContextCompat.getColor(context, R.color.red));
            holder.rv_status.setLineColor(ContextCompat.getColor(context, R.color.red));
        }
        else {
            holder.tv_status.setTextColor(ContextCompat.getColor(context, R.color.grey1));
            holder.rv_status.setLineColor(ContextCompat.getColor(context, R.color.grey1));
        }

    }


    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        RoundedImageView iv_image;
        ImageView iv_flag;
        TextView tv_name,tv_user,tv_location,tv_qty,tv_price,tv_status;
        MyRelativeLayout rv_status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_image = itemView.findViewById(R.id.iv_image);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_user = itemView.findViewById(R.id.tv_user);
            tv_location = itemView.findViewById(R.id.tv_location);
            iv_flag = itemView.findViewById(R.id.iv_flag);
            tv_qty = itemView.findViewById(R.id.tv_qty);
            tv_price = itemView.findViewById(R.id.tv_price);
            rv_status = itemView.findViewById(R.id.rv_status);
            tv_status = itemView.findViewById(R.id.tv_status);

        }
    }
}
