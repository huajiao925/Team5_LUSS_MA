package com.example.team5_luss;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import Model.ViewModel.CustomRequestDetail;

public class DisbursementByRequestItemAdapter extends ArrayAdapter<CustomRequestDetail> {
    private Context context;
    private LayoutInflater inflater;
    private CustomRequestDetail[] requestDetails;

    public DisbursementByRequestItemAdapter(@NonNull Context context, int resource, CustomRequestDetail[] requestDetails) {
        super(context, resource, requestDetails);
        this.context = context;
        this.requestDetails = requestDetails;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        viewHolder holder;

        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        }

        if(convertView == null){
            convertView = inflater.inflate(R.layout.disb_by_req_item, null);

            holder = new DisbursementByRequestItemAdapter.viewHolder();
            holder.itemCode = (TextView)convertView.findViewById(R.id.itemCode);
            holder.description = (TextView)convertView.findViewById(R.id.description);
            holder.UOM = (TextView)convertView.findViewById(R.id.uom);
            holder.inStockQty = (TextView)convertView.findViewById(R.id.instockqyt);
            holder.requestedQty = (TextView)convertView.findViewById(R.id.reqQty);
            //holder.fulfillQty= (EditText) convertView.findViewById(R.id.fulfillQty);
            convertView.setTag(holder);
        }else{
            holder =(DisbursementByRequestItemAdapter.viewHolder) convertView.getTag();

        }

        String itemCode = (requestDetails[position].getItemCode());
        String description = (requestDetails[position].getItemName());
        String UOM = (requestDetails[position].getUom());
        int inStockQty = (requestDetails[position].getInStockQty());
        int requestedQty = (requestDetails[position].getRequestQty());

        holder.itemCode.setText(String.valueOf(itemCode));
        holder.description.setText(String.valueOf(description));
        holder.UOM.setText(String.valueOf(UOM));
        holder.inStockQty.setText(String.valueOf(inStockQty));
        holder.requestedQty.setText(String.valueOf(requestedQty));
//        if(inStockQty >= requestedQty) {
//            holder.fulfillQty.setText(String.valueOf(requestedQty));
//        } else{
//            holder.fulfillQty.setText(String.valueOf(inStockQty));
//        }
        return convertView;
    }

    static class viewHolder{
        TextView itemCode;
        TextView description;
        TextView UOM;
        TextView inStockQty;
        TextView requestedQty;
        //EditText fulfillQty;

    }

}
