package com.stickynote.ttmiwn.myweatherinfo;

//ãð®ãåå²æå

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.annotation.UiThread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

// Web APIããåå¾ããJSONæå­åã»å¤©æ°æå ±ãããå¿è¦ãªå¤ãåãã ãHashMapã«ç»é²
//

final class ConversionJSON {

    final String DEBUG_TAG = "WetherInfo";
    Map<String, String> tempDatas = new HashMap<>();

    public Map<String, String> getMap() {

        return tempDatas;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ConversionJSON(String _result) {

        try {
            // ã«ã¼ãJSONãªãã¸ã§ã¯ããçæã                                âroot
            // ï¿¥ CityJSONãªãã¸ã§ã¯ããã rootããåå¾ãæ¹è£ãä¸æ®µæ½ãã¤ã¡ã¼ã¸  ãâCityJSONã
            // é½å¸åã»æ¥ã®åºã»æ¥ã®å¥ãã"getString"ã§åå¾ãããããããããããã  ã»sunriseã7:00
            JSONObject rootJSON = new JSONObject(_result);

            JSONObject citydJSON = rootJSON.getJSONObject("city");
            String cityName = citydJSON.getString("name");
            String sunriseEp = citydJSON.getString("sunrise");
            String sunsetEp = citydJSON.getString("sunset");

            //ãrootä¸ã"list"ã¨ããåã®ãå¨ã¦ã®æéå¸¯å¤©æ°è©³ç´°ããéå"getJSONArray"ã§åå¾
            JSONArray listJSONArray = rootJSON.getJSONArray("list");

            // ï¿¥ ä¸è¨éåã®ä¸ã¤ç®"list Array[0]"é¸æã3æéåºåãã§ãã£ã¨ãè¿ãæéã®å¤©æ°äºå ±
            JSONObject thisTimeJSON = listJSONArray.getJSONObject(0);
            String infoTime = thisTimeJSON.getString("dt_txt"); // æå»ç¿å¾
            // ï¿¥ "main"ãªãã¸ã§ã¯ããæ°æ¸©ãæ¹¿åº¦ããåå¾
            JSONObject mainJSON = thisTimeJSON.getJSONObject("main");
            String temper = mainJSON.getString("temp");
            // è¬ã®weatheréåãåå¾ãä¸åç®ããªãã¸ã§ã¯ãã«
            JSONArray weatherJSONArray = thisTimeJSON.getJSONArray("weather");
            JSONObject weatherJSON = weatherJSONArray.getJSONObject(0);
            // ç¾å¨ã®å¤©æ°æå ±æå­åãåå¾
            String description = weatherJSON.getString("description");
            String icon = weatherJSON.getString("icon");

            // ä»®ãã¼ã¿MAPã«ç»é²ã"sunrise"ç­ãæéã¯å å·¥ãå¿è¦
            tempDatas.put("cityName", cityName);
            tempDatas.put("description0", description);
            tempDatas.put("temper0", temper);
            tempDatas.put("icon0", icon);

            // timezone å å·¥ãã¦ç»é²
            TimeZoneChange tzc = new TimeZoneChange();
            String sunrise = tzc.epochUnixTimeChange(sunriseEp);
            String sunset = tzc.epochUnixTimeChange(sunsetEp);

            tempDatas.put("sunrise", sunrise);
            tempDatas.put("sunset", sunset);
            // æéã®è¡¨ç¤º
            String nearTime = tzc.ZoneChangeCut(infoTime);

            //ãâãããã¾ã§ãé½å¸ã®æå ±ç»é²ããâãããããããã®é½å¸ã®3æéåºåãã®å¤©æ°

            for (int i = 1; 6> i; i++) {
                thisTimeJSON = listJSONArray.getJSONObject(i);  //listarray[1]3jkikanngo
                mainJSON = thisTimeJSON.getJSONObject("main"); //main:[
                // Mapã«ãããã ã­ã¼å¤ "temp + 1~5"ã, å¤ãâJSONã-ãmainã(ï½ãæ¸©åº¦ãï½ã«âââä»ãã¦)ç»é²ãâ
                tempDatas.put(("temper" + i), (mainJSON.getString("temp") +" â"));
                weatherJSONArray = thisTimeJSON.getJSONArray("weather");
                weatherJSON = weatherJSONArray.getJSONObject(0);    //ä¸ã¤ããç¡ãè¬éåãªã®ã§0åºå®
                tempDatas.put(("description" + i), (weatherJSON.getString("description")));
                tempDatas.put(("icon" + i), (weatherJSON.getString("icon")));
                tempDatas.put(("dTime" + i), (thisTimeJSON.getString("dt_txt")));
            }
             if (cityName.equals("æ­å¹å¸")) {
                tempDatas.put("nearTime", nearTime);
                tempDatas.put("hoursLater6", (tzc.ZoneChangeCut(tempDatas.get("dTime2"))));
                tempDatas.put("hoursLater3", (tzc.ZoneChangeTime(tempDatas.get("dTime1"))));
                tempDatas.put("hoursLater9", (tzc.ZoneChangeTime(tempDatas.get("dTime3"))));
                tempDatas.put("hoursLater12", (tzc.ZoneChangeTime(tempDatas.get("dTime4"))));
                tempDatas.put("hoursLater15", (tzc.ZoneChangeTime(tempDatas.get("dTime5"))));
            }

        } catch (JSONException ex) {
            Log.e(DEBUG_TAG, "JSONè§£æå¤±æ", ex);
        } catch (NullPointerException ex){
            Log.w(DEBUG_TAG, "JSONã®å¤ã«nullæããã­ã¼nameç¢ºèª", ex);
        }
    }
}