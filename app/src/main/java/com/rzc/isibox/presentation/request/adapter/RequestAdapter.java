package com.rzc.isibox.presentation.request.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rzc.isibox.R;
import com.rzc.isibox.data.RequestStatus;
import com.rzc.isibox.presentation.component.MyRelativeLayout;
import com.rzc.isibox.presentation.request.model.RequestListModel;
import com.rzc.isibox.tools.MyCurrency;
import com.rzc.isibox.tools.Utility;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder>{

    Context context;

    ArrayList<RequestListModel> listProduct;

    public RequestAdapter(ArrayList<RequestListModel> list){
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
        RequestListModel data = listProduct.get(position);
        Glide.with(context).load(data.getImage()).into(holder.iv_image);
        holder.tv_name.setText(data.getName());
        holder.tv_grandTotal.setText(MyCurrency.toCurrnecy(data.getTotal()));
        holder.tv_category.setText(data.getCategory());
        holder.tv_view.setText(String.valueOf(data.getViews()));

        holder.tv_created.setText(Utility.getDateString(data.getECreatedDate(),"dd MMM yyyy"));

        if (data.getOffer() == 0){
            holder.tv_status.setText(context.getResources().getString(R.string.no_offer));
            holder.tv_status.setTextColor(Color.parseColor("#FFB743"));
        }
        else {
            String offer = context.getResources().getString(R.string.offer);
            offer = data.getOffer()+" " + offer ;
            holder.tv_status.setText(offer);
            holder.tv_status.setTextColor(Color.parseColor("#3C84FC"));
        }

        RequestStatus status = RequestStatus.GetStatusById(data.getStatus());
        if (status == RequestStatus.CANCELED){
            holder.tv_status.setText(status.getName());
            holder.tv_status.setTextColor(ContextCompat.getColor(context, R.color.red));
        }
        holder.rvl_root.setOnClickListener(v -> {
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
        TextView tv_name,tv_status,tv_created,tv_grandTotal,tv_category,tv_view;

        MyRelativeLayout rvl_root;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_image = itemView.findViewById(R.id.iv_image);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_status = itemView.findViewById(R.id.tv_status);
            tv_created = itemView.findViewById(R.id.tv_created);
            rvl_root = itemView.findViewById(R.id.rvl_root);
            tv_grandTotal = itemView.findViewById(R.id.tv_grandTotal);
            tv_category = itemView.findViewById(R.id.tv_category);
            tv_view = itemView.findViewById(R.id.tv_view);

        }
    }

    protected OnActionListener onActionListener;
    public void setOnActionListener(OnActionListener onActionListener){
        this.onActionListener = onActionListener;
    }

    public interface OnActionListener{
        void onAction(RequestListModel data);
    }
}
