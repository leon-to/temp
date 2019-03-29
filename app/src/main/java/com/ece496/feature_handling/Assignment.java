package com.ece496.feature_handling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

import com.ece496.R;
import com.ece496.assignments.Essay;
import com.ece496.assignments.Exam;
import com.ece496.assignments.Presentation;
import com.ece496.assignments.CustomAssignment;

public class Assignment extends AppCompatActivity {

    public Assignment() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        Button button = (Button) findViewById(R.id.button_back);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button button1 = (Button) findViewById(R.id.button_essay);
        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Assignment.this, Essay.class);
                startActivity(i);
            }
        });

        Button button2 = (Button) findViewById(R.id.button_exam);
        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Assignment.this, Exam.class);
                startActivity(i);
            }
        });

        Button button3 = (Button) findViewById(R.id.button_custom_assignment);
        button3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Assignment.this, CustomAssignment.class);
                startActivity(i);
            }
        });

        Button button4 = (Button) findViewById(R.id.button_presentation);
        button4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Assignment.this, Presentation.class);
                startActivity(i);
            }
        });
    }
}