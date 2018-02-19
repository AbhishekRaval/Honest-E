package com.honeste.honest_e.commonclasses;

import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by abhis on 22-Mar-17.
 */

public class SubCategoryList {

    ArrayList<String> subcategory;
    String JSON_String;

    public ArrayList<String> getSubcat(String complaint)
    {
        StrictMode.ThreadPolicy s=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(s);
        subcategory = new ArrayList<>();
        try {
            CommonURL c = new CommonURL();
            String ip = c.getIP("Complaint_Subcategory.php");
            URL url = new URL(ip);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            httpURLConnection.connect();

            JSONObject objJson = new JSONObject();
            objJson.put("cid",complaint);
            DataOutputStream objDOS = new DataOutputStream(httpURLConnection.getOutputStream());
            objDOS.write(objJson.toString().getBytes());


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
                    subcategory.add(cat.getString("Subcat"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //  Toast.makeText(NewComplaint.this,e.toString(), Toast.LENGTH_SHORT).show();
        }
        return subcategory;
    }

    }

