package com.honeste.honest_e;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.honeste.honest_e.commonclasses.CommonURL;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Edit_Comment extends AppCompatActivity {
    int commentid,complaintid;
    EditText et1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__comment);

        Intent intent = getIntent();
        String data = intent.getStringExtra("commentid");
        String data2 = intent.getStringExtra("content");
        String data3 = intent.getStringExtra("complaintid");
        commentid = Integer.parseInt(data);
        complaintid = Integer.parseInt(data3);

        et1 = (EditText)findViewById(R.id.EDetaddcomment);
        et1.setText(data2);
    }

    public void onUpdateComment(View view)
    {
        StrictMode.ThreadPolicy s=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(s);

        String Cmtcont = et1.getText().toString();

        try {
            CommonURL c=new CommonURL();
            String ip=c.getIP("EditComment.php");
            URL objUrl = new URL(ip);
            HttpURLConnection objUrlConnection = (HttpURLConnection)objUrl.openConnection();
            objUrlConnection.setRequestMethod("POST");
            objUrlConnection.setRequestProperty("Content-type","Application/json");
            objUrlConnection.setRequestProperty("Accept","Application/json");

            objUrlConnection.setDoOutput(true);
            objUrlConnection.setDoInput(true);

            objUrlConnection.connect();

            JSONObject objJson = new JSONObject();
            objJson.put("content",Cmtcont);
            objJson.put("cmtid",commentid);

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
                String lid1 = jsonobject2.getString("id");
                String msg = jsonobject2.getString("Message");
                //Toast.makeText(this,lid + " " + msg, Toast.LENGTH_SHORT).show();
                if(lid1.equals("1"))
                {
                    Toast.makeText(this,"Comment Edited Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this,comment_list.class);
                    String abc = String.valueOf(complaintid);
                    intent.putExtra("id",abc);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    //recreate();
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
