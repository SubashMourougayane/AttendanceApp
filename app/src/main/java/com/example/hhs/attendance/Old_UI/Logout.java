package com.example.hhs.attendance.Old_UI;

/**
 * Created by hhs on 28/2/17.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hhs.attendance.New_UI.Home.HomeFragment;
import com.example.hhs.attendance.New_UI.Login.Login;
import com.example.hhs.attendance.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Belal on 18/09/16.
 */


public class Logout extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments

        return inflater.inflate(R.layout.logout, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();
        editor.putString("CID","");
        editor.putString("Cname","");
        editor.putString("islogin","");
        editor.putString("uname","");
        editor.putString("gender","");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Logout?");
        builder.setMessage("Are you sure to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                editor.commit();
                getActivity().setTitle("Logout");
                getActivity().finish();
                startActivity(new Intent(getActivity(),Login.class));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Fragment fragment= new HomeFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.main_fragment, fragment); // fragment container id in first parameter is the  container(Main layout id) of Activity
                transaction.addToBackStack(null);  // this will manage backstack
                transaction.commit();

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}