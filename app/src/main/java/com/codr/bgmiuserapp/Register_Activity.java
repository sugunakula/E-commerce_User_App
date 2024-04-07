package com.codr.bgmiuserapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register_Activity extends AppCompatActivity {
    private EditText registerName, registerEmail, registerPassword;
    private TextView loginTxtbox;
    private Button registerBtn;
    public FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerName=findViewById(R.id.signup_name);
        registerEmail=findViewById(R.id.signup_email);
        registerPassword=findViewById(R.id.signup_password);
        loginTxtbox=findViewById(R.id.loginRedirectText);
        registerBtn=findViewById(R.id.signup_button);
        auth=FirebaseAuth.getInstance();


        loginTxtbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register_Activity.this,Login_Acitvity.class));
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidition();
            }
        });
    }

    private void checkValidition() {
        String name= registerName.getText().toString();
        String email= registerEmail.getText().toString();
        String password= registerPassword.getText().toString();
        if(name.isEmpty() ||email.isEmpty()||password.isEmpty()) {
            Toast.makeText( this, "Please fill all the forms", Toast.LENGTH_SHORT).show();
        }else {
            registerUser();
        }
    }

    private void registerUser() {
        auth.createUserWithEmailAndPassword(registerEmail.getText().toString(),registerPassword.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Register_Activity.this,"Registration Success",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(Register_Activity.this,"Registration Failed"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}