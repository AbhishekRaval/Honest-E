package com.honeste.honest_e;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.honeste.honest_e.commonclasses.AreaArrayList;
import com.honeste.honest_e.commonclasses.CategoryList;
import com.honeste.honest_e.commonclasses.CommonURL;
import com.honeste.honest_e.commonclasses.SearchListview;
import com.honeste.honest_e.commonclasses.SubCategoryList;
import com.honeste.honest_e.commonclasses.TimeAndDate;
import com.honeste.honest_e.commonclasses.common_functions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import static android.R.attr.category;

/**
 * Created by abhis on 02-Apr-17.
 */

public class Search extends Fragment implements AdapterView.OnItemSelectedListener {

    Spinner searchspinner, spinnerbycat,spinnerbyarea;
    ArrayList<String> filter,filterbycat,filterbyarea;
    CategoryList catsearch;
    AreaArrayList subbyarea;
    String cat,area,subcat;
    LinearLayout lcategory, larea;
    ListView listview;
    int rid_user;
    ArrayList<commonComplain> Complains;
    complainAdapter complainAdapter1;
    SearchListview listcontents;
    int searchid = 0;
    String JSON_String;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.searchui, container, false);

        lcategory = (LinearLayout)v.findViewById(R.id.layout_searchbycategory);
        larea = (LinearLayout)v.findViewById(R.id.layout_searchbyarea);
        searchspinner = (Spinner)v.findViewById(R.id.search_spinner);
        filter = new ArrayList<>();
        filter.add("By Category");
        filter.add("By Area");
        ArrayAdapter<String> arrayAdapterfilter = new ArrayAdapter<String>(v.getContext(),android.R.layout.simple_spinner_dropdown_item,filter);
        searchspinner.setAdapter(arrayAdapterfilter);
        searchspinner.setOnItemSelectedListener(this);

        // set spinner


        catsearch = new CategoryList();
        filterbycat = catsearch.getCatgory();
        Collections.sort(filterbycat,String.CASE_INSENSITIVE_ORDER);
        spinnerbycat = (Spinner)v.findViewById(R.id.search_spinner_bycategory);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(v.getContext(),android.R.layout.simple_spinner_dropdown_item,filterbycat);
        spinnerbycat.setAdapter(arrayAdapter);
        spinnerbycat.setOnItemSelectedListener(this);


        subbyarea = new AreaArrayList();
        filterbyarea = new ArrayList<>();
        filterbyarea = subbyarea.getAreaList();
        Collections.sort(filterbyarea,String.CASE_INSENSITIVE_ORDER);
        spinnerbyarea = (Spinner)v.findViewById(R.id.search_spinner_byarea);
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(v.getContext(),android.R.layout.simple_spinner_dropdown_item,filterbyarea);
        spinnerbyarea.setAdapter(arrayAdapter3);
        spinnerbyarea.setOnItemSelectedListener(this);

        listcontents = new SearchListview();
        listview= (ListView)v.findViewById(R.id.SearchListview);
        SharedPreferences shared1 = getContext().getSharedPreferences("Honest-E", Context.MODE_PRIVATE);
        String id1 = shared1.getString("lid","-1");
        int id2 = Integer.parseInt(id1);

        common_functions cf1 = new common_functions();
        rid_user = cf1.getRidfromLid(id2);
        Complains = new ArrayList<commonComplain>();
        complainAdapter1 = new complainAdapter(getContext(), Complains);

        return v;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

        TextView spinnerText = (TextView)spinnerbycat.getChildAt(0);
        TextView spinnerText1 = (TextView)spinnerbyarea.getChildAt(0);
        TextView spinnertext2 = (TextView)searchspinner.getChildAt(0);
        switch(adapterView.getId()){
            case R.id.search_spinner:
                spinnertext2.setTextColor(Color.BLACK);
                String  searchresult = adapterView.getItemAtPosition(position).toString();
                if (searchresult.equals("By Category"))
                {
                   lcategory.setVisibility(view.VISIBLE);
                    larea.setVisibility(view.GONE);
                    searchid = 1;

                }
                else if(searchresult.equals("By Area"))
                {
                    larea.setVisibility(view.VISIBLE);
                    lcategory.setVisibility(view.GONE);
                    searchid = 2;
                }

                break;
            case R.id.search_spinner_bycategory:
                Complains.clear();
                listview.setVisibility(view.GONE);
                spinnerText.setTextColor(Color.BLACK);
                String  s = adapterView.getItemAtPosition(position).toString();
                cat = s;
                listview.setVisibility(view.VISIBLE);
                common_functions cf1 = new common_functions();
                StrictMode.ThreadPolicy spolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(spolicy);
                try {

                    CommonURL c = new CommonURL();
                    String ip = c.getIP("Search_Complaints_by_id.php");
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
                    objJson.put("searchid",searchid);
                    objJson.put("dbid",cat);
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
                            final commonComplain commonComplains = new commonComplain();
                            commonComplains.setArea(jsonObject.getString("Area"));
                            commonComplains.setCategory(jsonObject.getString("Cat"));
                            commonComplains.setName(jsonObject.getString("Name"));
                            commonComplains.setDesc(jsonObject.getString("Cont"));
                            commonComplains.setHours(jsonObject.getString("Time"));
                            int c1id = Integer.parseInt(jsonObject.getString("ID"));
                            commonComplains.setCompid(c1id);
                            commonComplains.setComplaint_rid(Integer.parseInt(jsonObject.getString("Complaint_rid")));
                            commonComplains.setSubcategory(jsonObject.getString("subcat"));
                            commonComplains.setLogin_rid(rid_user);
                            String imgname = cf1.getImgNamefromComplainID(c1id);
                            commonComplains.setCompimgpath(imgname);
                            Complains.add(commonComplains);
                        }
                    }

                } catch (Exception e) {
                    //Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }


                listview.setAdapter(complainAdapter1);

                break;

            case R.id.search_spinner_byarea :
                Complains.clear();
                listview.setVisibility(view.GONE);
                spinnerText1.setTextColor(Color.BLACK);
                String s1  = adapterView.getItemAtPosition(position).toString();
                area = s1;
                listview.setVisibility(view.VISIBLE);
                common_functions cf2 = new common_functions();
                StrictMode.ThreadPolicy spolicy2 = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(spolicy2);
                try {

                    CommonURL c = new CommonURL();
                    String ip = c.getIP("Search_Complaints_by_id.php");
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
                    objJson.put("searchid",searchid);
                    objJson.put("dbid",area);
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
                            final commonComplain commonComplains = new commonComplain();
                            commonComplains.setArea(jsonObject.getString("Area"));
                            commonComplains.setCategory(jsonObject.getString("Cat"));
                            commonComplains.setName(jsonObject.getString("Name"));
                            commonComplains.setDesc(jsonObject.getString("Cont"));
                            commonComplains.setHours(jsonObject.getString("Time"));
                            int c1id = Integer.parseInt(jsonObject.getString("ID"));
                            commonComplains.setCompid(c1id);
                            commonComplains.setComplaint_rid(Integer.parseInt(jsonObject.getString("Complaint_rid")));
                            commonComplains.setSubcategory(jsonObject.getString("subcat"));
                            commonComplains.setLogin_rid(rid_user);
                            String imgname = cf2.getImgNamefromComplainID(c1id);
                            commonComplains.setCompimgpath(imgname);
                            Complains.add(commonComplains);
                        }
                    }

                } catch (Exception e) {
                    //Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                listview.setAdapter(complainAdapter1);
                break;
        }    }

    @Override
    public void onNothingSelected (AdapterView < ? > adapterView){

    }
}