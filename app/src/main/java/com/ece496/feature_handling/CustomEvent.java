package com.ece496.feature_handling;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.app.Dialog;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ece496.FrontEndEvent;
import com.ece496.MainActivity;
import com.ece496.R;

import org.xmlpull.v1.XmlPullParser;

import java.lang.*;
import java.util.ArrayList;
import java.util.Calendar;

public class CustomEvent extends AppCompatActivity {

    static Button mPickStartDate;
    static Button mPickStartTime;

    static Button mPickEndDate;
    static Button mPickEndTime;

    static Button mFreqEndDate;

    static TextView mFreqEndText;
    static TextView mErrorText;

    static String title;
    static String description;

    static String start_time_string;
    static int start_year;
    static int start_month;
    static int start_day;
    static int start_hour;
    static int start_minute;

    static String end_time_string;
    static int end_year;
    static int end_month;
    static int end_day;
    static int end_hour;
    static int end_minute;

    static int freq_end_year;
    static int freq_end_month;
    static int freq_end_day;

    static int freq;

    static EditText eventTitle;
    static EditText eventDesc;

    static int button_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        button_pressed = 0;
        freq = 0;

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_custom_event);

        mPickStartDate = (Button) findViewById(R.id.user_input_start_date);
        mPickStartTime = (Button) findViewById(R.id.user_input_start_time);
        mPickEndDate = (Button) findViewById(R.id.user_input_end_date);
        mPickEndTime = (Button) findViewById(R.id.user_input_end_time);
        mFreqEndDate = (Button) findViewById(R.id.user_input_freq_end_date);

        Button goBack = (Button) findViewById(R.id.cancel);
        Button submitted = (Button) findViewById(R.id.submit);

        eventTitle = (EditText) findViewById(R.id.user_input_event);
        eventDesc = (EditText) findViewById(R.id.user_input_description);

        mFreqEndText = (TextView) findViewById(R.id.textView_Frequency_End);
        mErrorText = (TextView) findViewById(R.id.textView_Error);

        mErrorText.setVisibility(View.INVISIBLE);
        mFreqEndDate.setVisibility(View.INVISIBLE);
        mFreqEndText.setVisibility(View.INVISIBLE);

        final Calendar c = Calendar.getInstance();

        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        mPickStartDate.setText(String.valueOf(day) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(year));
        mPickEndDate.setText(String.valueOf(day) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(year));
        mFreqEndDate.setText(String.valueOf(day) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(year));

        start_year = year;
        start_month = month + 1;
        start_day = day;
        start_hour = hour;
        start_minute = minute;

        end_year = year;
        end_month = month + 1;
        end_day = day;
        end_hour = hour;
        end_minute = minute;

        freq_end_year = year;
        freq_end_month = month + 1;
        freq_end_day = day;

        String min = "";

        if (minute < 10) {
            min = "0" + String.valueOf(minute);
        } else {
            min = String.valueOf(minute);
        }

        if ((hour == 0) || (hour == 24)) {
            hour = 12;
            mPickStartTime.setText(String.valueOf(hour) + ":" + min + " a.m.");
            mPickEndTime.setText(String.valueOf(hour) + ":" + min + " a.m.");
        } else if (hour < 12) {
            mPickStartTime.setText(String.valueOf(hour) + ":" + min + " a.m.");
            mPickEndTime.setText(String.valueOf(hour) + ":" + min + " a.m.");
        } else if (hour == 12) {
            mPickStartTime.setText(String.valueOf(hour) + ":" + min + " p.m.");
            mPickEndTime.setText(String.valueOf(hour) + ":" + min + " p.m.");
        } else {
            hour = hour - 12;
            mPickStartTime.setText(String.valueOf(hour) + ":" + min + " p.m.");
            mPickEndTime.setText(String.valueOf(hour) + ":" + min + " p.m.");
        }

        mPickStartDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // showDialog(DATE_DIALOG_ID);
                button_pressed = 1;
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        mPickEndDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // showDialog(DATE_DIALOG_ID);
                button_pressed = 2;
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        mFreqEndDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // showDialog(DATE_DIALOG_ID);
                button_pressed = 3;
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        mPickStartTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // showDialog(DATE_DIALOG_ID);
                button_pressed = 1;
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        mPickEndTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // showDialog(DATE_DIALOG_ID);
                button_pressed = 2;
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        submitted.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                validate();
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // showDialog(DATE_DIALOG_ID);
                finish();
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.frequency_spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                freq = i;

                if (i > 0) {
                    mFreqEndDate.setVisibility(View.VISIBLE);
                    mFreqEndText.setVisibility(View.VISIBLE);
                } else {
                    mFreqEndDate.setVisibility(View.INVISIBLE);
                    mFreqEndText.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

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

        title = eventTitle.getEditableText().toString();
        description = eventDesc.getEditableText().toString();

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        long curr_val = (year - 1) * minutes_per_year + (month) * minutes_per_month + (day - 1) * minutes_per_day
                + (hour - 1) * minutes_per_hour + minute;
        long start_val = (start_year - 1) * minutes_per_year + (start_month - 1) * minutes_per_month
                + (start_day - 1) * minutes_per_day + (start_hour - 1) * minutes_per_hour + start_minute;
        long end_val = (end_year - 1) * minutes_per_year + (end_month - 1) * minutes_per_month
                + (end_day - 1) * minutes_per_day + (end_hour - 1) * minutes_per_hour + end_minute;

        int duration = (int) (end_val - start_val);

        String title2 = title.replaceAll("\\s", "");

        if (title2.equals("")) {

            mErrorText.setText("*  event title required *");
            mErrorText.setVisibility(View.VISIBLE);

        } else {

            if (duration <= 0) {
                mErrorText.setText("*  event must start BEFORE event end time *");
                mErrorText.setVisibility(View.VISIBLE);

            } else {

                int diff = (int) (start_val - curr_val);

                if (diff < 0) {
                    mErrorText.setText("*  event cannot start in the past *");
                    mErrorText.setVisibility(View.VISIBLE);

                } else {
                    long freq_val = (freq_end_year - 1) * minutes_per_year + (freq_end_month - 1) * minutes_per_month
                            + (freq_end_day - 1) * minutes_per_day;
                    diff = (int) (freq_val - end_val);

                    if ((diff <= 0) && (freq > 0)) {
                        mErrorText.setText("*  frequency end date MUST be after event end date *");
                        mErrorText.setVisibility(View.VISIBLE);

                    } else {

                        String freq_end_time_string = "0000 00 00 00 00";

                        if (freq > 0) {
                            freq_end_time_string = pad_string(freq_end_year, 1000) + " "
                                    + pad_string(freq_end_month, 10) + " " + pad_string(freq_end_day, 10) + " 00 00";
                        }

                        start_time_string = pad_string(start_year, 1000) + " " + pad_string(start_month, 10) + " "
                                + pad_string(start_day, 10) + " " + pad_string(start_hour, 10) + " "
                                + pad_string(start_minute, 10);
                        end_time_string = pad_string(end_year, 1000) + " " + pad_string(end_month, 10) + " "
                                + pad_string(end_day, 10) + " " + pad_string(end_hour, 10) + " "
                                + pad_string(end_minute, 10);

                        freq = freq-1;
                        FrontEndEvent event = new FrontEndEvent(title, description, start_time_string, end_time_string,
                                duration, start_time_string, freq_end_time_string, freq, "0000 00 00 00 00");
                        //event.print_details();
                        //System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

                        //event.schedule();
                        Intent i = new Intent(CustomEvent.this, MainActivity.class);
                        startActivity(i);
                    }

                }

            }

        }

    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        public EditText editText;
        DatePicker dpResult;

        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            if (button_pressed == 1) {

                start_year = year;
                start_month = month + 1;
                start_day = day;

                mPickStartDate
                        .setText(String.valueOf(day) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(year));

            } else if (button_pressed == 2) {

                end_year = year;
                end_month = month + 1;
                end_day = day;

                mPickEndDate
                        .setText(String.valueOf(day) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(year));
            } else {
                freq_end_year = year;
                freq_end_month = month + 1;
                freq_end_day = day;

                mFreqEndDate
                        .setText(String.valueOf(day) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(year));
            }

        }
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hour, int minute) {

            String min = "";

            if (minute < 10) {
                min = "0" + String.valueOf(minute);
            } else {
                min = String.valueOf(minute);
            }

            if (button_pressed == 1) {

                start_hour = hour;
                start_minute = minute;

                if ((hour == 0) || (hour == 24)) {
                    hour = 12;
                    mPickStartTime.setText(String.valueOf(hour) + ":" + min + " a.m.");
                } else if (hour < 12) {
                    mPickStartTime.setText(String.valueOf(hour) + ":" + min + " a.m.");
                } else if (hour == 12) {
                    mPickStartTime.setText(String.valueOf(hour) + ":" + min + " p.m.");
                } else {
                    hour = hour - 12;
                    mPickStartTime.setText(String.valueOf(hour) + ":" + min + " p.m.");
                }

            } else {

                end_hour = hour;
                end_minute = minute;

                if ((hour == 0) || (hour == 24)) {
                    hour = 12;
                    mPickEndTime.setText(String.valueOf(hour) + ":" + min + " a.m.");
                } else if (hour < 12) {
                    mPickEndTime.setText(String.valueOf(hour) + ":" + min + " a.m.");
                } else if (hour == 12) {
                    mPickEndTime.setText(String.valueOf(hour) + ":" + min + " p.m.");
                } else {
                    hour = hour - 12;
                    mPickEndTime.setText(String.valueOf(hour) + ":" + min + " p.m.");
                }

            }

        }

    }

}
