package com.example.team5_luss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Model.Request;
import Model.RequestDetails;

public class RequestAdapter
        extends RecyclerView.Adapter<RequestAdapter.MyViewHolder> {

    private Activity activity;
    private ArrayList<Request> requestList;

    public RequestAdapter(Activity activity, ArrayList<Request> requestList) {
        this.activity = activity;
        this.requestList = requestList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.request_list_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder viewHolder, final int position) {
        viewHolder.rqtTextView.setText(requestList.get(position).RequestByUser.FirstName+" "+requestList.get(position).RequestByUser.LastName);
        viewHolder.rqtDateTextView.setText(CodeSetting.convertDateString(requestList.get(position).getRequestDate().toString()));

        viewHolder.layoutClick.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, RequestDetailActivity.class);
                intent.putExtra("requestID", requestList.get(position).RequestID);
                intent.putExtra("requestDate", CodeSetting.convertDateString(requestList.get(position).getRequestDate().toString()));
                intent.putExtra("requestBy", requestList.get(position).RequestByUser.FirstName+" "+requestList.get(position).RequestByUser.LastName);
               // ArrayList<RequestDetails> requestDetailsList=(ArrayList<RequestDetails>)CodeSetting.convertArrayToList(requestList.get(position).RequestDetails);

              //  intent.putExtra("requestDetailList", (ArrayList<RequestDetails>)requestDetailsList);
                activity.startActivity(intent);
                activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView rqtTextView;
        public TextView rqtDateTextView;
        public LinearLayout layoutClick;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rqtTextView = itemView.findViewById(R.id.rqtBy);
            rqtDateTextView= itemView.findViewById(R.id.rqtDate);
            layoutClick = itemView.findViewById(R.id.rowClick);
        }
    }
}


