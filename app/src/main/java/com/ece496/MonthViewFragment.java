package com.ece496;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ece496.backend.Scheduler;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MonthViewFragment extends Fragment {


    static TextView CurrentDate;
    static ImageView left;
    static ImageView right;
    public static final String TAG = "YOUR-TAG-NAME";
    static int curr_year;
    static int curr_month;
    static int curr_day;

    private RelativeLayout mLayout;

    Calendar c;
    int offset;
    int margin_offset;
    private int eventIndex;



    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monthview, container, false);

        //CalendarCustomView mView = (CalendarCustomView)view.findViewById(R.id.custom_calendar);

        System.out.println (new Scheduler().get_backend_events_of_date(LocalDate.now()));

        mLayout = view.findViewById(R.id.left_event_column);
        eventIndex = mLayout.getChildCount();

        CurrentDate = view.findViewById(R.id.display_current_date);
        left = view.findViewById(R.id.previous_day);
        right = view.findViewById(R.id.next_day);
            c = Calendar.getInstance();
            int day = c.get(Calendar.DAY_OF_MONTH);
            int month = c.get(Calendar.MONTH);
            int year = c.get(Calendar.YEAR);
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            curr_day = day;
            curr_month = month + 1;
            curr_year = year;

            CurrentDate.setText(String.format("%s %d, %d", getMonthName(curr_month), curr_day, curr_year));


            CurrentDate.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // showDialog(DATE_DIALOG_ID);
                    DialogFragment newFragment = new MonthViewFragment.DatePickerFragmentMainPage();
                    newFragment.show(getFragmentManager(), "datePicker");
                }
            });


            left.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    curr_day = day;
                    curr_month = month + 1;
                    curr_year = year;
                    offset = offset -1;

                    c = Calendar.getInstance();
                    c.add(Calendar.DAY_OF_MONTH, offset);
                    int day = c.get(Calendar.DAY_OF_MONTH);
                    int month = c.get(Calendar.MONTH);
                    int year = c.get(Calendar.YEAR);
                    int hour = c.get(Calendar.HOUR_OF_DAY);
                    int minute = c.get(Calendar.MINUTE);

                    curr_day = day;
                    curr_month = month + 1;
                    curr_year = year;
                    CurrentDate.setText(String.format("%s %d, %d", getMonthName(curr_month), curr_day, curr_year));
                }
            });

        right.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                curr_day = day;
                curr_month = month + 1;
                curr_year = year;
                offset = offset + 1;

                c = Calendar.getInstance();
                c.add(Calendar.DAY_OF_MONTH, offset);
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                curr_day = day;
                curr_month = month + 1;
                curr_year = year;
                CurrentDate.setText(String.format("%s %d, %d", getMonthName(curr_month), curr_day, curr_year));


            }
        });





        displayDailyEvents();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public static class DatePickerFragmentMainPage extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            curr_day = day;
            curr_month = month +1;
            curr_year=year;
            CurrentDate.setText(String.format("%s %d, %d", getMonthName(curr_month), curr_day, curr_year));

            //GET NEW LIST
        }

        public String getMonthName(int n) {

            String month = "";

            switch (n) {
                case 1:
                    month = "January";
                    break;
                case 2:
                    month = "February";
                    break;
                case 3:
                    month = "March";
                    break;
                case 4:
                    month = "April";
                    break;
                case 5:
                    month = "May";
                    break;
                case 6:
                    month = "June";
                    break;
                case 7:
                    month = "July";
                    break;
                case 8:
                    month = "August";
                    break;
                case 9:
                    month = "September";
                    break;
                case 10:
                    month = "October";
                    break;
                case 11:
                    month = "November";
                    break;
                case 12:
                    month = "December";
                    break;
                default:
                    month = "";
                    break;
            }

            return month;
        }


    }

    public String getMonthName(int n) {

        String month = "";

        switch (n) {
            case 1:
                month = "January";
                break;
            case 2:
                month = "February";
                break;
            case 3:
                month = "March";
                break;
            case 4:
                month = "April";
                break;
            case 5:
                month = "May";
                break;
            case 6:
                month = "June";
                break;
            case 7:
                month = "July";
                break;
            case 8:
                month = "August";
                break;
            case 9:
                month = "September";
                break;
            case 10:
                month = "October";
                break;
            case 11:
                month = "November";
                break;
            case 12:
                month = "December";
                break;
            default:
                month = "";
                break;
        }

        return month;
    }



    private void displayDailyEvents(){

        Scheduler s = new Scheduler();
        List<FrontEndEvent> dailyEvent = new ArrayList<FrontEndEvent>(); //s.get_frontend_events_of_day(curr_year, curr_month, curr_day);

        FrontEndEvent temp1 = new FrontEndEvent("event 1", "desc1", "2019 03 18 1 00", "2019 03 18 03 00", 120, "0000 00 00 00 00",
                "0000 00 00 00 00", 0, "0000 00 00 00 00");

        FrontEndEvent temp2 = new FrontEndEvent("event 2", "desc2", "2019 03 18 10 00", "2019 03 18 11 00", 120, "0000 00 00 00 00",
                "0000 00 00 00 00", 0, "0000 00 00 00 00");

        FrontEndEvent temp3 = new FrontEndEvent("event 3", "desc3", "2019 03 18 18 00", "2019 03 18 19 30", 120, "0000 00 00 00 00",
                "0000 00 00 00 00", 0, "0000 00 00 00 00");

        FrontEndEvent temp4 = new FrontEndEvent("event 3", "desc3", "2019 03 18 13 00", "2019 03 18 14 30", 120, "0000 00 00 00 00",
                "0000 00 00 00 00", 0, "0000 00 00 00 00");


        dailyEvent.add(temp1);
        dailyEvent.add(temp2);
        dailyEvent.add(temp4);
        dailyEvent.add(temp3);



        margin_offset = 15;
        for(FrontEndEvent eObject : dailyEvent){
            Date eventDate = eObject.getDate();
            Date endDate = eObject.getEnd();
            String eventMessage = eObject.getMessage();
            int eventBlockHeight = getEventTimeFrame(eventDate, endDate);
            Log.d(TAG, "Height " + eventBlockHeight);
            displayEventSection(eventDate, eventBlockHeight, eventMessage);
            margin_offset += 15 + eventBlockHeight;
        }
    }

    private int getEventTimeFrame(Date start, Date end){
        long diff = end.getTime() - start.getTime();
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;

        Log.d(TAG, "Hour value diff" + diffHours);
        Log.d(TAG, "Minutes value diff" + diffMinutes);
        long height  =  (diffHours * 60) + ((diffMinutes * 60) / 100) ;
        int h = (int) height;
        return h;
    }

    private void displayEventSection(Date eventDate, int height, String message){
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        String displayValue = timeFormatter.format(eventDate);
        String[]hourMinutes = displayValue.split(":");
        int hours = Integer.parseInt(hourMinutes[0]);
        int minutes = Integer.parseInt(hourMinutes[1]);
        Log.d(TAG, "Hour value " + hours);
        Log.d(TAG, "Minutes value " + minutes);

        int topViewMargin = 0 + margin_offset;
        Log.d(TAG, "Margin top " + topViewMargin);
        createEventView(topViewMargin, height, message);
    }
    private void createEventView(int topMargin, int height, String message){
        TextView mEventView = new TextView(getContext());

        RelativeLayout.LayoutParams lParam = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lParam.addRule(RelativeLayout.ALIGN_PARENT_START);
        lParam.topMargin = topMargin * 2;
        lParam.leftMargin = 24;
        mEventView.setLayoutParams(lParam);
        mEventView.setPadding(24, 0, 24, 0);
        mEventView.setHeight(height * 2);
        mEventView.setGravity(0x11);
        mEventView.setTextColor(Color.parseColor("#ffffff"));
        mEventView.setText(message);
        mEventView.setBackgroundColor(R.color.colorPrimary);
        mLayout.addView(mEventView, eventIndex - 1);
    }


}