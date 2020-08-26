package com.example.team5_luss;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Model.CustomAdjustmentVoucher;
import Model.Item;
import Model.ViewModel.CustomItem;

public class ItemListAdapter extends ArrayAdapter<CustomItem> implements Filterable {

    private Context context;
    private LayoutInflater inflater;
    //private CustomItem[] items; // array listing

    private List<CustomItem> items;
    private List<CustomItem> arraylist;

    public ItemListAdapter(@NonNull Context context, int resource, List<CustomItem> items) {
        super(context, resource, items);

        this.context = context;
        this.items = items;

        //this.arraylist = new ArrayList<CustomItem>();
        //this.arraylist.addAll(items);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ItemListAdapter.viewHolder holder;

        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adjust_voucher_listview, null);

            holder = new ItemListAdapter.viewHolder();
            holder.itemCode = (TextView) convertView.findViewById(R.id.itemCode);
            holder.category = (TextView) convertView.findViewById(R.id.itemCategory);
            holder.description = (TextView) convertView.findViewById(R.id.itemDescription);
            holder.quantity = (TextView) convertView.findViewById(R.id.itemQty);
            convertView.setTag(holder);
        } else {
            holder = (ItemListAdapter.viewHolder) convertView.getTag();
        }

        String itemCode = (items.get(position).getItemCode());
        String category = (items.get(position).getCategoryName());
        String description = (items.get(position).getItemName());
        int quantity = (items.get(position).getInStockQty());

        holder.itemCode.setText(String.valueOf(itemCode));
        holder.category.setText(String.valueOf(category));
        holder.description.setText(String.valueOf(description));
        holder.quantity.setText(String.valueOf(quantity));

        return convertView;
    }

    static class viewHolder {
        TextView itemCode;
        TextView category;
        TextView description;
        TextView quantity;
    }

    // Filter Class
/*    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        items.clear();
        if (charText.length() == 0) {
            items.addAll(arraylist);
        } else {
            for (CustomItem wp : arraylist) {
                if (wp.getItemName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    arraylist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }*/

    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final List<CustomItem> results = new ArrayList<CustomItem>();
                if (arraylist == null)
                    arraylist = items;
                if (constraint != null) {
                    if (arraylist != null && arraylist.size() > 0) {
                        for (final CustomItem g : arraylist) {
                            if (g.getItemName().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                items = (ArrayList<CustomItem>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getCount() {
        return items.size();
    }

    //@Override
    /*public Object getItem(int position) {
        return items.get(position);
    }*/

    @Override
    public long getItemId(int position) {
        return position;
    }

}