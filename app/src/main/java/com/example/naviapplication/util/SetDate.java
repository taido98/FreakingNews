package com.example.naviapplication.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SetDate {
    private String date;

    public SetDate(String date) {
        this.date = date;
    }

    public String getDate() {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String dateNow = sdf.format(now);
        if(date.substring(0,10).equals(dateNow.substring(0,10)))
            return date.substring(11,16);
        else if(date.substring(0,4).equals(dateNow.substring(0,4))){
            return "Ngày "+date.substring(8,10)+"/"
                    +date.substring(5,7)+" lúc "+date.substring(11,16);
        }
        return "Ngày "+date.substring(8,10)+"/"
                +date.substring(5,7)+"/"+date.substring(0,4)+" lúc "+date.substring(11,16);
    }

    public void setDate(String date) {
        this.date = date;
    }
}
