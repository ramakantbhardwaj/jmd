package com.app.jmd;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.Html;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AppPrefrences extends Application {

    private  static AppPrefrences appPrefrences;
    private SharedPreferences sharedPreferences;

    public AppPrefrences(Context context) {
        sharedPreferences = context.getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
    }

    public  static AppPrefrences getInstance(Context context){
        if(appPrefrences == null){
            appPrefrences = new AppPrefrences(context);
        }
        return appPrefrences;
    }

    public  void saveDataToPrefs(String key, String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public  void saveIntDataToPrefs(String key, int value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }
    public static void alertMsg(String s, Context c) {

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(c);
        builder.setTitle(Html.fromHtml("<font color='#EC407A'>alert !!</font>"));
        builder.setCancelable(true);
        //builder.setIcon(R.drawable.icon_alert);
        builder.setMessage(s);
        builder.setPositiveButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                new Handler().postDelayed(() -> {
                    dialog.dismiss();

                }, 1500);
            }
        });

        builder.create();
        builder.show();
    }

    public static void alertBox(String s, Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle(Html.fromHtml("<font color='#EC407A'>alert !!</font>"));
        builder.setCancelable(false);
        //builder.setIcon(R.drawable.icon_alert);
        builder.setMessage(s);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        builder.create();
        builder.show();
    }


    //for checkbox value SAVE
    public void saveArrayList(ArrayList<String> list, String key){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public ArrayList<String> getArrayList(String key){

        Gson gson = new Gson();
        String json = sharedPreferences.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public String getDataFromPrefs(String key){
        if(sharedPreferences!=null){
            return  sharedPreferences.getString(key,"");
        }
        return "";
    }
    public int getIntDataFromPrefs(String key){
        if(sharedPreferences != null){
            return  sharedPreferences.getInt(key,0);
        }
        return 0;
    }

    public  void removeFromPref(String key){
        sharedPreferences.edit().remove(key).apply();
    }


    public void removePrefs() {
        sharedPreferences.edit().remove("is_login").remove("Yes").apply();
    }
//       AppPrefrences.getInstance(CartViewActivity.this).getDataFromPrefs("lgn_user_code"),
//                        String.valueOf(sumttqty),amount,
//            AppPrefrences.getInstance(CartViewActivity.this).getDataFromPrefs("party_name"),
//                        AppPrefrences.getInstance(CartViewActivity.this).getDataFromPrefs("transport_code"),
//                        AppPrefrences.getInstance(CartViewActivity.this).getDataFromPrefs("account_code"),
//                        AppPrefrences.getInstance(CartViewActivity.this).getDataFromPrefs("eit_booking"),
//                        AppPrefrences.getInstance(CartViewActivity.this).getDataFromPrefs("eit_marka"));
}
