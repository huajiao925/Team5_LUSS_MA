package com.example.team5_luss;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Model.RequestDetails;

public class RequestItemAdapter
        extends RecyclerView.Adapter<RequestItemAdapter.MyViewHolder> {

    private Activity activity;
    private ArrayList<RequestDetails> requestDetailList;

    public RequestItemAdapter(Activity activity, ArrayList<RequestDetails> requestDetailList) {
        this.activity = activity;
        this.requestDetailList = requestDetailList;
    }

    @NonNull
    @Override
    public RequestItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.request_detail_item, viewGroup, false);
        return new RequestItemAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int position) {
        viewHolder.rqtDetailUOM.setText(requestDetailList.get(position).Item.UOM);
        viewHolder.rqtDetailQty.setText(String.valueOf(requestDetailList.get(position).RequestQty));
        viewHolder.rqtDetailItem.setText(requestDetailList.get(position).Item.ItemName);

    }

    @Override
    public int getItemCount() {
        return requestDetailList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView rqtDetailUOM;
        public TextView rqtDetailQty;
        public TextView rqtDetailItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rqtDetailUOM = itemView.findViewById(R.id.req_detail_uom);
            rqtDetailQty = itemView.findViewById(R.id.req_detail_qty);
            rqtDetailItem = itemView.findViewById(R.id.req_detail_item);
        }
    }

}

