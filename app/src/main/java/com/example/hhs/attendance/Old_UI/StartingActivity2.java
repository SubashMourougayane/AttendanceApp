package com.example.hhs.attendance.Old_UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hhs.attendance.New_UI.Home.MenuActivity;
import com.example.hhs.attendance.R;

/**
 * Created by shyam on 2/3/17.
 */

public class StartingActivity2 extends AppCompatActivity {

    static String name="";
    static String post="";
    EditText editText,editText2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startingactivity2);

        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        TextView textView =(TextView) findViewById(R.id.textView3);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();
        String gender = pref.getString("gender","");

        if(gender.equals("Mr"))
        {
               imageView.setImageResource(R.drawable.man);
        }
        else
        {
            imageView.setImageResource(R.drawable.woman);
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=editText.getText().toString();
                post=editText2.getText().toString();
                if(name.equals(null))
                {
                    Toast.makeText(StartingActivity2.this,"Please Enter Your Name",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    editor.putString("islogin","yes");
                    editor.putString("uname",name);
                    editor.putString("post",post);
                    editor.commit();
                    Intent i = new Intent(StartingActivity2.this,MenuActivity.class);
                    startActivity(i);
                    StartingActivity2.this.finish();
                }

            }
        });



        System.out.println("bbb"+name);
    }
}
