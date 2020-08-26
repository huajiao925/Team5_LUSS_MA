package com.example.team5_luss;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import Model.ViewModel.CustomRequestDetail;
import Model.ViewModel.CustomRetrieval;
import Model.ViewModel.InputFilterMinMax;

public class RetrievalAdapter extends ArrayAdapter<CustomRetrieval> {
    private Context context;
    private LayoutInflater inflater;
    public static CustomRetrieval[] retrievals;



    public RetrievalAdapter(@NonNull Context context, int resource, CustomRetrieval[] retrievals) {
        super(context, resource, retrievals);

        this.context = context;
        this.retrievals = retrievals;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        viewHolder holder;
        final CustomRetrieval retrieval = getItem(position);

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
            holder.retrievedQty=(EditText)convertView.findViewById(R.id.retrievedQty);
            holder.adjust =(Button)convertView.findViewById(R.id.adjustBtn);
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
        holder.adjust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,AdjustVoucherCreate.class);
                intent.putExtra("itemId", retrievals[position].getItemID());
                context.startActivity(intent);
            }
        });

        final EditText retrievedQtyEdit = convertView.findViewById(R.id.retrievedQty);
        holder.retrievedQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //retrieval.setRequestedQty(Integer.parseInt(retrievedQtyEdit.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    retrieval.setRequestedQty(Integer.parseInt(retrievedQtyEdit.getText().toString()));
                } catch (NumberFormatException e) {
                }
            }
        });

        if(inStockQty >= requestedQty)
            retrievedQtyEdit.setFilters(new InputFilter[]{new InputFilterMinMax(0, retrievals[position].getTotalQty())});
        else if(requestedQty > inStockQty){
            retrievedQtyEdit.setFilters(new InputFilter[]{ new InputFilterMinMax(0,retrievals[position].getInStockQty())});
        }

        return convertView;

    }

    static class viewHolder{
        TextView itemCode;
        TextView description;
        TextView location;
        TextView uom;
        TextView inStockQty;
        TextView requestedQty;
        EditText retrievedQty;
        Button adjust;
    }
}
