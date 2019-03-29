package com.ece496.assignments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ece496.FrontEndEvent;
import com.ece496.MainActivity;
import com.ece496.R;

import java.util.ArrayList;
import java.util.Calendar;

public class CustomAssignment extends AppCompatActivity {


    static Button buton_due_date;
    static Button button_due_time;

    public static EditText text_input_1_title;
    public static EditText integer_input1_work_session;
    public static EditText integer_input2_duration;
    public static TextView errorText;

    public static int due_year;
    public static int due_month;
    public static int due_day;
    public static int due_hour;
    public static int due_minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_assignment);

        final Calendar c = Calendar.getInstance();

        buton_due_date = (Button) findViewById(R.id.user_input_due_date_assignment);
        button_due_time = (Button) findViewById(R.id.user_input_due_time_assignment);
        due_year = c.get(Calendar.YEAR);
        due_month = c.get(Calendar.MONTH)+1;
        due_day = c.get(Calendar.DAY_OF_MONTH);
        due_hour = c.get(Calendar.HOUR_OF_DAY);
        due_minute = c.get(Calendar.MINUTE);

        formatButtonDate(c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH)+1, c.get(Calendar.YEAR), button_due_time);
        formatButtonTime(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), button_due_time);


        text_input_1_title = (EditText) findViewById(R.id.user_input_assignment_title);
        integer_input1_work_session = (EditText) findViewById(R.id.user_input_session);
        integer_input2_duration = (EditText) findViewById(R.id.user_input_session_time);
        errorText = (TextView) findViewById(R.id.output_error_assignment);
        errorText.setVisibility(View.INVISIBLE);

        buton_due_date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // showDialog(DATE_DIALOG_ID);
                DialogFragment newFragment = new CustomAssignment.DatePickerFragmentAssignment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        button_due_time.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // showDialog(DATE_DIALOG_ID);
                DialogFragment newFragment = new CustomAssignment.TimePickerFragmentAssignment();
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        Button button_back = (Button) findViewById(R.id.cancel_assignment);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button button_done = (Button) findViewById(R.id.submit_assignment);
        button_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
    }




    public void formatButtonTime(int hour, int minute, Button button) {

        String min = "";

        if (minute < 10) {
            min = "0" + String.valueOf(minute);
        } else {
            min = String.valueOf(minute);
        }

        if ((hour == 0) || (hour == 24)) {
            hour = 12;
            button.setText(String.valueOf(hour) + ":" + min + " a.m.");
        } else if (hour < 12) {
            button.setText(String.valueOf(hour) + ":" + min + " a.m.");
        } else if (hour == 12) {
            button.setText(String.valueOf(hour) + ":" + min + " p.m.");
        } else {
            hour = hour - 12;
            button.setText(String.valueOf(hour) + ":" + min + " p.m.");
        }

    }

    public void formatButtonDate(int day, int month, int year, Button button) {
        buton_due_date.setText(String.valueOf(day) + "/" + String.valueOf(month) + "/" + String.valueOf(year));
    }



    public static class DatePickerFragmentAssignment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            due_day = day;
            due_month = month +1;
            due_year=year;
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            due_day = day;
            due_month = month +1;
            due_year=year;
            formatButtonDatePicker(day, month+1, year, buton_due_date);
        }

        public void formatButtonDatePicker(int day, int month, int year, Button button) {
            buton_due_date.setText(String.valueOf(day) + "/" + String.valueOf(month) + "/" + String.valueOf(year));
        }

    }



    public static class TimePickerFragmentAssignment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            due_hour = hour;
            due_minute = minute;
            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hour, int minute) {

            due_hour = hour;
            due_minute = minute;
            formatButtonTimePicker(hour, minute, button_due_time);
        }

        public void formatButtonTimePicker(int hour, int minute, Button button) {

            String min = "";

            if (minute < 10) {
                min = "0" + String.valueOf(minute);
            } else {
                min = String.valueOf(minute);
            }

            if ((hour == 0) || (hour == 24)) {
                hour = 12;
                button.setText(String.valueOf(hour) + ":" + min + " a.m.");
            } else if (hour < 12) {
                button.setText(String.valueOf(hour) + ":" + min + " a.m.");
            } else if (hour == 12) {
                button.setText(String.valueOf(hour) + ":" + min + " p.m.");
            } else {
                hour = hour - 12;
                button.setText(String.valueOf(hour) + ":" + min + " p.m.");
            }

        }

    }



    /******************************************************************************************************************************************************************************************************
     * CODE FOR VALIDATING
     *******************************************************************************************************************************************************************************************************/


    public String pad_string(int var, int threshold) {
        String new_var = "";
        if (var < threshold) {
            new_var = "0" + String.valueOf(var);
        } else {
            new_var = String.valueOf(var);
        }

        return new_var;
    }

    public void validate() {

        int minutes_per_year = 525600;
        int minutes_per_month = 43800;
        int minutes_per_day = 1440;
        int minutes_per_hour = 60;

        String title = text_input_1_title.getEditableText().toString();

        int number_work_sessions = 0;
        int duration_work_sessions = 0;

        if (integer_input1_work_session.getEditableText().toString().equals("")) {
            number_work_sessions = 0;
        } else {
            number_work_sessions = (int) Long.parseLong(integer_input1_work_session.getEditableText().toString());
        }


        if (integer_input2_duration.getEditableText().toString().equals("")) {
            duration_work_sessions = 0;
        } else {
            duration_work_sessions = (int) Long.parseLong(integer_input2_duration.getEditableText().toString());
        }

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        long curr_val = (year - 1) * minutes_per_year + (month) * minutes_per_month + (day - 1) * minutes_per_day
                + (hour - 1) * minutes_per_hour + minute;
        long end_val = (due_year - 1) * minutes_per_year + (due_month - 1) * minutes_per_month
                + (due_day - 1) * minutes_per_day + (due_hour - 1) * minutes_per_hour + due_minute;

        int duration = (int) (end_val - curr_val);
        int work_session_duration = duration_work_sessions*number_work_sessions;

        String title2 = title.replaceAll("\\s", "");


        if (title2.equals("")) {
            errorText.setText("*  assignment identifier required *");
            errorText.setVisibility(View.VISIBLE);

        } else if (duration <= 0) {
            errorText.setText("*  assignment must be due in the future *");
            errorText.setVisibility(View.VISIBLE);

        } else if ((duration_work_sessions>0) && (number_work_sessions <= 0)) {
            errorText.setText("*  you must have atleast one work session *");
            errorText.setVisibility(View.VISIBLE);

        } else if (duration <= work_session_duration) {
            errorText.setText("*  please reduce number or duration of work session *");
            errorText.setVisibility(View.VISIBLE);

        } else if ((number_work_sessions > 0) && (duration_work_sessions < 15)) {
            errorText.setText("*  work session duration must be atleast 15 mins*");
            errorText.setVisibility(View.VISIBLE);

        } else if (duration_work_sessions > 360) {
            errorText.setText("*  work session must be less than 360 mins (6h)*");
            errorText.setVisibility(View.VISIBLE);

        } else {

            String due_string = pad_string(due_year, 1000) + " " + pad_string(due_month, 10) + " "
                    + pad_string(due_day, 10) + " " + pad_string(due_hour, 10) + " "
                    + pad_string(due_minute, 10);

            //essay(title, pages_to_write, difficulty, research, pages_reading, due_string);
            Intent i = new Intent(CustomAssignment.this, MainActivity.class);
            startActivity(i);

        }


    }


}
