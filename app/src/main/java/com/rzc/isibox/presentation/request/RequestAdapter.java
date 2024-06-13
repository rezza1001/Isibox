package com.rzc.isibox.presentation.request;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rzc.isibox.R;
import com.rzc.isibox.presentation.component.MyButton;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_adapter,parent, false);
        return new RequestAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RequestModel data = listProduct.get(position);


    }


    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView iv_profile;
        ImageView iv_product,iv_flag;
        TextView tv_name,tv_productName,tv_category,tv_description,tv_metric,tv_qty,tv_price,tv_created,tv_address;
        MyButton btn_detail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_profile = itemView.findViewById(R.id.iv_profile);
            tv_name = itemView.findViewById(R.id.tv_name);
            iv_product = itemView.findViewById(R.id.iv_product);
            tv_productName = itemView.findViewById(R.id.tv_productName);
            tv_category = itemView.findViewById(R.id.tv_category);
            tv_description = itemView.findViewById(R.id.tv_description);
            tv_metric = itemView.findViewById(R.id.tv_metric);
            tv_qty = itemView.findViewById(R.id.tv_qty);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_created = itemView.findViewById(R.id.tv_created);
            iv_flag = itemView.findViewById(R.id.iv_flag);
            tv_address = itemView.findViewById(R.id.tv_address);
            btn_detail = itemView.findViewById(R.id.btn_detail);
            btn_detail.create(MyButton.TYPE.PRIMARY, context.getResources().getString(R.string.view_request));
            btn_detail.setCardCfg(1,4);
            btn_detail.setTextSize(12);
        }
    }
}
