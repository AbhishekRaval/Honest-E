package com.honeste.honest_e.commonclasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.widget.ListView;
import android.widget.Toast;

import com.honeste.honest_e.R;
import com.honeste.honest_e.commonComplain;
import com.honeste.honest_e.complainAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by abhis on 07-Apr-17.
 */

public class SearchListview {
    ArrayList<commonComplain> Complains;
    String JSON_String;
    public ArrayList<commonComplain> getListviewData(String dbid,int searchid,int rid_user) {
        try {
            Complains = new ArrayList<>();
            common_functions cf1 = new common_functions();
            StrictMode.ThreadPolicy spolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(spolicy);
            try {

                CommonURL c = new CommonURL();
                String ip = c.getIP("Search_Complaints_by_id.php");
                URL url = new URL(ip);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.setRequestProperty("Accept", "application/json");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();

                JSONObject objJson = new JSONObject();
                TimeAndDate t1 = new TimeAndDate();
                String tdo = t1.getDateTime();
                objJson.put("time", tdo);
                objJson.put("searchid",searchid);
                objJson.put("dbid",dbid);
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

                    JSONArray jsonArray = new JSONArray(stringBuilder.toString());

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject = jsonArray.getJSONObject(i);
                        final commonComplain commonComplains = new commonComplain();
                        commonComplains.setArea(jsonObject.getString("Area"));
                        commonComplains.setCategory(jsonObject.getString("Cat"));
                        commonComplains.setName(jsonObject.getString("Name"));
                        commonComplains.setDesc(jsonObject.getString("Cont"));
                        commonComplains.setHours(jsonObject.getString("Time"));
                        int c1id = Integer.parseInt(jsonObject.getString("ID"));
                        commonComplains.setCompid(c1id);
                        commonComplains.setComplaint_rid(Integer.parseInt(jsonObject.getString("Complaint_rid")));
                        commonComplains.setSubcategory(jsonObject.getString("subcat"));
                        commonComplains.setLogin_rid(rid_user);
                        String imgname = cf1.getImgNamefromComplainID(c1id);
                        commonComplains.setCompimgpath(imgname);
                        Complains.add(commonComplains);
                    }
                }

            } catch (Exception e) {
                //Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            //Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return Complains;
    }
}
