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
import com.rzc.isibox.tools.MyCurrency;
import com.rzc.isibox.tools.Utility;

import java.util.ArrayList;

public class CompleteAdapter extends RecyclerView.Adapter<CompleteAdapter.AdapterView> {
    ArrayList<OrderModel> listOrder;

    Context context;
    public CompleteAdapter(ArrayList<OrderModel> listOrder) {
        this.listOrder = listOrder;
    }
    @NonNull
    @Override
    public AdapterView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_complete,parent,false);
        return new AdapterView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterView holder, int position) {
        OrderModel data = listOrder.get(position);
        Glide.with(context).load(data.getProductImage()).into(holder.iv_image);
        holder.tv_title.setText(data.getProductName());
        holder.tv_store.setText(data.getDistributorName());
        holder.tv_date.setText(Utility.getDateString(data.getCreatedDate(),"MMM, dd yyyy"));
        String rp = "Rp "+MyCurrency.toCurrnecy(data.getGrandTotal());
        holder.tv_price.setText(rp);

    }

    @Override
    public int getItemCount() {
        return listOrder.size();
    }

    static class AdapterView extends RecyclerView.ViewHolder{
        RoundedImageView iv_image;
        TextView tv_title,tv_store,tv_date,tv_price;
        ImageView iv_store,iv_verified;

        public AdapterView(@NonNull View itemView) {
            super(itemView);
            iv_image = itemView.findViewById(R.id.iv_image);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_store = itemView.findViewById(R.id.tv_store);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_price = itemView.findViewById(R.id.tv_price);
            iv_store = itemView.findViewById(R.id.iv_store);
            iv_verified = itemView.findViewById(R.id.iv_verified);
        }
    }
}
