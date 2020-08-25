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

import Model.CustomAdjustmentVoucher;
import Model.ViewModel.CustomItem;

public class ItemListAdapter extends ArrayAdapter {

    private Context context;
    private LayoutInflater inflater;
    private CustomItem[] items; // array listing

    public ItemListAdapter(@NonNull Context context, int resource, CustomItem[] items) {
        super(context, resource, items);

        this.context = context;
        this.items = items;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ItemListAdapter.viewHolder holder;

        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        }

        if(convertView == null){
            convertView = inflater.inflate(R.layout.adjust_voucher_listview, null);

            holder = new ItemListAdapter.viewHolder();
            holder.itemCode = (TextView)convertView.findViewById(R.id.itemCode);
            holder.category = (TextView)convertView.findViewById(R.id.itemCategory);
            holder.description = (TextView)convertView.findViewById(R.id.itemDescription);
            holder.quantity = (TextView)convertView.findViewById(R.id.itemQty);
            convertView.setTag(holder);
        }else{
            holder =(ItemListAdapter.viewHolder) convertView.getTag();
        }

        String itemCode = (items[position].getItemCode());
        String category = (items[position].getCategoryName());
        String description = (items[position].getItemName());
        int quantity = (items[position].getInStockQty());

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
