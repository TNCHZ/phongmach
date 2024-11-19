package com.example.jpyou;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class setting {

    public static final String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(new Date());
    }
}