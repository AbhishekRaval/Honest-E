package com.honeste.honest_e;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.honeste.honest_e.commonclasses.CommonURL;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AMC_Login extends AppCompatActivity {
    private Toast toast;
    private long lastBackPressTime = 0;
    String lid,email,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amc__login);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        SharedPreferences shared = getSharedPreferences("Honest-E", MODE_PRIVATE);
        if( shared != null)
        {
            String lid = shared.getString("lid","-1");
            if(!lid.equals("-1"))
            {
                Intent intent = new Intent(this,InflaterHome.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

        }
    }

    public void OnNotAMC(View view){

        Intent AMCIntent = new Intent(this,MainActivity.class);
        startActivity(AMCIntent);

    }

    public void OnAMCLogin(View v)
    {

        // editext reference

        EditText etemail1 = (EditText)findViewById(R.id.main_AMC_email);
        EditText etpwd1 = (EditText)findViewById(R.id.main_AMC_pass);

        email = etemail1.getText().toString();
        pass = etpwd1.getText().toString();
        StrictMode.ThreadPolicy s=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(s);

        //Validation by means of incomplete items
        if (v.getId() == R.id.button4) {
            if (TextUtils.isEmpty(pass) || TextUtils.isEmpty(email)) {
                Toast.makeText(AMC_Login.this, "None of the Field Should be empty", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        //E-mail validation
        String emailPattern = "[a-zA-Z0-9._-]+@[amc]+\\.+[a-z]+";
        if (email.matches(emailPattern))
        {
            //Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Invalid email address, AMC Login must have @AMC", Toast.LENGTH_SHORT).show();
            return;
        }


        //connection
        try {

            CommonURL c=new CommonURL();
            String ip=c.getIP("User_Login.php");
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
                lid = jsonobject2.getString("Email");
                //   String msg = jsonobject2.getString("Message");

                if(!(lid.equals("-1")))
                {
                    SharedPreferences shared = getSharedPreferences("Honest-E", MODE_PRIVATE);
                    SharedPreferences.Editor edit = shared.edit();
                    edit.putString("lid", lid);
                    edit.commit();
                    Toast.makeText(this,lid,Toast.LENGTH_SHORT).show();
                    Intent loginIntent = new Intent(this,InflaterHome.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(loginIntent);
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
    public void OnSignUp(View view)
    {
        Intent signUpIntent = new Intent(this,RegistrationActivity.class);
        startActivity(signUpIntent);
    }
/*
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public v                                                                                                                    oid onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }*/

    public void OnAMCLoginClick1(View view){
        Intent AMCIntent = new Intent(this,AMC_Login.class);
        startActivity(AMCIntent);
    }

    @Override
    public void onBackPressed() {
        if (this.lastBackPressTime < System.currentTimeMillis() - 4000) {
            toast = Toast.makeText(this, "Press back again to close this app",Toast.LENGTH_LONG);
            toast.show();
            this.lastBackPressTime = System.currentTimeMillis();
        } else {
            if (toast != null) {
                toast.cancel();
            }
            super.onBackPressed();
        }
    }
}