package com.honeste.honest_e.commonclasses;

/**
 * Created by abhis on 03-Mar-17.
 */
import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AreaArrayList {
    ArrayList<String> AreaAL;
    String JSON_String;
    ArrayList<String> Area;

    public ArrayList<String> getAreaList() {
        Area = new ArrayList<String>();
        StrictMode.ThreadPolicy s=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(s);
        Area = new ArrayList<String>();
        try {
            CommonURL c = new CommonURL();
            String ip = c.getIP("Area_Zone_Details.php");
            URL url = new URL(ip);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream())
                );
                StringBuilder stringBuilder = new StringBuilder();
                while ((JSON_String = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_String);
                }

                JSONArray mJsonArray = new JSONArray(stringBuilder.toString());
                JSONObject cat = new JSONObject();
                for (int i = 0; i < mJsonArray.length(); i++) {
                    cat = mJsonArray.getJSONObject(i);
                    Area.add(cat.getString("area"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            //  Toast.makeText(NewComplaint.this,e.toString(), Toast.LENGTH_SHORT).show();
        }
        return Area;
    }
/*
    public AreaArrayList(){

        String[]  nz = {"Sardarnagar","Noblenagar", "Narodamuthiya","Kubernagar","Saijpur","Meghaninagar","Asarwa", "Naroda Road","India Colony","Krushnanagar","Thakkarnagar","Saraspur"};
        String[] sz = {"Maningar", "Kankaria","Baherampura","Danilimda","Ghodasar","Indrapuri","Kokhra","Vatva","Isanpur","Lambha"};
        String[] ez = {"Bapunagar","Rakhiyal","Gomtipur","Rajpur","Amraiwadi","Bhaipura","Mahavirnagar","Viratnagar","Odhav","Arbudanagar","Vastral","Ramol","Nikol"};
        String[] cz = {"Khadia","Kalupur","Dariapur","Shahpur","Raikhad","Jamalpur","Dudheshwar","Madhupura","Girdharnagar"};
        String[] wz = {"Paldi","Vasna","Ambawadi","Navrangpura","Stadium","Naranpura","New Wadaj","Juna Wadaj","Sabarmati","Chandkheda-Motera"};
        String[] nwz ={"Sarkhej","Vejalpur","Jodhpur","Bodakdev","Thaltej","Ghatlodia","Chandlodia","Ranip","Gota","Kali"};
        AreaAL = new ArrayList<String>();

        for (int i=0 ; i<nz.length ; i++){
            AreaAL.add(nz[i]);
        }
        for (int i=0 ; i<sz.length ; i++){
            AreaAL.add(sz[i]);
        }
        for (int i=0 ; i<ez.length ; i++){
            AreaAL.add(ez[i]);
        }
        for (int i=0 ; i<cz.length ; i++){
            AreaAL.add(cz[i]);
        }
        for (int i=0 ; i<wz.length ; i++){
            AreaAL.add(wz[i]);
        }
        for (int i=0 ; i<nwz.length ; i++){
            AreaAL.add(nwz[i]);
        }
        Collections.sort(AreaAL, String.CASE_INSENSITIVE_ORDER);
    }

    public ArrayList<String> getList(){
        return AreaAL;
    }
    public String ReturnZone(String area)
    {
        String ReturnStr = "";
        if (area.equals("Sardarnagar") || area.equals("Noblenagar") || area.equals("Narodamuthiya") || area.equals("Kubernagar") || area.equals("Saijpur") || area.equals("Meghaninagar") || area.equals("Asarwa")|| area.equals("Naroda Road") || area.equals("India Colony") || area.equals("Krushnanagar") || area.equals("Thakkarnagar") || area.equals("Saraspur"))
        {
            ReturnStr = "North Zone";
        }
        else if (area.equals("Maningar") || area.equals("Kankaria") || area.equals("Baherampura") || area.equals("Danilimda") || area.equals("Ghodasar") || area.equals("Indrapuri") || area.equals("Kokhra") || area.equals("Vatva") || area.equals("Isanpur") || area.equals("Lambha"))
        {
            ReturnStr = "South Zone";
        }

        else if (area.equals("Bapunagar") || area.equals("Rakhiyal") || area.equals("Gomtipur") || area.equals("Rajpur") || area.equals("Amraiwadi") || area.equals("Bhaipura") || area.equals("Mahavirnagar") || area.equals("Viratnagar") || area.equals("Odhav") || area.equals("Arbudanagar") || area.equals("Vastral") || area.equals("Ramol") || area.equals("Nikol"))
        {
            ReturnStr = "East Zone";
        }
        else if (area.equals("Khadia") || area.equals("Kalupur") || area.equals("Dariapur") || area.equals("Shahpur") || area.equals("Raikhad") || area.equals("Jamalpur") || area.equals("Dudheshwar") || area.equals("Madhupura") || area.equals("Girdharnagar"))
        {
            ReturnStr = "Central Zone";
        }
        else if (area.equals("Paldi") || area.equals("Vasna") || area.equals("Ambawadi") || area.equals("Navrangpura") || area.equals("Stadium") || area.equals("Naranpura") || area.equals("New Wadaj") || area.equals("Juna Wadaj") || area.equals("Sabarmati") || area.equals("Chandkheda-Motera"))
        {
            ReturnStr = "West Zone";
        }
        else
        {
            ReturnStr = "New West Zone";
        }
        return ReturnStr;
    }*/
}
