package com.honeste.honest_e;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.honeste.honest_e.commonclasses.CommonURL;
import com.honeste.honest_e.commonclasses.Delete_Account_User;
import com.honeste.honest_e.commonclasses.FilePermission;
import com.honeste.honest_e.commonclasses.common_functions;
import com.honeste.honest_e.commonclasses.profilecontentDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by abhis on 04-Apr-17.
 */

public class Profile extends Fragment implements View.OnClickListener{
    int Flag_profile = 0;
    String Uploadurl;
    String profileAge, profileName, profileArea;
    TextView tvname, tvarea, tvage;
    Button my_complaints,logout,delete_profile,settings;
    Bitmap profile;
    int rid;
    int PICK_IMAGE_REQUEST = 111;
    com.honeste.honest_e.CircularImageView circleprofile;
    ArrayList<commonComplain> commonComplains;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.profile, container, false);
         circleprofile = (com.honeste.honest_e.CircularImageView)v.findViewById(R.id.imageButton);

        final common_functions allfuncts = new common_functions();


        //Loading  prestored image
        SharedPreferences shared1=getContext().getSharedPreferences("Honest-E", MODE_PRIVATE);
        int id1 = Integer.parseInt(shared1.getString("lid","-1"));

        mRequestQueue = Volley.newRequestQueue(getContext());
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });

        rid = allfuncts.getRidfromLid(id1);

        Flag_profile = allfuncts.checkImgset(rid);
        if (Flag_profile == 0)
        {
            circleprofile.setDefaultImageResId(R.drawable.ic_profile_default);
        }
        else {
            String imgname = allfuncts.getprofileimagename(rid);
            CommonURL c1 = new CommonURL();
            String server_url = c1.getProfilePathdetailIP(imgname);
            circleprofile.setImageUrl(server_url, mImageLoader);
        }
       circleprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startimagepicker();
           }
        });


        //Initializing textviews
        tvname = (TextView)v.findViewById(R.id.profilename);
        tvage = (TextView)v.findViewById(R.id.profileage);
        tvarea = (TextView)v.findViewById(R.id.profileplace);
        //filling details
        HashMap<String,String> profiledetails = new HashMap<>();
        profilecontentDetails pfdet = new profilecontentDetails();
        profiledetails = pfdet.getprofileDetails(rid);

        profileName = profiledetails.get("name");
        profileArea = profiledetails.get("area");
        profileAge = profiledetails.get("age");
        tvname.setText(profileName);
        tvage.setText(profileAge);
        tvarea.setText(profileArea);

        //Declaring Buttons
        logout = (Button)v.findViewById(R.id.pro_logout);
        my_complaints = (Button)v.findViewById(R.id.pro_my_complaints);
        delete_profile = (Button)v.findViewById(R.id.pro_delete_account);
        settings = (Button)v.findViewById(R.id.pro_settings);
        logout.setOnClickListener(this);
        my_complaints.setOnClickListener(this);
        delete_profile.setOnClickListener(this);
        settings.setOnClickListener(this);

        return v;
    }
    private void startimagepicker(){
            FilePermission fp = new FilePermission();
            fp.verifyStoragePermissions(getActivity());
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);   }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                FilePermission fp = new FilePermission();
                fp.verifyStoragePermissions(getActivity());
                profile = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (profile!= null) {
                circleprofile.setImageBitmap(profile);
             }
        }
        if (profile!= null) {
             Uploadimage();
        }
    }
    private void Uploadimage(){
        CommonURL cmURL = new CommonURL();

        if (Flag_profile == 0)
        {
            Uploadurl =  cmURL.getIP("Profile_Upload_Image.php");
        }
        else
        {
            Toast.makeText(getContext(),"Updating Profile Picture", Toast.LENGTH_SHORT).show();
            Uploadurl = cmURL.getIP("Update_Profile_Image.php");
        }
        String temp =  UUID.randomUUID().toString();
        String temp2  = temp.substring(0, Math.min(temp.length(), 5));
        final String imgname = temp2 + "idof" + rid;

        final ProgressDialog loading = ProgressDialog.show(getContext(),"Uploading...","Please wait...",false,false);

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, Uploadurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonobj = new JSONObject(response);
                    String Response = jsonobj.getString("response");
                    Toast.makeText(getContext(),Response, Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                    Flag_profile=1;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name",imgname);
                params.put("rid",rid+"");
                params.put("image",imageToString(profile));
                return params;
            }
        };

        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    private String imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream bytearray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,60,bytearray);
        byte[] imgbytes = bytearray.toByteArray();
        return Base64.encodeToString(imgbytes,Base64.DEFAULT);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.pro_my_complaints:
                FragmentTransaction fragmentTransactionc=getActivity().getSupportFragmentManager().beginTransaction();
                profile_all_complaints profileAllComplaints = new profile_all_complaints();
                fragmentTransactionc.replace(R.id.maincontainerUI,profileAllComplaints).addToBackStack("profileComplaints");
                fragmentTransactionc.commit();
                break;


            case R.id.pro_settings:
                FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                editprofile editpro = new editprofile();
                fragmentTransaction.replace(R.id.maincontainerUI,editpro).addToBackStack("profilesettings").commit();
                break;


            case R.id.pro_logout:
                new AlertDialog.Builder(getContext())
                        .setTitle("Signing Off")
                        .setMessage("Are you sure you want to Log off our System?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences sharedlog = getContext().getSharedPreferences("Honest-E",MODE_PRIVATE);
                                sharedlog.edit().clear().commit();
                                Intent intent = new Intent(getContext(),MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                Toast.makeText(getContext(),"Signed out successfully", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(),"Thank you for staying some more time", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

                break;

            case R.id.pro_delete_account:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setTitle("Warning! You will be erased from our system").setMessage("Are you sure, you want to delete All your Account activities, Including comments, Complaints & likes ?");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Delete_Account_User d1 = new Delete_Account_User();
                                ArrayList<Integer> a1 = new ArrayList<>();
                                a1 = d1.getAllComplainID(rid);
                                d1.deleteallcomplaint(a1);
                                final int id6 = d1.deletelogreg(rid);
                                if (id6 == 1)
                                {
                                    SharedPreferences sharedlog1 = getContext().getSharedPreferences("Honest-E",MODE_PRIVATE);
                                    sharedlog1.edit().clear().commit();
                                    Intent intent1 = new Intent(getContext(),MainActivity.class);
                                    Toast.makeText(getContext(), "Account Deleted successfully", Toast.LENGTH_SHORT).show();
                                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent1);
                                }
                                else
                                {
                                    Toast.makeText(getContext(), "Failed to delete Account", Toast.LENGTH_SHORT).show();
                                }
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
                break;
        }
    }

}
