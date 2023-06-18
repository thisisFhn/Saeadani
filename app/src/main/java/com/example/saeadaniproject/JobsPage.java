package com.example.saeadaniproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import kotlinx.coroutines.Job;

public class JobsPage extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Exit Application?");
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.show();
        return;
    }

    CardView jobscard1, jobscard2, jobscard3, jobscard4, jobscard5, jobscard6;
    Button logoutjobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_page);

        jobscard1 = (CardView) findViewById(R.id.jobscard1);
        jobscard2 = (CardView) findViewById(R.id.jobscard2);
        jobscard3 = (CardView) findViewById(R.id.jobscard3);
        jobscard4 = (CardView) findViewById(R.id.jobscard4);
        jobscard5 = (CardView) findViewById(R.id.jobscard5);
        jobscard6 = (CardView) findViewById(R.id.jobscard6);
        logoutjobs = (Button) findViewById(R.id.logoutjobs);

        jobscard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(JobsPage.this, "Applied for this job.", Toast.LENGTH_SHORT).show();
            }
        });

        jobscard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(JobsPage.this, "Applied for this job.", Toast.LENGTH_SHORT).show();
            }
        });

        jobscard3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(JobsPage.this, "Applied for this job.", Toast.LENGTH_SHORT).show();
            }
        });

        jobscard4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(JobsPage.this, "Applied for this job.", Toast.LENGTH_SHORT).show();
            }
        });

        jobscard5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(JobsPage.this, "Applied for this job.", Toast.LENGTH_SHORT).show();
            }
        });

        jobscard6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(JobsPage.this, "Applied for this job.", Toast.LENGTH_SHORT).show();
            }
        });

        logoutjobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobsPage.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(JobsPage.this, "Logout Successful.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}