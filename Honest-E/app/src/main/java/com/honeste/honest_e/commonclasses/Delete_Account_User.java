package com.honeste.honest_e.commonclasses;

import android.os.StrictMode;

import com.honeste.honest_e.commonComplain;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by abhis on 07-Apr-17.
 */

public class Delete_Account_User {
    ArrayList<Integer> ComplainList;
    String JSON_String;
    public ArrayList getAllComplainID(int rid)
    {
        ComplainList = new ArrayList<Integer>();
        StrictMode.ThreadPolicy s=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(s);

        try {
            CommonURL c=new CommonURL();
            String ip=c.getIP("All_complaint_cid.php");

            URL objUrl = new URL(ip);
            HttpURLConnection objUrlConnection = (HttpURLConnection)objUrl.openConnection();
            objUrlConnection.setRequestMethod("POST");
            objUrlConnection.setRequestProperty("Content-type","Application/json");
            objUrlConnection.setRequestProperty("Accept","Application/json");

            objUrlConnection.setDoOutput(true);
            objUrlConnection.setDoInput(true);

            objUrlConnection.connect();

            JSONObject objJson = new JSONObject();
            objJson.put("rid",rid);

            DataOutputStream objDOS = new DataOutputStream(objUrlConnection.getOutputStream());
            objDOS.write(objJson.toString().getBytes());
            int respcode = objUrlConnection.getResponseCode();

            if (respcode == 200) {

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(objUrlConnection.getInputStream())
                );
                StringBuilder stringBuilder = new StringBuilder();
                while ((JSON_String = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_String);
                }

                JSONArray jsonArray = new JSONArray(stringBuilder.toString());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject = jsonArray.getJSONObject(i);
                    ComplainList.add(Integer.parseInt(jsonObject.getString("cid")));
                } }
            else
            {
                //Toast.makeText(this, "insertion failed", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e) {
            //Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return ComplainList;
    }

    public void deleteallcomplaint(ArrayList<Integer> abc)
    {
        common_functions cf2 = new common_functions();
        for (int i=0;i<abc.size();i++)
        {
            int result2 = cf2.deletecommentforcompdelete(abc.get(i));
            int result =  cf2.deletecomplaint(abc.get(i));
            int result3 = cf2.delCompImgCID(abc.get(i));
            int result4 = cf2.deletelikesforcomment(abc.get(i));

        }
    }

    public int deletelogreg(int rid)
    {
        int rid_lid = 0;

        StrictMode.ThreadPolicy s=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(s);

        try {
            CommonURL c=new CommonURL();
            String ip=c.getIP("Delete_All_Profile.php");
            URL objUrl = new URL(ip);
            HttpURLConnection objUrlConnection = (HttpURLConnection)objUrl.openConnection();
            objUrlConnection.setRequestMethod("POST");
            objUrlConnection.setRequestProperty("Content-type","Application/json");
            objUrlConnection.setRequestProperty("Accept","Application/json");

            objUrlConnection.setDoOutput(true);
            objUrlConnection.setDoInput(true);

            objUrlConnection.connect();

            JSONObject objJson = new JSONObject();
            objJson.put("lid",rid);

            DataOutputStream objDOS = new DataOutputStream(objUrlConnection.getOutputStream());
            objDOS.write(objJson.toString().getBytes());
            int respcode = objUrlConnection.getResponseCode();
            if (respcode==200)
            {
                BufferedReader bfrdr = new BufferedReader(new InputStreamReader(objUrlConnection.getInputStream()));
                String line = "";
                StringBuilder response = new StringBuilder();
                while ((line = bfrdr.readLine()) != null)
                {
                    response.append(line);
                }
                JSONObject jsonobject2 = new JSONObject(response.toString());
                String result = jsonobject2.getString("idresult");
               rid_lid = Integer.parseInt(result);
                //   String msg = jsonobject2.getString("Message");

            }
            else
            {
                //Toast.makeText(this, "insertion failed", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e) {
            //Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return rid_lid;
    }
}
