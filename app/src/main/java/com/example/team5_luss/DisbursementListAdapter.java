package com.example.team5_luss;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.text.SimpleDateFormat;
import java.util.List;

import Model.Request;

public class DisbursementListAdapter extends ArrayAdapter<Request> {

    public DisbursementListAdapter(@NonNull Context context, int resource, List<Request> requests) {
        super(context, resource, requests);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.disb_req_item, parent, false);
        }

        Request request = getItem(position);

        if (request != null){
            TextView rqtId = v.findViewById(R.id.rqtId);
            TextView rqtDate = v.findViewById(R.id.rqtDate);
            TextView rqtBy = v.findViewById(R.id.rqtBy);

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String date = formatter.format(request.getRequestDate());
            rqtId.setText(request.getRequestID());
            rqtDate.setText(date);
            rqtBy.setText(request.getRequestBy());
        }

        return v;

    }
}