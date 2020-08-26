package com.example.team5_luss;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public  class CodeSetting {

    public static <T> List<T> convertArrayToList(T array[])
    {
        List<T> list = new ArrayList<>();
        for (T t : array) {
            list.add(t);
        }
        return list;
    }

    public  static String convertDateString(String dateString)
    {
         String[] arrOfStr = dateString.split("T", 2);
         return  arrOfStr[0];

    }

    public static void warningToastMsg(Context context, String message){
        Toast toast=Toast.makeText(context,message,Toast.LENGTH_SHORT);
        View view =toast.getView();
        view.setBackgroundColor(Color.parseColor("#ff6633"));
        TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
        toastMessage.setTextColor(Color.WHITE);
        toast.show();
    }

    public static boolean isQtyNull(ArrayList<Integer> quantity){
        int sum = 0;
        for(int i = 0; i< quantity.size(); i++){
            sum += quantity.get(i);
        }
        if(sum > 0){
            return true;
        }
        else return false;
    }
}
