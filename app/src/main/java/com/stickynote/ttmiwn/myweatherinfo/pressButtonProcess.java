package com.stickynote.ttmiwn.myweatherinfo;

// ð®ãç¡äºç¨¼å
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

import java.util.Map;

// mapç«ã¡ä¸ãæã«æ¤ç´¢ãã
final class pressButtonProcess {
    static String serchCity = "";
    static String serchSpot = "";

    @RequiresApi(api = Build.VERSION_CODES.N)
    void setSerchCity(Map<String, String> wmap){
        serchCity = wmap.getOrDefault("cityName", "åæµ·é");
    }

    String getSerchCity(){
        return serchCity;
    }

    String getSerchSpot(){
        return serchSpot;
    }

    String serchSpotSwitch(View view) {

        switch(view.getId()){
            case R.id.iBtHotel:
                serchSpot = " ããã«";
                break;
            case R.id.iBtcamp:
                serchSpot = " ã­ã£ã³ã";
                break;
            case R.id.iBtSpa:
                serchSpot = " æ¥å¸°ãå¥æµ´";
                break;
            case R.id.iBtRdSta:
                serchSpot = " éã®é§";
                break;
            case R.id.iBtSpot:
                serchSpot = " è¦³åå°";
                break;
            case R.id.imageBtClear:
                serchSpot = "";
                break;
            default:
                break;
        }
        return serchSpot;
    }
}