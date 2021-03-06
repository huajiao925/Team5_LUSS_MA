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

import java.util.List;

import Model.CustomAdjustmentVoucher;

public class AdjustVoucherAdapter extends ArrayAdapter {

    private Context context;
    private LayoutInflater inflater;
    private CustomAdjustmentVoucher[] voucherList; // array listing

    public AdjustVoucherAdapter(@NonNull Context context, int resource, CustomAdjustmentVoucher[] voucherList) {
        super(context, resource, voucherList);

        this.context = context;
        this.voucherList = voucherList;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        viewHolder holder;

        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        }

        if(convertView == null){
            convertView = inflater.inflate(R.layout.adjust_voucher_listview, null);

            holder = new viewHolder();
            holder.itemCode = (TextView)convertView.findViewById(R.id.itemCode);
            holder.category = (TextView)convertView.findViewById(R.id.itemCategory);
            holder.description = (TextView)convertView.findViewById(R.id.itemDescription);
            holder.quantity = (TextView)convertView.findViewById(R.id.itemQty);
            convertView.setTag(holder);
        }else{
            holder =(viewHolder) convertView.getTag();
        }

        String itemCode = (voucherList[position].getItemCode());
        String category = (voucherList[position].getCategoryName());
        String description = (voucherList[position].getItemName());
        int quantity = (voucherList[position].getAdjustQty());

        holder.itemCode.setText(String.valueOf(itemCode));
        holder.category.setText(String.valueOf(category));
        holder.description.setText(String.valueOf(description));
        holder.quantity.setText(String.valueOf(quantity));

        return convertView;
    }

    static class viewHolder{
        TextView itemCode;
        TextView category;
        TextView description;
        TextView quantity;
    }
}
