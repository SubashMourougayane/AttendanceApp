package com.example.hhs.attendance.Old_UI;

/**
 * Created by hhs on 28/2/17.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hhs.attendance.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;



public class PDF extends Fragment
{
    Button gen;
    Spinner classspinner,subjspinner,datespinner,hourspinner;
    EditText fromto;
    ArrayList<String> Classlist;
    ArrayList<String> Subjlist ;
    ArrayList<String> Datelist;
    ArrayList<String> Hourlist;
    Firebase fb_db,fb_db2;
    String Base_Url;
    String CID,uname;
    String c,s,d,h;
    ArrayList<String> class_content=new ArrayList<>();
    ArrayList<String> sub_content=new ArrayList<>();
    ArrayList<String> date_content=new ArrayList<>();
    ArrayList<String> hour_content=new ArrayList<>();
    ArrayList<String> attlist = new ArrayList<>();
    ArrayList<String> stulist = new ArrayList<>();

    View v;
    private String fromto_string;
    LinearLayout l1,l2;
    Boolean flag = false;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        v = inflater.inflate(R.layout.generate_pdf, container, false);
        classspinner = (Spinner) v.findViewById(R.id.classspinner);
        subjspinner = (Spinner) v.findViewById(R.id.subjspinner);
        datespinner = (Spinner) v.findViewById(R.id.datespinner);
        hourspinner = (Spinner) v.findViewById(R.id.hourspinner);
        l1 = (LinearLayout) v.findViewById(R.id.l1);
        l2 = (LinearLayout) v.findViewById(R.id.l2);
        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
        CID = pref.getString("CID","");
        uname = pref.getString("uname","");
        Firebase.setAndroidContext(getActivity());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirmation");
        builder.setMessage("Generate PDF for a single day or multiple days?");
        builder.setCancelable(false);
        builder.setPositiveButton("Single Day", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                l1.setVisibility(View.VISIBLE);
                l2.setVisibility(View.GONE);
                flag = false;
                new MyTask().execute();
            }
        }).setNegativeButton("Multiple Days", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fromto = (EditText) v.findViewById(R.id.fromto);
                l1.setVisibility(View.GONE);
                l2.setVisibility(View.VISIBLE);
                flag = true;
                new MyTask().execute();

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        classspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("CLICKING ON "+Classlist.get(position));
                c = Classlist.get(position);
                new MyTask2().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        subjspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("CLICKING ON "+Subjlist.get(position));
                s = Subjlist.get(position);
                new MyTask3().execute();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        datespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("CLICKING ON "+Datelist.get(position));
                d = Datelist.get(position);
                new MyTask4().execute();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        hourspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("CLICKING ON "+Hourlist.get(position));
                h = Hourlist.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        if(!flag) {

            getActivity().setTitle("Generate PDF");
            File dir = new File(Environment.getExternalStorageDirectory() + "/Attendance");
            try {
                if (dir.mkdir()) {
                    System.out.println("Directory created");
                } else {
                    System.out.println("Directory is not created");
                }
            } catch (Exception e) {
                System.out.println("BOWW 1");
                e.printStackTrace();
            }


            gen = (Button) v.findViewById(R.id.genbut);

            gen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("LOLOLOL " + c + "_" + s + "_" + d + "_" + h);
                    new MyTask9().execute();

                }
            });


        }
        else
        {
            getActivity().setTitle("Generate PDF");
            File dir = new File(Environment.getExternalStorageDirectory() + "/Attendance");
            try {
                if (dir.mkdir()) {
                    System.out.println("Directory created");
                } else {
                    System.out.println("Directory is not created");
                }
            } catch (Exception e) {
                System.out.println("BOWW 1");
                e.printStackTrace();
            }


            gen = (Button) v.findViewById(R.id.genbut);

            gen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    c = classspinner.getSelectedItem().toString();
                    s = subjspinner.getSelectedItem().toString();
                    fromto_string = fromto.getText().toString();

                    System.out.println("LOLOLOL " + c + "_" + s + "_" + d + "_" + h);
                    new MyTask4().execute();

                }
            });
        }
        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles




    }
    @Override
    public void onResume() {
        super.onResume();


        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener
                    Toast.makeText(getActivity(), "Swipe to change menu", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }
    public class MyTask extends AsyncTask<String, Integer, String>
    {

        @Override
        protected String doInBackground(String... params)
        {
             Classlist = new ArrayList<>();
            Base_Url = "https://attendance-79ba4.firebaseio.com/"+CID+"/Attendance/"+uname+"/";
            fb_db=new Firebase(Base_Url);
            System.out.println("BASE URL IS "+Base_Url);
            fb_db.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                    {
                        System.out.println("KEYS ARE "+postSnapshot.getKey());
                        String S = postSnapshot.getKey();
                        String S1[]=S.split("_");
                        Classlist.add(S1[1]);
                    }

                    Set<String> hs = new HashSet<String>();
                    hs.addAll(Classlist);
                    Classlist.clear();
                    Classlist.addAll(hs);

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Classlist);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    classspinner.setAdapter(dataAdapter);


                    System.out.println("LIST IS "+Classlist);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError)
                {
                    System.out.println("FIREBASE ERROR OCCURED");
                }

            });
            return null;
        }


    }
    public class MyTask2 extends AsyncTask<String, Integer, String>
    {

        @Override
        protected String doInBackground(String... params)
        {
            Subjlist = new ArrayList<>();
            Base_Url = "https://attendance-79ba4.firebaseio.com/"+CID+"/Attendance/"+uname+"/";
            fb_db=new Firebase(Base_Url);
            System.out.println("BASE URL IS "+Base_Url);
            fb_db.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                    {
                        System.out.println("KEYS ARE "+postSnapshot.getKey());
                        String S = postSnapshot.getKey();
                        if(S.contains(c))
                        {
                            String S1[]=S.split("_");
                            Subjlist.add(S1[2]);

                        }
                    }
                    Set<String> hs1 = new HashSet<String>();
                    hs1.addAll(Subjlist);
                    Subjlist.clear();
                    Subjlist.addAll(hs1);

                    ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Subjlist);
                    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    subjspinner.setAdapter(dataAdapter2);

                    System.out.println("LIST IS "+Subjlist);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError)
                {
                    System.out.println("FIREBASE ERROR OCCURED");
                }

            });
            return null;
        }


    }
    public class MyTask3 extends AsyncTask<String, Integer, String>
    {

        @Override
        protected String doInBackground(String... params)
        {

            Datelist = new ArrayList<>();
            Base_Url = "https://attendance-79ba4.firebaseio.com/"+CID+"/Attendance/"+uname+"/";
            fb_db=new Firebase(Base_Url);
            System.out.println("BASE URL IS "+Base_Url);
            fb_db.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                    {
                        System.out.println("KEYS ARE "+postSnapshot.getKey());
                        String S = postSnapshot.getKey();
                        if(S.contains(c)&&S.contains(c))
                        {
                            String S1[]=S.split("_");
                            Datelist.add(S1[3]);

                        }
                    }

                    Set<String> hs2 = new HashSet<String>();
                    hs2.addAll(Datelist);
                    Datelist.clear();
                    Datelist.addAll(hs2);

                    ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Datelist);
                    dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    datespinner.setAdapter(dataAdapter3);

                    System.out.println("LIST IS "+Datelist);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError)
                {
                    System.out.println("FIREBASE ERROR OCCURED");
                }

            });
            return null;
        }


    }
    public class MyTask4 extends AsyncTask<String, Integer, String>
    {

        @Override
        protected String doInBackground(String... params)
        {

            Hourlist = new ArrayList<>();


            Base_Url = "https://attendance-79ba4.firebaseio.com/"+CID+"/Attendance/"+uname+"/";
            fb_db=new Firebase(Base_Url);
            System.out.println("BASE URL IS "+Base_Url);
            fb_db.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                    {
                        System.out.println("KEYS ARE "+postSnapshot.getKey());
                        String S = postSnapshot.getKey();
                        if(S.contains(c)&&S.contains(s)&&S.contains(d))
                        {
                            String S1[]=S.split("_");
                            Hourlist.add(S1[4]);
                        }

                    }

                    Set<String> hs3 = new HashSet<String>();
                    hs3.addAll(Hourlist);
                    Hourlist.clear();
                    Hourlist.addAll(hs3);

                    ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Hourlist);
                    dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    hourspinner.setAdapter(dataAdapter4);
                    System.out.println("LIST IS "+Hourlist);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError)
                {
                    System.out.println("FIREBASE ERROR OCCURED");
                }

            });
            return null;
        }


    }
    private class MyTask9 extends AsyncTask<String, Integer, String>
    {


        @Override
        protected String doInBackground(String... params) {



            String URL = "https://attendance-79ba4.firebaseio.com/"+CID+"/Attendance/"+uname+"/"+uname+"_"+c+"_"+s+"_"+d+"_"+h+"/";
            System.out.println("BASE 2 URL IS $"+URL);
            fb_db2=new Firebase(URL);
            attlist = new ArrayList<>();
            stulist = new ArrayList<>();

//            uname+"_"+c+"_"+s+"_"+d+"_"+h+"/"

            fb_db2.addValueEventListener(new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {

                    for(DataSnapshot postSnapshot:dataSnapshot.getChildren()) {
                        System.out.println("keys are 1:" + postSnapshot.getKey());

                        String S = postSnapshot.getKey();
                        AttendanceAdapter attendanceAdapter = dataSnapshot.getValue(AttendanceAdapter.class);

                            System.out.println("LOL PRINT 2 " + attendanceAdapter.StudAttendance);
                            attlist = attendanceAdapter.StudAttendance;
                            stulist = attendanceAdapter.StudList;

                            System.out.println("LOL PRINT AAGUTHU");

                            System.out.println("STU LIST IS " + stulist);
                            System.out.println("STU LIST IS " + attlist);




                    }
                    System.out.println("LOL POST EXEC");
//            CreatePDF();
                    System.out.println("IN PDF CREATOR");
                    Document document = new Document();
                    String fname = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
                    String path = Environment.getExternalStorageDirectory()+"/Attendance/"+fname+"_"+s+"_"+h+".pdf";
                    try {

                        System.out.println("LIST IS "+Subjlist.toString());

                        PdfWriter.getInstance(document,new FileOutputStream(path));
                        document.open();
                        document.add(new Paragraph("Class   :   "+c));
                        document.add(new Paragraph("Subject   :   "+s));
                        document.add(new Paragraph("Date   :   "+d));
                        document.add(new Paragraph("Hour   :   "+h));
                        document.add(new Paragraph("\n"));

                        PdfPTable table = new PdfPTable(2);
                        PdfPCell cell = new PdfPCell(new Phrase(fname));
                        cell.setColspan(3);
                        table.addCell(cell);

                        // we add the four remaining cells with addCell()
//                        table.addCell("row 1; cell 1");
//                        table.addCell("row 1; cell 2");
//                        table.addCell("row 2; cell 1");
//                        table.addCell("row 2; cell 2");
                        System.out.println("PDF * "+stulist+"  "+attlist);
                        Collections.reverse(attlist);
                        Collections.reverse(stulist);
                        for(int i=0;i<stulist.size();i++)
                        {
                            table.addCell(stulist.get(i));
                            table.addCell(attlist.get(i));
                            System.out.println("Adding "+stulist.get(i));
                        }
                        document.add(table);
                        document.close();
                        System.out.println("BOWW 2");

                    } catch (Exception e)
                    {
                        System.out.println("EXCEPTION is "+e);
                    }




                }


                @Override
                public void onCancelled(FirebaseError firebaseError)
                {
                    System.out.println("FIREBASE ERROR OCCURED");

                }

            });


            return "SUCCESS";
        }
        @Override
        protected void onPostExecute(String result)
        {
            System.out.println("IN POST EXEC");

        }


            //    progressDialog.dismiss();
            // Do things like hide the progress bar or change a TextView
        }




}