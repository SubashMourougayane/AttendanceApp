package com.example.hhs.attendance.New_UI.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.hhs.attendance.New_UI.Home.MenuActivity;
import com.example.hhs.attendance.Old_UI.ColgIDAdapter;
import com.example.hhs.attendance.Old_UI.Home;
import com.example.hhs.attendance.Old_UI.StartingActivity;
import com.example.hhs.attendance.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.singh.daman.proprogressviews.DottedArcProgress;

import me.omidh.ripplevalidatoredittext.RVEValidatorFactory;
import me.omidh.ripplevalidatoredittext.RVEValidatorType;
import me.omidh.ripplevalidatoredittext.RippleValidatorEditText;

public class Login extends AppCompatActivity {

    Firebase fb_db;
    String BASE_URL = "https://attendance-79ba4.firebaseio.com/CollegeID/";
    Button submit;
    String ID, pwd;
    DottedArcProgress arc_prog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_new);

        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        final String stats = pref.getString("islogin", "");
        System.out.println("Stats is " + stats);
        if (stats.equals("yes")) {
            Intent i = new Intent(Login.this, MenuActivity.class);
            startActivity(i);
        } else {

            final RippleValidatorEditText colgid = (RippleValidatorEditText) findViewById(R.id.colgid);
            final RippleValidatorEditText pass = (RippleValidatorEditText) findViewById(R.id.pass);
            arc_prog = (DottedArcProgress) findViewById(R.id.arc_prog);
            arc_prog.setVisibility(View.INVISIBLE);

            colgid.addValidator(
                    RVEValidatorFactory.getValidator(RVEValidatorType.MIN_LENGTH, "Minimum length is 4 words", 4),
                    RVEValidatorFactory.getValidator(RVEValidatorType.EMPTY, "Name can't be empty", null)
                    // RVEValidatorFactory.getValidator(RVEValidatorType.BEGIN,"Name should Begin with A","A"),
                    //RVEValidatorFactory.getValidator(RVEValidatorType.EQUAL,"Name should Be Angelina","Angelina")
            );
            pass.addValidator(
                    RVEValidatorFactory.getValidator(RVEValidatorType.MIN_LENGTH, "Minimum length is 4 characters", 4),
                    RVEValidatorFactory.getValidator(RVEValidatorType.EMPTY, "Password cannot be empty", null)
                    //RVEValidatorFactory.getValidator(RVEValidatorType.END, "Email Should End with com", "com")
            );

            Firebase.setAndroidContext(this);
            submit = (Button) findViewById(R.id.colidsubmit);

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ID = colgid.getText().toString();
                    pwd = pass.getText().toString();
                    /*if(ID.equals("")||pwd.equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"Please enter the credentials...",Toast.LENGTH_SHORT).show();
                    }*/
                    //else
                    //{
                    arc_prog.setVisibility(View.VISIBLE);
                    new Login.MyTask().execute();

                    //}


                }
            });
        }


    }

    public class MyTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            final SharedPreferences.Editor editor = pref.edit();
            fb_db = new Firebase(BASE_URL);
            fb_db.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        ColgIDAdapter colgIDAdapter = postSnapshot.getValue(ColgIDAdapter.class);
                        String S = colgIDAdapter.getID();
                        String name = colgIDAdapter.getName();
                        String passss = colgIDAdapter.getPass();
                        System.out.println("LOL 1 IS " + S + "  " + name);
                        System.out.println("retrieved ID is " + S);
                        if (ID.equals(S) && (pwd.equals(passss))) {
                            System.out.println("LOL 2 IS " + S + "  " + name);
                            editor.putString("CID", S);
                            editor.putString("Cname", name);
                            editor.commit();
                            Intent i = new Intent(Login.this, StartingActivity.class);
                            arc_prog.setVisibility(View.INVISIBLE);
                            startActivity(i);
                        }

                    }


                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("FIREBASE ERROR OCCURED");

                }

            });
            return null;
        }


    }
}

