package com.example.finalproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class Adapter_Delivery  extends RecyclerView.Adapter<Adapter_Delivery.MyViewHolder> {


    private List<Delivery> deliveries;
    private LayoutInflater mInflater;
    private MyItemClickListener mClickListener;



    Adapter_Delivery(Context context, List<Delivery> _deliveries) {
        this.mInflater = LayoutInflater.from(context);
        this.deliveries = _deliveries;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_deliveries, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Delivery m = deliveries.get(position);
        holder.recycle_LBL_name.setText(m.getReceiverName());
        holder.recycle_LBL_address.setText(m.getAddress());
        holder.recycle_LBL_phone.setText(m.getPhoneNumber());
        holder.recycle_LBL_weight.setText(Float.toString(m.getWeight()));
    }

    @Override
    public int getItemCount() {
        return deliveries.size();
    }

    Delivery getItem(int id) {
        return deliveries.get(id);
    }

    public void setClickListener(MyItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView recycle_LBL_name;
        TextView recycle_LBL_address;
        TextView recycle_LBL_phone;
        TextView recycle_LBL_weight;


        MyViewHolder(View itemView) {
            super(itemView);
            recycle_LBL_name = itemView.findViewById(R.id.recycle_LBL_name);
            recycle_LBL_address = itemView.findViewById(R.id.recycle_LBL_address);
            recycle_LBL_phone = itemView.findViewById(R.id.recycle_LBL_phone);
            recycle_LBL_weight = itemView.findViewById(R.id.recycle_LBL_weight);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener != null) {
                        mClickListener.onItemClick(view, getAdapterPosition());
                    }
                }
            });
        }
    }
}
