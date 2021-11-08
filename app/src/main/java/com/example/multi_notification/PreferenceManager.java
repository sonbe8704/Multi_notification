package com.example.multi_notification;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.IDNA;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class PreferenceManager {
    public static final String PREFERENCES_NAME = "rebuild_preference";



    private static final String DEFAULT_VALUE_STRING = "";

    private static final boolean DEFAULT_VALUE_BOOLEAN = false;

    private static final int DEFAULT_VALUE_INT = -1;

    private static final long DEFAULT_VALUE_LONG = -1L;

    private static final float DEFAULT_VALUE_FLOAT = -1F;



    private static SharedPreferences getPreferences(Context context) {

        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

    }

    public void setInfo(Context context, String key, ArrayList<Information> infos){
        SharedPreferences prefs = getPreferences(context);

        SharedPreferences.Editor editor = prefs.edit();

        JSONArray a = new JSONArray();
        Gson gson =new GsonBuilder().create();
        for (int i = 0; i < infos.size(); i++) {
            String string = gson.toJson(infos.get(i), Information.class);
            a.put(string);
        }
        if (!infos.isEmpty()) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }
        editor.apply();

        editor.commit();
    }

    public ArrayList<Information> getInfo(Context context, String key) {

        SharedPreferences prefs = getPreferences(context);
        String json = prefs.getString(key, null);
        ArrayList<Information> infos= new ArrayList<Information>();
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson =new GsonBuilder().create();
        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                for (int i = 0; i < a.length(); i++) {
                    Information info= gson.fromJson( a.get(i).toString() , Information.class);
                    infos.add(info);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return infos;

    }


    /**

     * 키 값 삭제

     * @param context

     * @param key

     */

    public static void removeKey(Context context, String key) {

        SharedPreferences prefs = getPreferences(context);

        SharedPreferences.Editor edit = prefs.edit();

        edit.remove(key);

        edit.commit();

    }



    /**

     * 모든 저장 데이터 삭제

     * @param context

     */

    public static void clear(Context context) {

        SharedPreferences prefs = getPreferences(context);

        SharedPreferences.Editor edit = prefs.edit();

        edit.clear();

        edit.commit();

    }
}
