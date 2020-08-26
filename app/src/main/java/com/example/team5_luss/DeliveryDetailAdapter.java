package com.example.team5_luss;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import Model.ViewModel.CustomRetrieval;

public class DeliveryDetailAdapter extends RecyclerView.Adapter<DeliveryDetailAdapter.DeliveryDetailViewHolder> {

    private Activity activity;
    public static ArrayList<CustomRetrieval> itemList = new ArrayList<CustomRetrieval>();

    public DeliveryDetailAdapter(Activity activity, ArrayList<CustomRetrieval> itemList) {
        this.activity = activity;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public DeliveryDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivery_detail_item, parent,false);
        return new DeliveryDetailViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryDetailViewHolder holder, int position) {
        final CustomRetrieval item = itemList.get(position);
        holder.itemCode.setText(item.getItemCode());
        holder.itemName.setText(item.getItemName());
        holder.UOM.setText(item.getUOM());
        holder.requestedQty.setText(Integer.toString(item.getRequestedQty()));
        holder.fullfilledQty.setText(Integer.toString(item.getTotalQty()));
        holder.accptQty.setText(Integer.toString(item.getAcceptedQty()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class DeliveryDetailViewHolder extends RecyclerView.ViewHolder {

        public TextView itemCode;
        public TextView itemName;
        public TextView UOM;
        public TextView requestedQty;
        public TextView fullfilledQty;
        public TextView accptQty;

        public DeliveryDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            itemCode = itemView.findViewById(R.id.itemCode);
            itemName = itemView.findViewById(R.id.description);
            UOM = itemView.findViewById(R.id.uom);
            requestedQty = itemView.findViewById(R.id.reqQty);
            fullfilledQty = itemView.findViewById(R.id.fulfilQty);
            accptQty = itemView.findViewById(R.id.accptQty);
        }
    }
}
