package com.example.saeadaniproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ClientDashboard extends AppCompatActivity {

    Button logoutbutton, savenewjobbtn;
    EditText jobtitle, jobdescription;

    FirebaseDatabase saeadanifb;
    DatabaseReference fbreference;

    AddNewJob addnewjob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_dashboard);

        jobtitle = (EditText) findViewById(R.id.newjobname);
        jobdescription = (EditText) findViewById(R.id.newdescription);
        logoutbutton = (Button) findViewById(R.id.logoutbuttonClient);
        savenewjobbtn = (Button) findViewById(R.id.savenewjobbtn);

        saeadanifb = FirebaseDatabase.getInstance();

        fbreference = saeadanifb.getReference("AddNewJob");

        addnewjob = new AddNewJob();

        savenewjobbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = jobtitle.getText().toString();
                String description = jobdescription.getText().toString();

                // below line is for checking whether the
                // edittext fields are empty or not.
                if (TextUtils.isEmpty(title) && TextUtils.isEmpty(description)) {
                    // if the text fields are empty
                    // then show the below message.
                    Toast.makeText(ClientDashboard.this, "Please add the data", Toast.LENGTH_SHORT).show();
                } else {
                    // else call the method to add
                    // data to our database.
                    addDatatoFirebase(title, description);
                }
            }

            private void addDatatoFirebase(String title, String description) {
                // below 3 lines of code is used to set
                // data in our object class.
                addnewjob.setjobtitle(title);
                addnewjob.setjobdescription(description);

                // we are use add value event listener method
                // which is called with database reference.
                fbreference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // inside the method of on Data change we are setting
                        // our object class to our database reference.
                        // data base reference will sends data to firebase.
                        fbreference.setValue(addnewjob);

                        // after adding this data we are showing toast message.
                        Toast.makeText(ClientDashboard.this, "Your request has been saved successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // if the data is not added or it is cancelled then
                        // we are displaying a failure toast message.
                        Toast.makeText(ClientDashboard.this, "Fail to send your request " + error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        logoutbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ClientDashboard.this, ClientLogin.class);
                startActivity(intent);
                Toast.makeText(ClientDashboard.this, "Logged out", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public class AddNewJob {

        // string variable for
        // storing new job title.
        String jobtitle;

        // string variable for
        // storing new job description.
        String jobdescription;

        public AddNewJob() {

        }


        // created getter and setter methods
        // for all our variables.
        public String getjobtitle() {
            return jobtitle;
        }

        public void setjobtitle(String jobtitle) {
            this.jobtitle = jobtitle;
        }

        public String getjobdescription() {
            return jobdescription;
        }

        public void setjobdescription(String jobdescription) {
            this.jobdescription = jobdescription;
        }
    }
}