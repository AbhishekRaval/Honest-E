//with async

package com.honeste.honest_e;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.honeste.honest_e.commonclasses.AreaArrayList;
import com.honeste.honest_e.commonclasses.CategoryList;
import com.honeste.honest_e.commonclasses.CommonURL;
import com.honeste.honest_e.commonclasses.FilePermission;
import com.honeste.honest_e.commonclasses.SubCategoryList;
import com.honeste.honest_e.commonclasses.TimeAndDate;
import com.honeste.honest_e.commonclasses.common_functions;
import com.honeste.honest_e.commonclasses.realPathfromURI;
import com.nguyenhoanglam.imagepicker.activity.ImagePicker;
import com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity;
import com.nguyenhoanglam.imagepicker.model.Image;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NewComplaint extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener{

    //TextView textView;
    Spinner SpinCat,s1,SpinSubCat;
    AreaArrayList a1;
    String id;
    String category,area,subcat;
    ArrayList<String> catgory,areaAL,subcatAL;
    SubCategoryList subCategoryList;
    Button browse;
    String complainid;

    //image
    private int REQUEST_CODE_PICKER = 2000;
    int PICK_IMAGE_REQUEST = 111;
    private ArrayList<Image> images = new ArrayList<>();
    private TextView textViewpath;
    private final int IMG_REQUEST =1;
    private Bitmap bitmap ;
    ImageView imageView;
    Context context;
    private String Uploadurl;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_complaint);
        this.context = context;

        SharedPreferences s=getSharedPreferences("Honest-E",MODE_PRIVATE);
        id = s.getString("lid","-1");

        CommonURL cmURL = new CommonURL();
        Uploadurl =  cmURL.getIP("Complain_Upload_Image.php");

        CategoryList ctglist = new CategoryList();
        catgory = new ArrayList<>();
        catgory = ctglist.getCatgory();
        Collections.sort(catgory, String.CASE_INSENSITIVE_ORDER);
        SpinCat = (Spinner)findViewById(R.id.cat_spinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,catgory);
        SpinCat.setAdapter(arrayAdapter);
        SpinCat.setOnItemSelectedListener(this);

        subCategoryList = new SubCategoryList();
        subcatAL = new ArrayList<>();
        SpinSubCat = (Spinner)findViewById(R.id.Subcat_spinner);
        SpinSubCat.setOnItemSelectedListener(this);

        s1 = (Spinner)findViewById(R.id.comp_area);
        a1 = new AreaArrayList();
        areaAL = a1.getAreaList();
        Collections.sort(areaAL, String.CASE_INSENSITIVE_ORDER);
        ArrayAdapter<String> arrayAdapterArea = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,areaAL);
        s1.setAdapter(arrayAdapterArea);
        s1.setOnItemSelectedListener(this);

        browse = (Button)findViewById(R.id.browse_complaint);
        browse.setOnClickListener(this);
        textViewpath = (TextView)findViewById(R.id.pathTV);
        imageView = (ImageView)findViewById(R.id.image_view);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }



    public  void OnPost(View v)
    {
        StrictMode.ThreadPolicy s=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(s);

        EditText complaintdes = (EditText)findViewById(R.id.etcomplaintdescription);
        EditText location1 = (EditText)findViewById(R.id.etcomplaintlocation1);
        EditText location2 = (EditText)findViewById(R.id.etcomplaintlocation2);

        String ComplaintDes = complaintdes.getText().toString();
        String Location1 = location1.getText().toString();
        String Location2 = location2.getText().toString();
        String Location = Location1 + " " + Location2;
        String stats = "0" ;
        //String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        //validation by means of incomplete item
        if (v.getId() == R.id.postcomplaint) {
            if (TextUtils.isEmpty(ComplaintDes) || TextUtils.isEmpty(Location1) ) {
                Toast.makeText(NewComplaint.this, "None of the Field Should be empty", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        try {
            CommonURL c=new CommonURL();
            String ip=c.getIP("Submit_Complaint.php");
            URL objUrl = new URL(ip);
            HttpURLConnection objUrlConnection = (HttpURLConnection)objUrl.openConnection();
            objUrlConnection.setRequestMethod("POST");
            objUrlConnection.setRequestProperty("Content-type","Application/json");
            objUrlConnection.setRequestProperty("Accept","Application/json");

            objUrlConnection.setDoOutput(true);
            objUrlConnection.setDoInput(true);

            objUrlConnection.connect();

            JSONObject objJson = new JSONObject();
            objJson.put("subcat",subcat);
            objJson.put("content",ComplaintDes);
            objJson.put("add",Location);
            objJson.put("lid",id);
            objJson.put("category",category);
            objJson.put("stats",stats);
            TimeAndDate t1 = new TimeAndDate();
            objJson.put("time",t1.getDateTime());
            objJson.put("Area",area);

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
                complainid = jsonobject2.getString("complainid");
                Toast.makeText(this,complainid + " " + msg, Toast.LENGTH_SHORT).show();
                if(lid1.equals("1"))
                {
                    //Toast.makeText(this,"Success", Toast.LENGTH_SHORT).show();
                    Uploadimage();

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

        TextView spinnerText = (TextView) SpinCat.getChildAt(0);
        TextView spinnerText1 = (TextView) s1.getChildAt(0);
        TextView spinnerText2 = (TextView) SpinSubCat.getChildAt(0);

        switch(adapterView.getId()){
            case R.id.cat_spinner :
                spinnerText.setTextColor(Color.BLACK);
                String  s = adapterView.getItemAtPosition(i).toString();
                category = s;
                subcatAL = subCategoryList.getSubcat(category);
                ArrayAdapter<String> arrayAdapterSubcat = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,subcatAL);
                arrayAdapterSubcat.notifyDataSetChanged();
                SpinSubCat.setAdapter(arrayAdapterSubcat);

                break;
            case R.id.comp_area :
                spinnerText1.setTextColor(Color.BLACK);
                String abx  = adapterView.getItemAtPosition(i).toString();
                area = abx;
                break;
            case R.id.Subcat_spinner:
                spinnerText2.setTextColor(Color.BLACK);
                String s2 = adapterView.getItemAtPosition(i).toString();
                subcat = s2;
        }    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
    if (view.getId() == R.id.browse_complaint)
    {
        //ImagePicker.create(this);
        //images.clear();
        //textViewpath.setText("");
        startimagepicker();
    }
    }

    private void startimagepicker(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);   }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            realPathfromURI realpath = new realPathfromURI();
            String path = realpath.getPathFromURI(this,filePath);
            textViewpath.setText(path);
            try {
                //Getting the Bitmap from Gallery
                FilePermission fp = new FilePermission();
                fp.verifyStoragePermissions(this);

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //imageView.setVisibility(View.VISIBLE);
        //imageView.setImageBitmap(bitmap);
    }

    private void Uploadimage(){
        String temp =  UUID.randomUUID().toString();
        String temp2  = temp.substring(0, Math.min(temp.length(), 5));
        final String imgname = temp2 + "idof" +complainid;

        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, Uploadurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonobj = new JSONObject(response);
                    String Response = jsonobj.getString("response");
                    Toast.makeText(NewComplaint.this,Response, Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                    Intent intent = new Intent(NewComplaint.this,InflaterHome.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Intent intent = new Intent(NewComplaint.this,InflaterHome.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name",imgname);
                params.put("compid",complainid);
                params.put("image",imageToString(bitmap));
                return params;
            }
        };
        MySingleton.getInstance(NewComplaint.this).addToRequestQueue(stringRequest);
    }

    private String imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream bytearray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,60,bytearray);
        byte[] imgbytes = bytearray.toByteArray();
        return Base64.encodeToString(imgbytes,Base64.DEFAULT);
    }
}



