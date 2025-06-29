package com.rzc.isibox.presentation.request.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.rzc.isibox.R;
import com.rzc.isibox.presentation.account.model.CustomerAddressModel;
import com.rzc.isibox.presentation.component.MyLinearLayout;
import com.rzc.isibox.presentation.component.MyRelativeLayout;
import com.rzc.isibox.presentation.quots.model.BidsUsersModel;
import com.rzc.isibox.tools.Utility;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class BidAdapter extends RecyclerView.Adapter<BidAdapter.ViewHolder> {

    ArrayList<BidsUsersModel> list ;
    Context context;
    public BidAdapter(ArrayList<BidsUsersModel> list){
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_adapter_quot,parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BidsUsersModel data = list.get(position);
        holder.tv_name.setText(data.getName());
        Date date = Utility.convert2Date(data.getCreatedAt(),"yyyy-MM-dd HH:mm");
        holder.tv_date.setText(Utility.getDateString(date,"dd MMM yyyy HH:mm"));
        holder.tv_note.setText(data.getComments());


        holder.rv_action.setOnClickListener(view -> {
            if (onActionListener != null){
                onActionListener.onSelected(data);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name, tv_date, tv_note;
        CircleImageView iv_image;
        RelativeLayout rv_action;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_image = itemView.findViewById(R.id.iv_image);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_note = itemView.findViewById(R.id.tv_note);
            rv_action = itemView.findViewById(R.id.rv_action);

        }
    }

    private OnActionListener onActionListener;
    public void setOnActionListener(OnActionListener onActionListener){
        this.onActionListener = onActionListener;
    }

    public interface OnActionListener{
        void onSelected(BidsUsersModel data);
    }
}
