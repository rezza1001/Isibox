package com.rzc.isibox.presentation.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rzc.isibox.R;
import com.rzc.isibox.presentation.component.MyButton;
import com.rzc.isibox.tools.MyCurrency;
import com.rzc.isibox.tools.Utility;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>{

    Context context;

    ArrayList<MainModel> listProduct;

    public MainAdapter (ArrayList<MainModel> list){
        listProduct = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_adapter,parent, false);
        return new MainAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MainModel data = listProduct.get(position);

        Glide.with(context).load(data.getProfile()).placeholder(R.drawable.default_profile).into(holder.iv_profile);
        Glide.with(context).load(data.getProductImg()).into(holder.iv_product);
        holder.tv_name.setText(data.getName());
        holder.tv_productName.setText(data.getProductName());
        holder.tv_category.setText(data.getProductCategory());
        holder.tv_description.setText(data.getProductDescription());
        holder.tv_metric.setText(data.getMetric());
        holder.tv_qty.setText(String.valueOf(data.getQuantity()));
        holder.tv_price.setText(MyCurrency.toCurrnecy("Rp", data.getTargetPrice()));

        String sDate = "Created "+Utility.getDateString(data.getCreatedAt(),"dd MMMM yyyy");
        holder.tv_created.setText(sDate);
        holder.tv_address.setText(data.getAddress());

        holder.btn_detail.setOnMyClickListener(view -> {
            if (onSelectedListener != null){
                onSelectedListener.onSelected(data);
            }
        });

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


    private OnSelectedListener onSelectedListener;
    public void setOnSelectedListener(OnSelectedListener onSelectedListener){
        this.onSelectedListener = onSelectedListener;
    }

    public interface OnSelectedListener{
        void onSelected(MainModel data);
    }
}
