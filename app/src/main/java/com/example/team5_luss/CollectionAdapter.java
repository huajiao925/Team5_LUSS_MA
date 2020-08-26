package com.example.team5_luss;

import android.app.Activity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.ArrayList;

import Model.Request;
import Model.ViewModel.CustomRequestDetail;
import Model.ViewModel.CustomRetrieval;

public class CollectionAdapter  extends RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder>{

    private Activity activity;
    public static ArrayList<CustomRetrieval> itemList = new ArrayList<CustomRetrieval>();

    public CollectionAdapter(Activity activity, ArrayList<CustomRetrieval> itemList) {
        this.activity = activity;
        this.itemList = itemList;
    }
    @NonNull
    @Override
    public CollectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_list_item, parent,false);
        final EditText accptQty = view.findViewById(R.id.editTextNumberSigned);
        view.findViewById(R.id.plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accptQty.setText(Integer.toString(Integer.parseInt(accptQty.getText().toString())+1));
            }
        });
        view.findViewById(R.id.minus).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                accptQty.setText(Integer.toString(Integer.parseInt(accptQty.getText().toString())-1));
            }
        });

        return new CollectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CollectionViewHolder holder, int position) {

            final CustomRetrieval item = itemList.get(position);

            holder.itemCode.setText(itemList.get(position).ItemCode);
            holder.UOM.setText(itemList.get(position).UOM);
            holder.itemName.setText(itemList.get(position).ItemName);
            holder.requestedQty.setText(Integer.toString(itemList.get(position).RequestedQty));
            holder.accptQty.setText(Integer.toString(itemList.get(position).RequestedQty));

            holder.accptQty.setFilters(new InputFilter[]{ new MinMaxFilter( "0" , Integer.toString(item.getRequestedQty()))});

            item.setAcceptedQty(Integer.parseInt(holder.accptQty.getText().toString()));
            holder.accptQty.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    item.setAcceptedQty(Integer.parseInt(holder.accptQty.getText().toString()));
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }
                @Override
                public void afterTextChanged(Editable editable) {
                    if(editable.length()> 0){
                        item.setAcceptedQty(Integer.parseInt(holder.accptQty.getText().toString()));
                    }

                }
            });
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class CollectionViewHolder extends RecyclerView.ViewHolder {

        public TextView itemCode;
        public TextView itemName;
        public TextView UOM;
        public TextView requestedQty;
        public EditText accptQty;
        public Button plus;
        public Button minus;

        public CollectionViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.findViewById(R.id.plus);
            itemCode = itemView.findViewById(R.id.itemCode);
            UOM = itemView.findViewById(R.id.uom);
            itemName = itemView.findViewById(R.id.description);
            requestedQty = itemView.findViewById(R.id.reqQty);
            accptQty = itemView.findViewById(R.id.editTextNumberSigned);
            plus = itemView.findViewById(R.id.plus);
            minus = itemView.findViewById(R.id.minus);

        }
    }
}
