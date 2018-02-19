package com.honeste.honest_e.commonclasses;

import android.os.StrictMode;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by abhis on 19-Mar-17.
 */

public class common_functions {
    int rid_comp,rid_lid;
    int del_id;
    int profile_flag, issetlike_flag, insert_likeFlag,delete_likeFlag,isset_email;
    int insert_report_complain;

     public int getRidfromCompID(int abc){
         StrictMode.ThreadPolicy s=new StrictMode.ThreadPolicy.Builder().permitAll().build();
         StrictMode.setThreadPolicy(s);

         try {
             CommonURL c=new CommonURL();
             String ip=c.getIP("RidfromCompID.php");
             URL objUrl = new URL(ip);
             HttpURLConnection objUrlConnection = (HttpURLConnection)objUrl.openConnection();
             objUrlConnection.setRequestMethod("POST");
             objUrlConnection.setRequestProperty("Content-type","Application/json");
             objUrlConnection.setRequestProperty("Accept","Application/json");

             objUrlConnection.setDoOutput(true);
             objUrlConnection.setDoInput(true);

             objUrlConnection.connect();

             JSONObject objJson = new JSONObject();
             objJson.put("compid",abc);

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
                 String rid1 = jsonobject2.getString("rid");
                 rid_comp = Integer.parseInt(rid1);
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

         return rid_comp;
     }

     public int getRidfromLid(int abc)
     {
         StrictMode.ThreadPolicy s=new StrictMode.ThreadPolicy.Builder().permitAll().build();
         StrictMode.setThreadPolicy(s);

         try {
             CommonURL c=new CommonURL();
             String ip=c.getIP("RidFromLid.php");
             URL objUrl = new URL(ip);
             HttpURLConnection objUrlConnection = (HttpURLConnection)objUrl.openConnection();
             objUrlConnection.setRequestMethod("POST");
             objUrlConnection.setRequestProperty("Content-type","Application/json");
             objUrlConnection.setRequestProperty("Accept","Application/json");

             objUrlConnection.setDoOutput(true);
             objUrlConnection.setDoInput(true);

             objUrlConnection.connect();

             JSONObject objJson = new JSONObject();
             objJson.put("lid",abc);

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
                 String rid1 = jsonobject2.getString("rid");
                 rid_lid = Integer.parseInt(rid1);
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
     public int deletecomplaint(int complaintid){
         StrictMode.ThreadPolicy s=new StrictMode.ThreadPolicy.Builder().permitAll().build();
         StrictMode.setThreadPolicy(s);

         try {
             CommonURL c=new CommonURL();
             String ip=c.getIP("DeleteComplaint.php");

             URL objUrl = new URL(ip);
             HttpURLConnection objUrlConnection = (HttpURLConnection)objUrl.openConnection();
             objUrlConnection.setRequestMethod("POST");
             objUrlConnection.setRequestProperty("Content-type","Application/json");
             objUrlConnection.setRequestProperty("Accept","Application/json");

             objUrlConnection.setDoOutput(true);
             objUrlConnection.setDoInput(true);

             objUrlConnection.connect();

             JSONObject objJson = new JSONObject();
             objJson.put("cid",complaintid);

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
                 String rid1 = jsonobject2.getString("idresult");
                 del_id = Integer.parseInt(rid1);
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
         return  del_id;
     }

    public int deletecomment(int commenttid){
        StrictMode.ThreadPolicy s=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(s);

        try {
            CommonURL c=new CommonURL();
            String ip=c.getIP("DeleteComment.php");

            URL objUrl = new URL(ip);
            HttpURLConnection objUrlConnection = (HttpURLConnection)objUrl.openConnection();
            objUrlConnection.setRequestMethod("POST");
            objUrlConnection.setRequestProperty("Content-type","Application/json");
            objUrlConnection.setRequestProperty("Accept","Application/json");

            objUrlConnection.setDoOutput(true);
            objUrlConnection.setDoInput(true);

            objUrlConnection.connect();

            JSONObject objJson = new JSONObject();
            objJson.put("cid",commenttid);

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
                String rid1 = jsonobject2.getString("idresult");
                del_id = Integer.parseInt(rid1);
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
        return  del_id;
    }

    public int deletecommentforcompdelete(int compid)
    {
        StrictMode.ThreadPolicy s=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(s);

        try {
            CommonURL c=new CommonURL();
            String ip=c.getIP("Deletecomtforcomp.php");

            URL objUrl = new URL(ip);
            HttpURLConnection objUrlConnection = (HttpURLConnection)objUrl.openConnection();
            objUrlConnection.setRequestMethod("POST");
            objUrlConnection.setRequestProperty("Content-type","Application/json");
            objUrlConnection.setRequestProperty("Accept","Application/json");

            objUrlConnection.setDoOutput(true);
            objUrlConnection.setDoInput(true);

            objUrlConnection.connect();

            JSONObject objJson = new JSONObject();
            objJson.put("cid",compid);

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
                String rid1 = jsonobject2.getString("idresult");
                del_id = Integer.parseInt(rid1);
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
        return  del_id;
    }

    public int deletelikesforcomment(int compid)
    {StrictMode.ThreadPolicy s=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(s);

        try {
            CommonURL c=new CommonURL();
            String ip=c.getIP("DeleteLikesforcmt.php");

            URL objUrl = new URL(ip);
            HttpURLConnection objUrlConnection = (HttpURLConnection)objUrl.openConnection();
            objUrlConnection.setRequestMethod("POST");
            objUrlConnection.setRequestProperty("Content-type","Application/json");
            objUrlConnection.setRequestProperty("Accept","Application/json");

            objUrlConnection.setDoOutput(true);
            objUrlConnection.setDoInput(true);

            objUrlConnection.connect();

            JSONObject objJson = new JSONObject();
            objJson.put("cid",compid);

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
                String rid1 = jsonobject2.getString("idresult");
                del_id = Integer.parseInt(rid1);
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
        return  del_id;
    }

    public int delCompImgCID(int compid)
    {
        StrictMode.ThreadPolicy s=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(s);

        try {
            CommonURL c=new CommonURL();
            String ip=c.getIP("DeleteComplainImage.php");

            URL objUrl = new URL(ip);
            HttpURLConnection objUrlConnection = (HttpURLConnection)objUrl.openConnection();
            objUrlConnection.setRequestMethod("POST");
            objUrlConnection.setRequestProperty("Content-type","Application/json");
            objUrlConnection.setRequestProperty("Accept","Application/json");

            objUrlConnection.setDoOutput(true);
            objUrlConnection.setDoInput(true);

            objUrlConnection.connect();

            JSONObject objJson = new JSONObject();
            objJson.put("cid",compid);

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
                String rid1 = jsonobject2.getString("idresult");
                del_id = Integer.parseInt(rid1);
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
        return  del_id;
    }

    public String getImgNamefromComplainID(int cid)
    {
        String imageName = "";
        StrictMode.ThreadPolicy s=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(s);

        try {
            CommonURL c=new CommonURL();
            String ip=c.getIP("getImagename.php");

            URL objUrl = new URL(ip);
            HttpURLConnection objUrlConnection = (HttpURLConnection)objUrl.openConnection();
            objUrlConnection.setRequestMethod("POST");
            objUrlConnection.setRequestProperty("Content-type","Application/json");
            objUrlConnection.setRequestProperty("Accept","Application/json");

            objUrlConnection.setDoOutput(true);
            objUrlConnection.setDoInput(true);

            objUrlConnection.connect();

            JSONObject objJson = new JSONObject();
            objJson.put("cid",cid);

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
                imageName = jsonobject2.getString("imgname");
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
        return imageName;
    }

    public int checkImgset(int rid){

        StrictMode.ThreadPolicy s=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(s);

        try {
            CommonURL c=new CommonURL();
            String ip=c.getIP("ProfileIsset.php");

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
                profile_flag = (Integer.parseInt(jsonobject2.getString("flag")));
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
    return profile_flag;
    }

    public String getprofileimagename(int lid)
    {
        String imageName = "";
        StrictMode.ThreadPolicy s=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(s);

        try {
            CommonURL c=new CommonURL();
            String ip=c.getIP("getProfileImageName.php");

            URL objUrl = new URL(ip);
            HttpURLConnection objUrlConnection = (HttpURLConnection)objUrl.openConnection();
            objUrlConnection.setRequestMethod("POST");
            objUrlConnection.setRequestProperty("Content-type","Application/json");
            objUrlConnection.setRequestProperty("Accept","Application/json");

            objUrlConnection.setDoOutput(true);
            objUrlConnection.setDoInput(true);

            objUrlConnection.connect();

            JSONObject objJson = new JSONObject();
            objJson.put("lid",lid);

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
                imageName = jsonobject2.getString("imgname");
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
        return imageName;
    }

    public int issetLiked(int rid, int cid)
    {
        StrictMode.ThreadPolicy s=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(s);

        try {
            CommonURL c=new CommonURL();
            String ip=c.getIP("issetLike.php");

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
            objJson.put("cid",cid);

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
                issetlike_flag = (Integer.parseInt(jsonobject2.getString("flag")));
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
        return issetlike_flag;
    }

    public int InsertLike(int rid, int cid)
    {
        StrictMode.ThreadPolicy s=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(s);

        try {
            CommonURL c=new CommonURL();
            String ip=c.getIP("insertLike.php");

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
            objJson.put("cid",cid);

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
                insert_likeFlag = (Integer.parseInt(jsonobject2.getString("id")));
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
        return insert_likeFlag;
    }

    public int deletelike(int compid, int rid)
    {
        StrictMode.ThreadPolicy s=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(s);

        try {
            CommonURL c=new CommonURL();
            String ip=c.getIP("deleteLike.php");

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
            objJson.put("cid",compid);

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
                delete_likeFlag = (Integer.parseInt(jsonobject2.getString("id")));
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
        return delete_likeFlag;
    }

    public int emailExistsCheck(String email)
    {
        StrictMode.ThreadPolicy s=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(s);

        try {
            CommonURL c=new CommonURL();
            String ip=c.getIP("issetEmail.php");

            URL objUrl = new URL(ip);
            HttpURLConnection objUrlConnection = (HttpURLConnection)objUrl.openConnection();
            objUrlConnection.setRequestMethod("POST");
            objUrlConnection.setRequestProperty("Content-type","Application/json");
            objUrlConnection.setRequestProperty("Accept","Application/json");

            objUrlConnection.setDoOutput(true);
            objUrlConnection.setDoInput(true);

            objUrlConnection.connect();

            JSONObject objJson = new JSONObject();
            objJson.put("emailid",email);
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
                isset_email = (Integer.parseInt(jsonobject2.getString("flag")));
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
        return isset_email;
    }
    public int insertReport(int rid, int compid)
    {
        StrictMode.ThreadPolicy s=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(s);

        try {
            CommonURL c=new CommonURL();
            String ip=c.getIP("reportcomplaint.php");

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
            objJson.put("cid",compid);

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
                insert_report_complain = (Integer.parseInt(jsonobject2.getString("id")));
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
        return insert_report_complain;
    }
}
