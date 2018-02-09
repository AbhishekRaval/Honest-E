package com.honeste.honest_e;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.honeste.honest_e.commonclasses.AreaArrayList;
import com.honeste.honest_e.commonclasses.CommonURL;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;


public class RegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //Set current date in calender
        final Calendar cal =  Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);
        showDialogOnETClick();
        //set current date closed. And registering on click activity.

        //Spinner for area
        s1 = (Spinner)findViewById(R.id.SpArea);
        a1 = new AreaArrayList();
        areaAL = a1.getAreaList();
        Collections.sort(areaAL, String.CASE_INSENSITIVE_ORDER);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,areaAL);
        s1.setAdapter(arrayAdapter);
        s1.setOnItemSelectedListener(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    //Spinner
    Spinner s1;
    AreaArrayList a1;
    ArrayList<String> areaAL;
    //

    //

    //Declaring String variables
    String name, pass, email, phone, area;

    //Datepicker in edittext starts
    EditText dateET ;
    int year_x,month_x,day_x;
    static final int DIALOGUE_ID = 0;
    String dob;

    public void showDialogOnETClick(){
        dateET = (EditText)findViewById(R.id.etdate);
        dateET.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog(DIALOGUE_ID);
                    }
                }

        );
    }
    @Override
    protected Dialog onCreateDialog(int id){
        if (id == DIALOGUE_ID)
            return new DatePickerDialog(this,dpickerlistener,year_x,month_x,day_x);
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerlistener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthofyear, int dayofmonth) {
            year_x=year;
            month_x=monthofyear;
            day_x=dayofmonth;
            dob = year_x+"-"+month_x+"-"+day_x;
            dateET.setText(dob + "");

        }
    };
    //datepicker in edittext ends. Dates are stored in String "DOB"

    public void regbtnclick(View view)
    {
        // editext reference
        EditText etname1 = (EditText)findViewById(R.id.etname);
        EditText etpwd1 = (EditText)findViewById(R.id.etpwd);
        EditText etemail1 = (EditText)findViewById(R.id.etemail);
        EditText etphone1 = (EditText)findViewById(R.id.etphone);

        //fetching values from edittext into string
        name = etname1.getText().toString();
        pass = etpwd1.getText().toString();
        email = etemail1.getText().toString();
        phone = etphone1.getText().toString();

        //obtaining value of selected radiobutton
        RadioGroup rg1 = (RadioGroup)findViewById(R.id.gender);
        int id = rg1.getCheckedRadioButtonId();
        if (id == -1)
        {
            Toast.makeText(RegistrationActivity.this, "Select one gender", Toast.LENGTH_SHORT).show();
            return;
        }
        RadioButton rb1 = (RadioButton)findViewById(id);
        String gender = rb1.getText().toString();

        //Validation by means of incomplete items
        if (view.getId() == R.id.regbtn) {
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(dob) || TextUtils.isEmpty(area)) {
                Toast.makeText(RegistrationActivity.this, "None of the Field Should be empty", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        //E-mail validation
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.matches(emailPattern))
        {
            //Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        //connection
        try {
            CommonURL c=new CommonURL();
            String ip=c.getIP("User_Registration.php");
            URL objUrl = new URL(ip);
            HttpURLConnection objUrlConnection = (HttpURLConnection)objUrl.openConnection();
            objUrlConnection.setRequestMethod("POST");
            objUrlConnection.setRequestProperty("Content-type","Application/json");
            objUrlConnection.setRequestProperty("Accept","Application/json");

            objUrlConnection.setDoOutput(true);
            objUrlConnection.setDoInput(true);

            objUrlConnection.connect();

            JSONObject objJson = new JSONObject();
            objJson.put("email",email);
            objJson.put("pwd",pass);
            objJson.put("name",name);
            objJson.put("gender",gender);
            objJson.put("dob",dob);
            objJson.put("area",area);
            objJson.put("phone",phone);

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
                String lid = jsonobject2.getString("Email");
                String msg = jsonobject2.getString("Message");
                Toast.makeText(this,lid + " " + msg, Toast.LENGTH_SHORT).show();
                if(!(lid.equals("-1")))
                {
                    SharedPreferences shared = getSharedPreferences("Honest-E", MODE_PRIVATE);
                    SharedPreferences.Editor edit = shared.edit();
                    edit.putString("lid", lid);
                    edit.commit();
                    Intent intent = new Intent(this,InflaterHome.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(this,"Invalid Data", Toast.LENGTH_SHORT).show();
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

    public void backtosingin(View view){
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String  s = adapterView.getItemAtPosition(i).toString();
        area = s;
        TextView spinnerText = (TextView) s1.getChildAt(0);
        spinnerText.setTextColor(Color.BLACK);
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
