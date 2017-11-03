package com.example.hhs.attendance.New_UI.Home;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.hhs.attendance.Old_UI.SelectDateFragment;
import com.example.hhs.attendance.R;

import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * User: special
 * Date: 13-12-22
 * Time: 下午1:31
 * Mail: specialcyci@gmail.com
 */
public class PDFFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_pdf, container, false);

        Button genpdf = (Button) v.findViewById(R.id.genpdf);

        genpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        return v;
    }


    private void showDatePicker() {
        SelectDateFragment date = new SelectDateFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }


    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            DecimalFormat mFormat= new DecimalFormat("00");

          //  editText2.setText(String.valueOf(mFormat.format(Double.valueOf(dayOfMonth))) + "-" + String.valueOf(mFormat.format(Double.valueOf(month+1)))
                  //  + "-" + String.valueOf(mFormat.format(Double.valueOf(year))));

          //  CurDate=String.valueOf(mFormat.format(Double.valueOf(dayOfMonth))) + "-" + String.valueOf(mFormat.format(Double.valueOf(month+1)))
                  //  + "-" + String.valueOf(mFormat.format(Double.valueOf(year)));
        }
    };

}


