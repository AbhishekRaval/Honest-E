package com.honeste.honest_e;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
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

public class comment_list extends AppCompatActivity {

    ListView lvcomment;
    commentAdapter Commentadapter;
    ArrayList<commonComment> Commoncomment;
    int Compid ;
    String JSON_String;
    String logid;
    int rid_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);

        Intent intent = getIntent();
        String data = intent.getStringExtra("id");
        Compid = Integer.parseInt(data);

        SharedPreferences shared = getSharedPreferences("Honest-E",MODE_PRIVATE);
        logid = shared.getString("lid","-1");
        int id2 = Integer.parseInt(logid);

        common_functions cf1 = new common_functions();
        rid_user = cf1.getRidfromLid(id2);

        try {
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            lvcomment = (ListView) findViewById(R.id.ViewComment);
            Commoncomment = new ArrayList<commonComment>();
            Commentadapter = new commentAdapter(this, Commoncomment);

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {

                CommonURL c = new CommonURL();
                String ip = c.getIP("List_All_Comments.php");
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
                objJson.put("compID",Compid+"");

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
                        commonComment comment = new commonComment();
                        comment.setCommentid(Integer.parseInt(jsonObject.getString("ID")));
                        comment.setName(jsonObject.getString("Name"));
                        comment.setContent(jsonObject.getString("Content"));
                        comment.setTime(jsonObject.getString("Time"));
                        comment.setRid_user(rid_user);
                        comment.setRid_comment(Integer.parseInt(jsonObject.getString("comment_regid")));
                        comment.setComplaintid(Compid);
                        Commoncomment.add(comment);
                    }
                }

            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            lvcomment.setAdapter(Commentadapter);
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onAddComment(View view)
    {
        StrictMode.ThreadPolicy spolicy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(spolicy);

        EditText et1 = (EditText)findViewById(R.id.etaddcomment);
        String Commentcontent = et1.getText().toString();


        try {
            CommonURL c=new CommonURL();
            String ip=c.getIP("Submit_Comment.php");
            URL objUrl = new URL(ip);
            HttpURLConnection objUrlConnection = (HttpURLConnection)objUrl.openConnection();
            objUrlConnection.setRequestMethod("POST");
            objUrlConnection.setRequestProperty("Content-type","Application/json");
            objUrlConnection.setRequestProperty("Accept","Application/json");

            objUrlConnection.setDoOutput(true);
            objUrlConnection.setDoInput(true);

            objUrlConnection.connect();

            JSONObject objJson = new JSONObject();

            objJson.put("content",Commentcontent);
            objJson.put("logid",logid);

            TimeAndDate t1 = new TimeAndDate();
            objJson.put("time",t1.getDateTime());

            objJson.put("compid",Compid+"");


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
                String id1 = jsonobject2.getString("id");
                String msg = jsonobject2.getString("Message");
                //Toast.makeText(this,lid + " " + msg, Toast.LENGTH_SHORT).show();
                if(id1.equals("1"))
                {
                    Toast.makeText(this,"Success", Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    finish();
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(this,"failed", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(this, "insertion failed", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e) {
            Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
