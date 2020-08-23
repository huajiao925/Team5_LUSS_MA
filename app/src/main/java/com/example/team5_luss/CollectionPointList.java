package com.example.team5_luss;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class CollectionPointList extends DialogFragment {


    int position = 0; //default selected item
    public interface SingleChoiceListner{
        void onPositiveButtonClicked(String[] list, int position);
        void onNegativeButtonClicked();
    }

    SingleChoiceListner mLisener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            mLisener = (SingleChoiceListner) context;
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String[] cp_list = {"behind the tree", "under the sea", "into the woods"};

        builder.setTitle("Select the Collection Point")
                .setSingleChoiceItems(cp_list, position, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        position = i;
                    }
                })
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mLisener.onPositiveButtonClicked(cp_list, position);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mLisener.onNegativeButtonClicked();
                    }
                });
        return builder.create();
    }
}
