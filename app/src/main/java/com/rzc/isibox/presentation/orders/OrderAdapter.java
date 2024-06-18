package com.rzc.isibox.presentation.orders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rzc.isibox.R;
import com.rzc.isibox.presentation.component.MyLinearLayout;
import com.rzc.isibox.presentation.component.MyRelativeLayout;
import com.rzc.isibox.tools.MyCurrency;
import com.rzc.isibox.tools.Utility;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{

    Context context;

    ArrayList<OrderModel> listProduct;

    public OrderAdapter(ArrayList<OrderModel> list){
        listProduct = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_adapter_myorder,parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderModel data = listProduct.get(position);
        Glide.with(context).load(data.getProductImage()).into(holder.iv_image);
        holder.tv_name.setText(data.getProductName());
        holder.tv_from.setText(data.getDistributorName());
        holder.tv_status.setText(data.getStatusPayName());
        holder.tv_metric.setText(data.getMetric());
        holder.tv_qty.setText(MyCurrency.toCurrnecy(data.getQty()));
        holder.tv_grandTotal.setText(MyCurrency.toCurrnecy(data.getGrandTotal()));
        holder.tv_created.setText(Utility.getDateString(data.getCreatedDate(),"MMM, dd yyyy"));
        holder.tv_payStatus.setText(data.getStatusActionName());

        if (data.getStatusAction() == 2){
            holder.ln_payStatus.setVisibility(View.GONE);
        }
        else {
            holder.ln_payStatus.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        RoundedImageView iv_image;
        ImageView iv_verified;
        TextView tv_name,tv_from,tv_qty,tv_status,tv_metric,tv_grandTotal,tv_created,tv_payStatus;
        MyLinearLayout rv_status;
        MyRelativeLayout ln_payStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_image = itemView.findViewById(R.id.iv_image);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_qty = itemView.findViewById(R.id.tv_qty);
            tv_from = itemView.findViewById(R.id.tv_from);
            rv_status = itemView.findViewById(R.id.rv_status);
            tv_status = itemView.findViewById(R.id.tv_status);
            iv_verified = itemView.findViewById(R.id.iv_verified);
            tv_metric = itemView.findViewById(R.id.tv_metric);
            tv_grandTotal = itemView.findViewById(R.id.tv_grandTotal);
            tv_created = itemView.findViewById(R.id.tv_created);
            tv_payStatus = itemView.findViewById(R.id.tv_payStatus);
            ln_payStatus = itemView.findViewById(R.id.ln_payStatus);

        }
    }
}
