package com.example.saeadaniproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Signup extends AppCompatActivity {

    EditText signupName, signupJob, signupContNum, signupEmail, signupPassword;
    Spinner signupspinner;
    Button signupbutton;
    TextView backtolog;
    FirebaseDatabase saeadanifb;
    DatabaseReference fbreference;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupName = findViewById(R.id.editTextNameSignup);
        signupJob = findViewById(R.id.editTextJobSignup);
        signupContNum = findViewById(R.id.editTextNumSignup);
        signupEmail = findViewById(R.id.editTextEmailClient);
        signupPassword = findViewById(R.id.editTextPassClient);
        signupspinner = (Spinner)findViewById(R.id.signupspinner);
        signupbutton = findViewById(R.id.signupbutton);
        backtolog = (TextView) findViewById(R.id.backtologintextview);

        ArrayList<String> List = new ArrayList<>();
        List.add("Freelancer");
        List.add("Client");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,List);
        signupspinner.setAdapter(adapter);

        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saeadanifb = FirebaseDatabase.getInstance();
                fbreference = saeadanifb.getReference("users");
                String name = String.valueOf(signupName.getText());
                String job = String.valueOf(signupJob.getText());
                String num = String.valueOf(signupContNum.getText());
                String email = String.valueOf(signupEmail.getText());
                String password = String.valueOf(signupPassword.getText());
                String role = String.valueOf(signupspinner.getSelectedItem());

                try {


                    if (name.isEmpty()) {
                        Toast.makeText(Signup.this, "Please Check Fields", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        saeadanifb = FirebaseDatabase.getInstance();
                        fbreference = saeadanifb.getReference("users");
                        fbreference.child(name).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (task.isSuccessful()){
                                    if(task.getResult().exists()){
                                        Toast.makeText(Signup.this, "User already exists", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        HelperClass helperClass = new HelperClass(name, job, num, email, password, role);
                                        saeadanifb = FirebaseDatabase.getInstance();
                                        fbreference = saeadanifb.getReference("users");
                                        fbreference.child(name).setValue(helperClass);
                                        Toast.makeText(Signup.this, "You have signup successfully!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Signup.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                }
                                else {
                                    Toast.makeText(Signup.this, "failed to connect to database", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
                catch (Exception e){
                    Toast.makeText(Signup.this, "Signup Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backtolog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(Signup.this, "Sign Up clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }
}