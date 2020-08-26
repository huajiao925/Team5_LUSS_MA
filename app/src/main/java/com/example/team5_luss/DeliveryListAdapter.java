package com.example.team5_luss;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Model.Request;
import Model.ViewModel.CustomRequest;
import Model.ViewModel.CustomRetrieval;

public class DeliveryListAdapter extends RecyclerView.Adapter<DeliveryListAdapter.DeliveryListViewHolder>{
    private Activity activity;
    public static ArrayList<CustomRequest> itemList = new ArrayList<CustomRequest>();

    public DeliveryListAdapter(Activity activity, ArrayList<CustomRequest> itemList) {
        this.activity = activity;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public DeliveryListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivery_list_item, parent,false);
        return new DeliveryListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryListViewHolder holder, int position) {
        final CustomRequest item = itemList.get(position);
        holder.retrievalID.setText(Integer.toString(item.getRetrievalID()));
        holder.departmentName.setText(item.getDepartmentName());
        holder.deliveryDate.setText(item.getCollectionTime().substring(0,10));
        holder.collectionPoint.setText(item.getCollectionPoint());
        holder.layoutClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, DeliveryDetailActivity.class);
                intent.putExtra("retrievalID", item.getRetrievalID());
                intent.putExtra("requestIDs", item.getRequestID());
                activity.startActivity(intent);
                activity.finish();
            }
        });
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public class DeliveryListViewHolder extends RecyclerView.ViewHolder {

        public TextView retrievalID;
        public TextView deliveryDate;
        public TextView departmentName;
        public TextView collectionPoint;
        public LinearLayout layoutClick;

        public DeliveryListViewHolder(@NonNull View itemView) {
            super(itemView);

            retrievalID = itemView.findViewById(R.id.rtvId);
            deliveryDate = itemView.findViewById(R.id.dlvdate);
            departmentName = itemView.findViewById(R.id.deptName);
            collectionPoint= itemView.findViewById(R.id.cp);
            layoutClick = itemView.findViewById(R.id.clickView);
        }
    }
}
