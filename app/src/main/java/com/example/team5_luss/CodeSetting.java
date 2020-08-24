package com.example.team5_luss;

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
}
