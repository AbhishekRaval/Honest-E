package com.honeste.honest_e;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.honeste.honest_e.commonclasses.AreaArrayList;
import com.honeste.honest_e.commonclasses.EditProfileModel;
import com.honeste.honest_e.commonclasses.common_functions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by abhis on 06-Apr-17.
 */

public class editprofile extends Fragment implements AdapterView.OnItemSelectedListener {

    int rid_user;
    EditText etname, etdob, etpass, etemail, etphn;
    String sarea,sname,sdob,spass,semail,sphone,sgender,emailtemp;
    EditText pwd1, pwd2;
    Spinner Areaspinner;
    AreaArrayList arealist;
    ArrayList<String> areaAL;
    common_functions cf1;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit_profile, container, false);

        SharedPreferences shared1=getContext().getSharedPreferences("Honest-E", Context.MODE_PRIVATE);
        String id1 = shared1.getString("lid","-1");
        int id2 = Integer.parseInt(id1);
        cf1 = new common_functions();
        rid_user = cf1.getRidfromLid(id2);

        etname = (EditText)v.findViewById(R.id.et_name_profile);
        etdob = (EditText)v.findViewById(R.id.profiledob);
        etpass = (EditText)v.findViewById(R.id.et_pwd_profile);
        etemail = (EditText)v.findViewById(R.id.et_email_profile);
        etphn = (EditText)v.findViewById(R.id.et_phone_profile);

        pwd1 =(EditText)v.findViewById(R.id.et_newpassword_profile);
        pwd2 = (EditText)v.findViewById(R.id.et_confirmpwd_profile);

        Areaspinner = (Spinner)v.findViewById(R.id.areaspinerprofile);
        arealist = new AreaArrayList();
        areaAL = arealist.getAreaList();
        Collections.sort(areaAL, String.CASE_INSENSITIVE_ORDER);
        ArrayAdapter<String> arrayAdapterArea = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,areaAL);
        Areaspinner.setAdapter(arrayAdapterArea);
        Areaspinner.setOnItemSelectedListener(this);

        HashMap<String,String> fetchdata = new HashMap<>();
        EditProfileModel editProfileModel = new EditProfileModel();
        fetchdata = editProfileModel.getdetails(rid_user);

        sname =fetchdata.get("name");
        sarea=fetchdata.get("area");
        sdob = fetchdata.get("dob");
        sgender = fetchdata.get("gender");
        semail = fetchdata.get("email");
        emailtemp = semail;
        spass = fetchdata.get("pass");
        sphone = fetchdata.get("phone");

        etname.setText(sname);
        etemail.setText(semail);
        etpass.setText(spass);
        etdob.setText(sdob);
        etphn.setText(sphone);
        Areaspinner.setSelection(areaAL.indexOf(sarea));

       RadioGroup rg1 = (RadioGroup)v.findViewById(R.id.gender_profile);
        RadioButton r1 = (RadioButton)v.findViewById(R.id.rbmale_profile);
        RadioButton r2 = (RadioButton)v.findViewById(R.id.rbfemale_profile);
        RadioButton r3 = (RadioButton)v.findViewById(R.id.rbother_profile);


        if (r1.getText().equals(sgender))
        {
            r1.setChecked(true);
        }
        else if (r2.getText().equals(sgender))
        {
            r2.setChecked(true);
        }
        else if (r3.getText().equals(sgender))
        {
            r3.setChecked(true);
        }

        Button save = (Button)v.findViewById(R.id.btnsaveprofile);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEditProfile(view);
               Toast.makeText(view.getContext(), "Data Updated", Toast.LENGTH_SHORT).show();
                /*if (view.getId() == R.id.btnsaveprofile) {
                    if (TextUtils.isEmpty(sname) || TextUtils.isEmpty(spass) || TextUtils.isEmpty(semail) || TextUtils.isEmpty(sphone) || TextUtils.isEmpty(sdob) || TextUtils.isEmpty(sarea)) {
                        Toast.makeText(view.getContext(), "None of the Field Should be empty", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                else {
                    Toast.makeText(view.getContext(), "Data Updated", Toast.LENGTH_SHORT).show();
                }*/
            }
        });


        return v;
    }

    public void onEditProfile(View view){
        String pass1 = pwd1.getText().toString();
        String pass2 = pwd2.getText().toString();

        if(pass1!=pass2 && !pass1.isEmpty())
        {
            Toast.makeText(getContext(),"Your New Password and Confirm Password doesn't matches", Toast.LENGTH_SHORT).show();
            return;
        }

        if ( (spass == pass1 && !pass1.isEmpty()) || (spass == pass2 && !pass2.isEmpty()))
        {
            Toast.makeText(getContext(),"Your new password and old password can't be same", Toast.LENGTH_SHORT).show();
            return;
        }

        String emailPattern = "[a-zA-Z0-9._-]+@[amc]+\\.+[a-z]+";
        if (semail.matches(emailPattern))
        {
            int mailflag = cf1.emailExistsCheck(semail);
            if (!emailtemp.equals(semail) && mailflag == 1)
            {
                Toast.makeText(getContext(),"Email already exists can't use that", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        else
        {
            Toast.makeText(getContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (view.getId() == R.id.btnsaveprofile) {
            if (TextUtils.isEmpty(sname) || TextUtils.isEmpty(spass) || TextUtils.isEmpty(semail) || TextUtils.isEmpty(sphone) || TextUtils.isEmpty(sdob) || TextUtils.isEmpty(sarea)) {
                Toast.makeText(getContext(), "None of the Field Should be empty", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Toast.makeText(getContext(), "Data Updated", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
