package com.rzc.isibox.presentation.request;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rzc.isibox.R;
import com.rzc.isibox.presentation.component.MyRelativeLayout;
import com.rzc.isibox.tools.Utility;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder>{

    Context context;

    ArrayList<RequestModel> listProduct;

    public RequestAdapter(ArrayList<RequestModel> list){
        listProduct = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_adapter_request,parent, false);
        return new RequestAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RequestModel data = listProduct.get(position);
        Glide.with(context).load(data.getImage()).into(holder.iv_image);
        holder.tv_name.setText(data.getName());

        String sExpired = context.getString(R.string.expired);
        sExpired = sExpired +" "+ Utility.getDateString(data.getExpiredDate(),"dd MMM yyyy");
        holder.tv_expired.setText(sExpired);

        if (data.getOffer() == 0){
            holder.tv_offer.setText(context.getResources().getString(R.string.no_offer));
            holder.tv_offer.setTextColor(Color.parseColor("#FFB743"));
        }
        else {
            String offer = context.getResources().getString(R.string.offer);
            offer = data.getOffer()+" " + offer ;
            holder.tv_offer.setText(offer);
            holder.tv_offer.setTextColor(Color.parseColor("#3C84FC"));
        }

        holder.rvly_root.setOnClickListener(v -> {
            if (onActionListener != null){
                onActionListener.onAction(data);
            }
        });


    }


    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        RoundedImageView iv_image;
        TextView tv_name,tv_offer,tv_expired;

        MyRelativeLayout rvly_root;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_image = itemView.findViewById(R.id.iv_image);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_offer = itemView.findViewById(R.id.tv_offer);
            tv_expired = itemView.findViewById(R.id.tv_expired);
            rvly_root = itemView.findViewById(R.id.rvly_root);

        }
    }

    protected OnActionListener onActionListener;
    public void setOnActionListener(OnActionListener onActionListener){
        this.onActionListener = onActionListener;
    }

    public interface OnActionListener{
        void onAction(RequestModel data);
    }
}
