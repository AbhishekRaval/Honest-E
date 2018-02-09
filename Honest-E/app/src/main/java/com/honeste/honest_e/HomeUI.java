package com.honeste.honest_e;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
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

public class HomeUI extends AppCompatActivity {
    String JSON_String;
    FloatingActionButton fab;
    ListView listView;
    int rid_user;

    ArrayList<commonComplain> Complains;
    complainAdapter complainAdapter;

    String lid="";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_ui);
        fab = (FloatingActionButton)findViewById(R.id.fab);

        SharedPreferences shared1=getSharedPreferences("Honest-E",MODE_PRIVATE);
         String id1 = shared1.getString("lid","-1");
        int id2 = Integer.parseInt(id1);
        common_functions cf1 = new common_functions();
         rid_user = cf1.getRidfromLid(id2);


        try {
            listView = (ListView) findViewById(R.id.ViewComplain);

            Complains = new ArrayList<commonComplain>();
            complainAdapter = new complainAdapter(this, Complains);

            StrictMode.ThreadPolicy s = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(s);
            try {

                CommonURL c = new CommonURL();
                String ip = c.getIP("List_All_Complaints.php");
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
                        commonComplain commonComplains = new commonComplain();
                        commonComplains.setArea(jsonObject.getString("Area"));
                        commonComplains.setCategory(jsonObject.getString("Cat"));
                        commonComplains.setName(jsonObject.getString("Name"));
                        commonComplains.setDesc(jsonObject.getString("Cont"));
                        commonComplains.setHours(jsonObject.getString("Time"));
                        commonComplains.setCompid(Integer.parseInt(jsonObject.getString("ID")));
                        commonComplains.setComplaint_rid(Integer.parseInt(jsonObject.getString("Complaint_rid")));
                        commonComplains.setSubcategory(jsonObject.getString("subcat"));
                        commonComplains.setLogin_rid(rid_user);
                        String imgname = cf1.getImgNamefromComplainID(Integer.parseInt(jsonObject.getString("ID")));
                        commonComplains.setCompimgpath(imgname);
                        Complains.add(commonComplains);
                    }
                }

            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            listView.setAdapter(complainAdapter);
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void onFloatBtn(View view)
    {
        Intent FABIntent = new Intent(this,NewComplaint.class);
        startActivity(FABIntent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.Logout:
            SharedPreferences sharedlog =getSharedPreferences("Honest-E",MODE_PRIVATE);
            sharedlog.edit().clear().commit();
            Intent intent = new Intent(this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
             return(true);

    }
        return(super.onOptionsItemSelected(item));
    }

    /*public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        HomeUI.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }*/
}
