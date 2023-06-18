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

public class ClientLogin extends AppCompatActivity {


    EditText editTextEmailClient, editTextPassClient;
    Button loginbuttonClient;
    TextView Signuptext;



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
        setContentView(R.layout.activity_client_login);


        //login logic

        editTextEmailClient = findViewById(R.id.editTextEmailClient);
        editTextPassClient = findViewById(R.id.editTextPassClient);
        loginbuttonClient = findViewById(R.id.loginbuttonClient);
        Signuptext = (TextView) findViewById(R.id.signuptextView);

        loginbuttonClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateUsername() | !validatePassword()) {

                } else {
                    checkUser();
                }

                Toast.makeText(ClientLogin.this, "Logging in...", Toast.LENGTH_SHORT).show();
            }
        });

        Signuptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClientLogin.this, Signup.class);
                startActivity(intent);
                Toast.makeText(ClientLogin.this, "Sign Up clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public Boolean validateUsername() {
        String val = editTextEmailClient.getText().toString();
        if (val.isEmpty()) {
            editTextEmailClient.setError("Username cannot be empty");
            return false;
        } else {
            editTextEmailClient.setError(null);
            return true;
        }
    }

    public Boolean validatePassword(){
        String val = editTextPassClient.getText().toString();
        if (val.isEmpty()) {
            editTextPassClient.setError("Password cannot be empty");
            return false;
        } else {
            editTextPassClient.setError(null);
            return true;
        }
    }

    public void checkUser(){
        String userUsername = editTextEmailClient.getText().toString().trim();
        String userPassword = editTextPassClient.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByValue();

        checkUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    for(DataSnapshot abc: snapshot.getChildren()){

                    }
                    editTextEmailClient.setError(null);
                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);

                    if (passwordFromDB.equals(userPassword)) {
                        editTextEmailClient.setError(null);

                        String nameFromDB = snapshot.child(userUsername).child("name").getValue(String.class);
                        String emailFromDB = snapshot.child(userUsername).child("email").getValue(String.class);

                        Intent intent = new Intent(ClientLogin.this, ClientDashboard.class);

                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("password", passwordFromDB);

                        startActivity(intent);
                    } else {
                        editTextPassClient.setError("Invalid Credentials");
                        editTextPassClient.requestFocus();
                    }
                } else {
                    editTextEmailClient.setError("User does not exist");
                    editTextEmailClient.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}