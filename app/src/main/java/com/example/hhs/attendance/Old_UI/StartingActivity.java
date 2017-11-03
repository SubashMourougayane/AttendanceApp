package com.example.hhs.attendance.Old_UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hhs.attendance.R;

/**
 * Created by shyam on 2/3/17.
 */

public class StartingActivity extends AppCompatActivity {

    private static final int WRITE_EXTERNAL_STORAGE = 111;
    public static String img="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startingactivity);
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();
        String stats = pref.getString("islogin","");
        String name = pref.getString("uname","");
        String gender = pref.getString("gender","");
        if(stats.equals("yes"))
        {
            System.out.println("BOWWWWWW");
            System.out.println("login as "+gender+" "+name);
            Intent i = new Intent(StartingActivity.this,Home.class);
            startActivity(i);
            finish();

        }


        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        ImageView imageView2 = (ImageView) findViewById(R.id.imageView2);



        TextView textView = (TextView) findViewById(R.id.textView);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img="Mr";
                editor.putString("gender",img);
                editor.commit();
                Intent i = new Intent(StartingActivity.this,StartingActivity2.class);
                startActivity(i);
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img="Mrs";
                editor.putString("gender",img);
                editor.commit();
                Intent i = new Intent(StartingActivity.this,StartingActivity2.class);
                startActivity(i);
            }
        });


    }

}
