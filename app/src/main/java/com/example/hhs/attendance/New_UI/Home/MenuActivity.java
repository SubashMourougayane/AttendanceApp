package com.example.hhs.attendance.New_UI.Home;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hhs.attendance.Old_UI.Attendance;
import com.example.hhs.attendance.Old_UI.Classes;
import com.example.hhs.attendance.Old_UI.Logout;
import com.example.hhs.attendance.Old_UI.PDF;
import com.example.hhs.attendance.Old_UI.PastAttendance;
import com.example.hhs.attendance.Old_UI.Subject;
import com.example.hhs.attendance.R;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;


public class MenuActivity extends FragmentActivity implements View.OnClickListener{

    private ResideMenu resideMenu;
    private MenuActivity mContext;
    private ResideMenuItem classes;
    private ResideMenuItem attendance;
    private ResideMenuItem subjects;
    private ResideMenuItem pastatt;
    private ResideMenuItem genpdf;
    private ResideMenuItem logout;
    private ResideMenuItem home;
    public TextView text;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mContext = this;
        setUpMenu();
        if( savedInstanceState == null )
            changeFragment(new HomeFragment());

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();
        String gender = pref.getString("gender","");
        String name = pref.getString("uname","");

        text = (TextView) findViewById(R.id.text);

        if(isNetworkAvailable()){
            // do network operation here
        }else{
            Toast.makeText(this, "No Available Network. Please try again later", Toast.LENGTH_LONG).show();
            return;
        }

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {
       System.out.println("Clear app to exit");
    }

    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(this);
        //resideMenu.setUse3D(true);
        resideMenu.setBackground(R.drawable.pattern_new);
        //  resideMenu.setBackgroundColor(getResources().getColor(R.color.colorPrimary_new));
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip. 
        resideMenu.setScaleValue(0.6f);

        // create menu items;
        home = new ResideMenuItem(this, R.drawable.new_home, "Home");
        classes     = new ResideMenuItem(this, R.drawable.new_classes,"Classes");
        subjects  = new ResideMenuItem(this, R.drawable.new_subjects,"Subjects");
        attendance = new ResideMenuItem(this, R.drawable.new_att, "Mark");
        pastatt = new ResideMenuItem(this, R.drawable.ic_action_past, "Review");
        genpdf = new ResideMenuItem(this, R.drawable.ic_action_pdf, "View PDF");
        logout = new ResideMenuItem(this, R.drawable.ic_action_logout, "Logout");


        classes.setOnClickListener(this);
        subjects.setOnClickListener(this);
        attendance.setOnClickListener(this);
        pastatt.setOnClickListener(this);
        genpdf.setOnClickListener(this);
        logout.setOnClickListener(this);
        home.setOnClickListener(this);

        resideMenu.addMenuItem(home, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(classes, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(subjects, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(attendance, ResideMenu.DIRECTION_LEFT);


        resideMenu.addMenuItem(pastatt, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(genpdf, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(logout, ResideMenu.DIRECTION_RIGHT);

        // You can disable a direction by setting ->
        // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
        findViewById(R.id.title_bar_right_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {

        if (view == home){
            text.setText("Home");
            changeFragment(new HomeFragment());
        }else if (view == subjects){
            text.setText("Subjects");
            changeFragment(new Subject());
        }else if (view == attendance){
            text.setText("Mark Attendance");
            changeFragment(new Attendance());
        }else if (view == pastatt){
            text.setText("Review Attendance");
            changeFragment(new PastAttendance());
        }else if (view == genpdf){
            text.setText("PDF");
            changeFragment(new PDF());
        }else if (view == logout){
            text.setText("Logout");
            changeFragment(new Logout());
        }else if (view == classes){
            text.setText("Classes");
            changeFragment(new Classes());
        }

        resideMenu.closeMenu();
    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
            //Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void closeMenu() {
            //Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    };

    private void changeFragment(Fragment targetFragment){
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();

    }

    // What good method is to access resideMenu？
    public ResideMenu getResideMenu(){
        return resideMenu;
    }
}
