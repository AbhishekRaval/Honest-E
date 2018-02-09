package com.honeste.honest_e;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.honeste.honest_e.commonclasses.FilePermission;

public class InflaterHome extends AppCompatActivity {

    private BottomNavigationView mBottomNav;
    private int mSelectedItem;private static
    final String SELECTED_ITEM = "arg_selected_item";
    private Toast toast;
    private long lastBackPressTime = 0;
    int Flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FilePermission fp = new FilePermission();
        fp.verifyStoragePermissions(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inflater_home);
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        home home=new home();
        fragmentTransaction.replace(R.id.maincontainerUI,home);
        fragmentTransaction.commit();

        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id=item.getItemId();
                if(id==R.id.navHome)
                {   Flag=1;
                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    home home=new home();
                    fragmentTransaction.replace(R.id.maincontainerUI,home);
                    android.app.FragmentManager fm = getFragmentManager(); // or 'getSupportFragmentManager();'
                    int count = fm.getBackStackEntryCount();
                    for(int i = 0; i < count; ++i) {
                        fm.popBackStack();
                    }
                    fragmentTransaction.commit();
                }
                else  if(id==R.id.navSearch)
                {
                    Flag=0;
                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    Search search = new Search();
                    fragmentTransaction.replace(R.id.maincontainerUI,search).addToBackStack("Search").commit();
                }
                else if (id == R.id.navProfile)
                {
                    Flag=0;
                     FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    Profile profile = new Profile();
                    fragmentTransaction.replace(R.id.maincontainerUI,profile).addToBackStack("profile");
                    android.app.FragmentManager fm = getFragmentManager(); // or 'getSupportFragmentManager();'
                    int count = fm.getBackStackEntryCount();
                    for(int i = 0; i < count; ++i) {
                        fm.popBackStack();
                    }
                    fragmentTransaction.commit();

                }


                return true;
            }
        });

    }

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

    @Override
    public void onBackPressed() {
        if (this.lastBackPressTime < System.currentTimeMillis() - 4000 && Flag == 1) {
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
