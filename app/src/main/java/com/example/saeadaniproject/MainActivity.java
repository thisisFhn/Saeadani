package com.example.saeadaniproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    Button buttonLogin;
    TextView Signuptext;
    TextView clientlogin;


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
        setContentView(R.layout.activity_main);

        //login logic

        editTextEmail = findViewById(R.id.editTextEmailClient);
        editTextPassword = findViewById(R.id.editTextPassClient);
        buttonLogin = findViewById(R.id.loginbuttonClient);
        Signuptext = (TextView) findViewById(R.id.signuptextView);
        clientlogin = (TextView) findViewById(R.id.clientlogintextView);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateUsername() | !validatePassword()) {

                } else {
                    checkUser();
                }

                Toast.makeText(MainActivity.this, "Logging in...", Toast.LENGTH_SHORT).show();
            }
        });

        Signuptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Signup.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Sign Up clicked", Toast.LENGTH_SHORT).show();
            }
        });

        clientlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ClientLogin.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Sign Up clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public Boolean validateUsername() {
        String val = editTextEmail.getText().toString();
        if (val.isEmpty()) {
            editTextEmail.setError("Username cannot be empty");
            return false;
        } else {
            editTextEmail.setError(null);
            return true;
        }
    }

    public Boolean validatePassword(){
        String val = editTextPassword.getText().toString();
        if (val.isEmpty()) {
            editTextPassword.setError("Password cannot be empty");
            return false;
        } else {
            editTextPassword.setError(null);
            return true;
        }
    }

    public void checkUser(){
        String userUsername = editTextEmail.getText().toString().trim();
        String userPassword = editTextPassword.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByValue();

        checkUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    for(DataSnapshot abc: snapshot.getChildren()){

                    }
                    editTextEmail.setError(null);
                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);

                    if (passwordFromDB.equals(userPassword)) {
                        editTextEmail.setError(null);

                        String nameFromDB = snapshot.child(userUsername).child("name").getValue(String.class);
                        String emailFromDB = snapshot.child(userUsername).child("email").getValue(String.class);
                        //String usernameFromDB = snapshot.child(userUsername).child("username").getValue(String.class);

                        Intent intent = new Intent(MainActivity.this, JobsPage.class);

                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("email", emailFromDB);
                        //intent.putExtra("username", usernameFromDB);
                        intent.putExtra("password", passwordFromDB);

                        startActivity(intent);
                    } else {
                        editTextPassword.setError("Invalid Credentials");
                        editTextPassword.requestFocus();
                    }
                } else {
                    editTextEmail.setError("User does not exist");
                    editTextEmail.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}