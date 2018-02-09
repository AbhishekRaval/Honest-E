package com.honeste.honest_e.commonclasses;

import android.os.StrictMode;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by abhis on 06-Apr-17.
 */

public class EditProfileModel {
String name, area, dob, phone, gender, pass, email;
    public HashMap<String,String> getdetails(int rid)
    {
        HashMap<String,String> Hprofile =new HashMap<>();
        String imageName = "";
        StrictMode.ThreadPolicy s=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(s);

        try {
            CommonURL c=new CommonURL();
            String ip=c.getIP("Edit_profile_data.php");

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
                name = jsonobject2.getString("name");
                area = jsonobject2.getString("area");
                dob = jsonobject2.getString("dob");
                phone = jsonobject2.getString("phone");
                gender = jsonobject2.getString("gender");
                pass = jsonobject2.getString("pass");
                email = jsonobject2.getString("email");

                Hprofile.put("name",name);
                Hprofile.put("area",area);
                Hprofile.put("dob",dob);
                Hprofile.put("phone",phone);
                Hprofile.put("gender",gender);
                Hprofile.put("pass",pass);
                Hprofile.put("email",email);
            }
            else
            {
                //Toast.makeText(this, "insertion failed", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e) {
            //Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return Hprofile;
    }

}
