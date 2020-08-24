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

import Model.ViewModel.CustomRetrieval;

public class RetrievalAdapter extends ArrayAdapter {
    private Context context;
    private LayoutInflater inflater;
    private CustomRetrieval[] retrievals;

    public RetrievalAdapter(@NonNull Context context, int resource, CustomRetrieval[] retrievals) {
        super(context, resource, retrievals);

        this.context = context;
        this.retrievals = retrievals;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        viewHolder holder;

        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        }

        if(convertView == null){
            convertView =inflater.inflate(R.layout.retrieved_items, null);

            holder = new viewHolder();
            holder.itemCode =(TextView)convertView.findViewById(R.id.itemCode);
            holder.description =(TextView)convertView.findViewById(R.id.description);
            holder.location =(TextView)convertView.findViewById(R.id.location);
            holder.uom =(TextView)convertView.findViewById(R.id.uom);
            holder.inStockQty =(TextView)convertView.findViewById(R.id.inStock);
            holder.requestedQty =(TextView)convertView.findViewById(R.id.reqQty);
            convertView.setTag(holder);
        }else {
            holder = (viewHolder) convertView.getTag();
        }

        String itemCode =(retrievals[position].getItemCode());
        String description = (retrievals[position].getItemName());
        String location = (retrievals[position].getLocation());
        String uom = (retrievals[position].getUOM());
        int inStockQty = (retrievals[position].getInStockQty());
        int requestedQty = (retrievals[position].getTotalQty());

        holder.itemCode.setText(String.valueOf(itemCode));
        holder.description.setText(String.valueOf(description));
        holder.location.setText(String.valueOf(location));
        holder.uom.setText(String.valueOf(uom));
        holder.inStockQty.setText(String.valueOf(inStockQty));
        holder.requestedQty.setText(String.valueOf(requestedQty));

        return convertView;

    }

    static class viewHolder{
        TextView itemCode;
        TextView description;
        TextView location;
        TextView uom;
        TextView inStockQty;
        TextView requestedQty;
    }
}
