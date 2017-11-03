package com.example.hhs.attendance.Old_UI;

/**
 * Created by hhs on 28/2/17.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hhs.attendance.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Belal on 18/09/16.
 */


public class Stats extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static ArrayList<DataObject>present;
    public static ArrayList<DataObject>absent;
    public static ArrayList<DataObject>onduty;
    public static ArrayList<DataObject>leave;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view = inflater.inflate(R.layout.stats, container, false);
        setcontent();
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        return view;
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new Present(), "Present");
        adapter.addFragment(new Absent(), "Absent");
        adapter.addFragment(new OnDuty(), "OnDuty");
        adapter.addFragment(new Leave(), "Leave");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Stats");
    }
    public void setcontent()
    {
        present=new ArrayList<>();
        absent=new ArrayList<>();
        onduty=new ArrayList<>();
        leave=new ArrayList<>();
        for(int i=0;i<PastAttendance.allatt.size();i++)
        {
            switch(PastAttendance.allatt.get(i))
            {
                case "Present":
                    present.add(new DataObject(PastAttendance.allstu.get(i)));
                    break;
                case "Absent":
                    absent.add(new DataObject(PastAttendance.allstu.get(i)));
                    break;
                case "On Duty":
                    onduty.add(new DataObject(PastAttendance.allstu.get(i)));
                    break;
                case "Leave":
                    leave.add(new DataObject(PastAttendance.allstu.get(i)));
                    break;

            }
        }
    }

}