package com.honeste.honest_e;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.honeste.honest_e.commonclasses.CommonURL;
import com.honeste.honest_e.commonclasses.TimeAndDate;
import com.honeste.honest_e.commonclasses.common_functions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by abhis on 06-Apr-17.
 */

public class profile_all_complaints extends Fragment {

int logid;
ListView listView;
ArrayList<commonComplain> Complains;
complainAdapter complainAdapter;
String JSON_String;
    int rid_user;
    TextView tvnocomplaints;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profile_mycomplaints, container, false);
        SharedPreferences shared1=getContext().getSharedPreferences("Honest-E", Context.MODE_PRIVATE);

        String id1 = shared1.getString("lid","-1");
        logid = Integer.parseInt(id1);

        listView = (ListView)v.findViewById(R.id.profile_mycomplaints);
        tvnocomplaints = (TextView)v.findViewById(R.id.profile_no_complaints);
        try {

            Complains = new ArrayList<commonComplain>();
            complainAdapter = new complainAdapter(getContext(), Complains);

            common_functions cf1 = new common_functions();
            rid_user = cf1.getRidfromLid(logid);

            StrictMode.ThreadPolicy s = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(s);
            try {

                CommonURL c = new CommonURL();
                String ip = c.getIP("List_Complaints_for_Profile.php");
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
                objJson.put("userregid",rid_user);
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
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            if (Complains.isEmpty())
            {
                tvnocomplaints.setVisibility(v.VISIBLE);
            }
            listView.setVisibility(v.VISIBLE);
            listView.setAdapter(complainAdapter);
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        return v;
    }
}
