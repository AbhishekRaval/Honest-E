package com.honeste.honest_e;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.honeste.honest_e.HomeUI;
import com.honeste.honest_e.R;
import com.honeste.honest_e.commonclasses.AreaArrayList;
import com.honeste.honest_e.commonclasses.CategoryList;
import com.honeste.honest_e.commonclasses.CommonURL;
import com.honeste.honest_e.commonclasses.SubCategoryList;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class Edit_Complaint extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner Catspinner,Areaspinner,Subcatspinner;
    SubCategoryList subCategoryList;
    AreaArrayList arealist;
    CategoryList catlist;
    String id;
    int Compid;
    String content1, address1, arset1, catset1,subcatset1;
    String area,category,subCat;
    ArrayList<String> catgory,areaAL,subcatAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__complaint);
        Intent intent = getIntent();
        String data = intent.getStringExtra("compid");
        Compid = Integer.parseInt(data);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        catgory = new ArrayList<>();
        catlist = new CategoryList();
        catgory = catlist.getCatgory();
        Collections.sort(catgory, String.CASE_INSENSITIVE_ORDER);
        Catspinner = (Spinner)findViewById(R.id.EDcat_spinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,catgory);
        Catspinner.setAdapter(arrayAdapter);
        Catspinner.setOnItemSelectedListener(this);

        subCategoryList = new SubCategoryList();
        subcatAL = new ArrayList<>();
        Subcatspinner = (Spinner)findViewById(R.id.EDSubcat_spinner);
        Subcatspinner.setOnItemSelectedListener(this);

        Areaspinner = (Spinner)findViewById(R.id.EDcomp_area);
        arealist = new AreaArrayList();
        areaAL = arealist.getAreaList();
        Collections.sort(areaAL, String.CASE_INSENSITIVE_ORDER);
        ArrayAdapter<String> arrayAdapterArea = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,areaAL);
        Areaspinner.setAdapter(arrayAdapterArea);
        Areaspinner.setOnItemSelectedListener(this);


        StrictMode.ThreadPolicy s=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(s);

        try {

            CommonURL c=new CommonURL();
            String ip=c.getIP("editComplaint_data.php");
            URL objUrl = new URL(ip);
            HttpURLConnection objUrlConnection = (HttpURLConnection)objUrl.openConnection();
            objUrlConnection.setRequestMethod("POST");
            objUrlConnection.setRequestProperty("Content-type","Application/json");
            objUrlConnection.setRequestProperty("Accept","Application/json");

            objUrlConnection.setDoOutput(true);
            objUrlConnection.setDoInput(true);

            objUrlConnection.connect();

            JSONObject objJson = new JSONObject();
            objJson.put("compid",Compid);

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
                //lid = jsonobject2.getString("Email");
                content1 = jsonobject2.getString("description");
                address1 = jsonobject2.getString("address");
                arset1 = jsonobject2.getString("area");
                catset1 = jsonobject2.getString("category");
                subcatset1 = jsonobject2.getString("subcat");
                //   String msg = jsonobject2.getString("Message");
            }
            else
            {
                Toast.makeText(this, "Can't retrieve data", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e) {
            Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        EditText etcont = (EditText)findViewById(R.id.EDetcomplaintdescription);
        EditText etadd = (EditText)findViewById(R.id.EDetcomplaintlocation1);

        etcont.setText(content1);
        etadd.setText(address1);
        Areaspinner.setSelection(areaAL.indexOf(arset1));
        Catspinner.setSelection(catgory.indexOf(catset1));
        Subcatspinner.setSelection(subcatAL.indexOf(subcatset1));
    }

    public  void onUpdatebtn(View v)
    {
        StrictMode.ThreadPolicy s=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(s);

        EditText complaintdes = (EditText)findViewById(R.id.EDetcomplaintdescription);
        EditText location1 = (EditText)findViewById(R.id.EDetcomplaintlocation1);

        String ComplaintDes = complaintdes.getText().toString();
        String Location1 = location1.getText().toString();
        String Location = Location1;
        String stats = "0" ;
        //String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        try {
            CommonURL c=new CommonURL();
            String ip=c.getIP("EditComplaint.php");
            URL objUrl = new URL(ip);
            HttpURLConnection objUrlConnection = (HttpURLConnection)objUrl.openConnection();
            objUrlConnection.setRequestMethod("POST");
            objUrlConnection.setRequestProperty("Content-type","Application/json");
            objUrlConnection.setRequestProperty("Accept","Application/json");

            objUrlConnection.setDoOutput(true);
            objUrlConnection.setDoInput(true);

            objUrlConnection.connect();

            JSONObject objJson = new JSONObject();
            objJson.put("content",ComplaintDes);
            objJson.put("add",Location);
            objJson.put("category",category);
            objJson.put("stats",stats);
            objJson.put("Area",area);
            objJson.put("Subcat",subCat);
            objJson.put("compid",Compid);

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
                    Toast.makeText(this,"Complaint Edited Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this,InflaterHome.class);
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


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


        TextView spinnerText = (TextView) Catspinner.getChildAt(0);
        TextView spinnerText1 = (TextView) Areaspinner.getChildAt(0);
        TextView spinnerText2 = (TextView) Subcatspinner.getChildAt(0);

        switch(adapterView.getId()){
            case R.id.EDcat_spinner :
                spinnerText.setTextColor(Color.BLACK);
                String  s = adapterView.getItemAtPosition(i).toString();
                category = s;
                subcatAL = subCategoryList.getSubcat(category);
                ArrayAdapter<String> arrayAdapterSubcat = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,subcatAL);
                arrayAdapterSubcat.notifyDataSetChanged();
                Subcatspinner.setAdapter(arrayAdapterSubcat);
                break;

            case R.id.EDcomp_area :
                spinnerText1.setTextColor(Color.BLACK);
                String abx  = adapterView.getItemAtPosition(i).toString();
                area = abx;
                break;

            case R.id.EDSubcat_spinner:
                spinnerText2.setTextColor(Color.BLACK);
                String s2 = adapterView.getItemAtPosition(i).toString();
                subCat = s2;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
