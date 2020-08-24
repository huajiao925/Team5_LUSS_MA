package com.example.team5_luss;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import Model.ViewModel.CustomRequest;

public class DisbursementListAdapter extends ArrayAdapter<CustomRequest> {

    private Context context;
    private LayoutInflater inflater;
    private CustomRequest[] requests;

    public DisbursementListAdapter(@NonNull Context context, int resource, CustomRequest[] requests) {
        super(context, resource, requests);

        this.context = context;
        this.requests = requests;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DisbursementListAdapter.viewHolder holder;

        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        }

        if(convertView == null){
            convertView = inflater.inflate(R.layout.disb_req_item, null);

            holder = new DisbursementListAdapter.viewHolder();
            holder.requestID = (TextView)convertView.findViewById(R.id.rqtId);
            holder.requestDate = (TextView)convertView.findViewById(R.id.rqtDate);
            holder.requestByName = (TextView)convertView.findViewById(R.id.rqtBy);
            convertView.setTag(holder);
        }else{
            holder =(DisbursementListAdapter.viewHolder) convertView.getTag();
        }

        int requestID = (requests[position].getRequestID());
        String requestDate = CodeSetting.convertDateString(requests[position].getRequestDate());
        String requestByName = (requests[position].getRequestByName());
        holder.requestID.setText(String.valueOf(requestID));
        holder.requestDate.setText(String.valueOf(requestDate));
        holder.requestByName.setText(String.valueOf(requestByName));

        return convertView;
    }

    static class viewHolder{
        TextView requestID;
        TextView requestDate;
        TextView requestByName;
    }

}