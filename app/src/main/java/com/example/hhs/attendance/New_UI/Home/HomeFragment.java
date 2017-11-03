package com.example.hhs.attendance.New_UI.Home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.hhs.attendance.R;
import com.special.ResideMenu.ResideMenu;

import static android.content.Context.MODE_PRIVATE;

/**
 * User: special
 * Date: 13-12-22
 * Time: 下午1:33
 * Mail: specialcyci@gmail.com
 */
public class HomeFragment extends Fragment {

    private View parentView;
    private ResideMenu resideMenu;
    String Uname, post;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.home, container, false);
        setUpViews();
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        Uname = pref.getString("uname","");
        post = pref.getString("post","");

        System.out.println("LOL "+Uname+" "+post);
        TextView textView14 = (TextView) parentView.findViewById(R.id.textView14);
        TextView textView15 = (TextView) parentView.findViewById(R.id.textView15);

        textView14.setText(Uname);
        textView15.setText(post);

        return parentView;
    }

    private void setUpViews() {
        MenuActivity parentActivity = (MenuActivity) getActivity();
        resideMenu = parentActivity.getResideMenu();

    }

}
