package com.ece496.assignments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ece496.MainActivity;
import com.ece496.FrontEndEvent;
import com.ece496.R;

import java.util.ArrayList;
import java.util.Calendar;

public class Essay extends AppCompatActivity {

    public static final int EASY = 0;
    public static final int MODERATE = 1;
    public static final int DIFFICULT = 2;

    public static final int RESEARCH_REQUIRED = 0;
    public static final int RESEARCH_NOT_REQUIRED = 1;


    static Button button_due_date;
    static Button button_due_time;

    public static EditText text_input_1_title;
    public static EditText integer_input1_pages;
    public static EditText integer_input2_pages_reading;
    public static CheckBox checkbox_research;
    public static int difficulty;
    public static TextView errorText;

    public static int due_year;
    public static int due_month;
    public static int due_day;
    public static int due_hour;
    public static int due_minute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essay);

        final Calendar c = Calendar.getInstance();

        button_due_date = (Button) findViewById(R.id.user_input_due_date_essay);
        button_due_time = (Button) findViewById(R.id.user_input_due_time_essay);
        formatButtonDate(c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH)+1, c.get(Calendar.YEAR), button_due_time);
        formatButtonTime(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), button_due_time);
        due_year = c.get(Calendar.YEAR);
        due_month = c.get(Calendar.MONTH)+1;
        due_day = c.get(Calendar.DAY_OF_MONTH);
        due_hour = c.get(Calendar.HOUR_OF_DAY);
        due_minute = c.get(Calendar.MINUTE);

        text_input_1_title = (EditText) findViewById(R.id.user_input_essay_title);
        integer_input1_pages = (EditText) findViewById(R.id.user_input_essay_pages);
        integer_input2_pages_reading = (EditText) findViewById(R.id.user_input_essay_pages_reading);
        checkbox_research = (CheckBox)  findViewById(R.id.user_input_essay_research);

        difficulty = 0;
        errorText = (TextView) findViewById(R.id.output_error_essay);
        errorText.setVisibility(View.INVISIBLE);

        button_due_date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // showDialog(DATE_DIALOG_ID);
                DialogFragment newFragment = new Essay.DatePickerFragmentEssay();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        button_due_time.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // showDialog(DATE_DIALOG_ID);
                DialogFragment newFragment = new Essay.TimePickerFragmentEssay();
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        Button button_back = (Button) findViewById(R.id.cancel_essay);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        Button button_done = (Button) findViewById(R.id.submit_essay);
        button_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

        Spinner difficulty_spinner = (Spinner) findViewById(R.id.user_input_essay_difficulty_spinner);

        difficulty_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                difficulty = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public static class DatePickerFragmentEssay extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            due_day = day;
            due_month = month +1;
            due_year=year;
            formatButtonDatePicker(day, month+1, year, button_due_date);
        }

        public void formatButtonDatePicker(int day, int month, int year, Button button) {
            button_due_date.setText(String.valueOf(day) + "/" + String.valueOf(month) + "/" + String.valueOf(year));
        }

    }

    public static class TimePickerFragmentEssay extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

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
        button_due_date.setText(String.valueOf(day) + "/" + String.valueOf(month) + "/" + String.valueOf(year));
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

        int research = RESEARCH_NOT_REQUIRED;

        if (checkbox_research.isChecked()) {
            research = RESEARCH_REQUIRED;
        }

        int pages_to_write = 0;
        int pages_reading = 0;

        if (integer_input1_pages.getEditableText().toString().equals("")) {
            pages_to_write = 0;
        } else {
            pages_to_write = (int) Long.parseLong(integer_input1_pages.getEditableText().toString());
        }


        if (integer_input2_pages_reading.getEditableText().toString().equals("")) {
            pages_reading = 0;
        } else {
            pages_reading = (int) Long.parseLong(integer_input2_pages_reading.getEditableText().toString());
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

        String title2 = title.replaceAll("\\s", "");

        if (title2.equals("")) {

            errorText.setText("*  essay identifier required *");
            errorText.setVisibility(View.VISIBLE);

        } else {

            if (pages_to_write <= 0) {
                errorText.setText("*  essay must atleast be 1 page *");
                errorText.setVisibility(View.VISIBLE);
            } else {

                if (duration <= 0) {
                    errorText.setText("*  essay due date must be in the future *");
                    errorText.setVisibility(View.VISIBLE);
                } else {

                    String due_string = pad_string(due_year, 1000) + " " + pad_string(due_month, 10) + " "
                            + pad_string(due_day, 10) + " " + pad_string(due_hour, 10) + " "
                            + pad_string(due_minute, 10);

                    essay(title, pages_to_write, difficulty, research, pages_reading, due_string);
                    Intent i = new Intent(Essay.this, MainActivity.class);
                    startActivity(i);

                }

            }

        }
    }









    /******************************************************************************************************************************************************************************************************
     * ALL CODE BEYOND THIS POINT is specific to the calculation of essay parameters
     * no point using this as a template for other things
     *******************************************************************************************************************************************************************************************************/

    public static int round_nearest_30(double n) {
        int num = (int) (n);
        int mod = num % 30;
        int rounded = num;

        if (mod >= 15) {
            rounded = num + (30 - mod);
        } else {
            rounded = num - mod;
        }
        return rounded;
    }

    public static ArrayList<FrontEndEvent> make_events(ArrayList<FrontEndEvent> objectList, String title,
                                                       String description, String start_time, String end_time, double totaltime, String freq_start_time,
                                                       String freq_end_time, int freq, String due_date) {

        int duration = 0;
        int total_time = (int) (totaltime);

        while (total_time > 0) {

            duration = 0;

            if (total_time >= 120) {
                duration = 120;
            } else if (total_time >= 90) {
                duration = 90;
            } else if (total_time >= 60) {
                duration = 60;
            } else if (total_time >= 30) {
                duration = 30;
            }

            FrontEndEvent temp = new FrontEndEvent(title, description, start_time, end_time, duration,
                    freq_start_time, freq_end_time, freq, due_date);
            objectList.add(temp);

            total_time = total_time - duration;

        }

        return objectList;
    }


    public static void essay(String essay_title, int number_of_pages, int difficulty, int research_needed,
                             int readings_needed, String due_date) {

        double unit_pages;

        switch (difficulty) {
            case EASY:
                unit_pages = number_of_pages;
                break;
            case MODERATE:
                unit_pages = number_of_pages * 1.25;
                break;
            case DIFFICULT:
                unit_pages = number_of_pages * 1.5;
                break;
            default:
                unit_pages = 0;
                break;
        }

        double outline;
        outline = (number_of_pages * 12) + (unit_pages - number_of_pages) * 5;

        double research = 0;
        double referencing = number_of_pages * 5;

        if (research_needed == RESEARCH_REQUIRED) {
            research = (number_of_pages * 45) + (unit_pages - number_of_pages) * 15;

        } else {
            if (outline > 120) {
                double difference = outline - 120;
                outline = 120 + difference * 0.25;
            }
        }

        double readings = readings_needed * 2;
        double prep_time = 30 + readings;
        double first_draft = (number_of_pages * 30) + (unit_pages - number_of_pages) * 10;
        double edits = (number_of_pages * 10) + (unit_pages - number_of_pages) * 5;
        double final_draft = (number_of_pages * 15) + (unit_pages - number_of_pages) * 5;

        prep_time = round_nearest_30(prep_time);
        outline = round_nearest_30(outline);
        research = round_nearest_30(research);
        first_draft = round_nearest_30(first_draft);
        edits = round_nearest_30(edits);
        final_draft = round_nearest_30(final_draft);
        referencing = round_nearest_30(referencing);

        System.out.println("");
        System.out.println(essay_title);
        System.out.printf("  number of pages: %d\n", number_of_pages);

        switch (difficulty) {
            case EASY:
                System.out.printf("  difficulty: easy\n");
                break;
            case MODERATE:
                System.out.printf("  difficulty: moderate\n");
                break;
            case DIFFICULT:
                System.out.printf("  difficulty: difficult\n");
                break;
            default:
                unit_pages = 0;
                break;
        }

        /* System.out.printf("  unit number of pages: %.0f\n", unit_pages); */

        ArrayList<FrontEndEvent> objectsList = new ArrayList<>();
        String title = "";
        String description = "";
        String start_time = "0000 00 00 00 00";
        String end_time = "0000 00 00 00 00";
        String freq_start_time = "0000 00 00 00 00";
        String freq_end_time = "0000 00 00 00 00";
        int freq = 0;

        if (readings_needed > 0) {
            title = "Essay: " + essay_title + ", Pre-Writing Preparation Time";
            description = "For brainstorming and completing required readings";
            objectsList = make_events(objectsList, title, description, start_time, end_time, prep_time,
                    freq_start_time, freq_end_time, freq, due_date);
        } else {
            title = "Essay: " + essay_title + ", Pre-Writing Preparation Time";
            description = "For brainstorming";
            objectsList = make_events(objectsList, title, description, start_time, end_time, prep_time,
                    freq_start_time, freq_end_time, freq, due_date);
        }

        if (research_needed == RESEARCH_REQUIRED) {
            title = "Essay: " + essay_title + ", Initial Research and Outlining";
            description = "";
            objectsList = make_events(objectsList, title, description, start_time, end_time, outline,
                    freq_start_time, freq_end_time, freq, due_date);

        } else {
            title = "Essay: " + essay_title + ", Outlining";
            description = "";
            objectsList = make_events(objectsList, title, description, start_time, end_time, outline,
                    freq_start_time, freq_end_time, freq, due_date);
        }

        if (research_needed == RESEARCH_REQUIRED) {
            title = "Essay: " + essay_title + ", Research";
            description = "";
            objectsList = make_events(objectsList, title, description, start_time, end_time, research,
                    freq_start_time, freq_end_time, freq, due_date);
        }

        title = "Essay: " + essay_title + ", Write First Draft";
        description = "";
        objectsList = make_events(objectsList, title, description, start_time, end_time, first_draft,
                freq_start_time, freq_end_time, freq, due_date);

        title = "Essay: " + essay_title + ", Edit and Revise";
        description = "";
        objectsList = make_events(objectsList, title, description, start_time, end_time, edits, freq_start_time,
                freq_end_time, freq, due_date);

        title = "Essay: " + essay_title + ", Polish Final Draft";
        description = "";
        objectsList = make_events(objectsList, title, description, start_time, end_time, final_draft,
                freq_start_time, freq_end_time, freq, due_date);

        title = "Essay: " + essay_title + ", Referencing";
        description = "";
        objectsList = make_events(objectsList, title, description, start_time, end_time, referencing,
                freq_start_time, freq_end_time, freq, due_date);

        for (int x = 0; x < objectsList.size(); x++) {
            objectsList.get(x).print_details();
        }

        int total = (int) (prep_time + outline + research + first_draft + final_draft + edits + referencing);
        System.out.printf("  total: %d mins - %d hours\n", total, total / 60);
    }

}


