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
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_Acitvity extends AppCompatActivity {
    private EditText forgotEmail;
    private TextView loginTxtbox,registerTxtbox;
    private Button submitbtn;
    public FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_acitvity);
        forgotEmail=findViewById(R.id.editTextEmail);
        loginTxtbox=findViewById(R.id.login);
        submitbtn=findViewById(R.id.buttonResetPassword);
        auth=FirebaseAuth.getInstance();

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (forgotEmail.getText().toString().isEmpty()){
                    Toast.makeText(Forgot_Acitvity.this,"Please enter your email",Toast.LENGTH_SHORT).show();
                }else{
                    sendEmail();
                }
            }
        });
    }

    private void sendEmail() {
        auth.sendPasswordResetEmail(forgotEmail.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Forgot_Acitvity.this,"Please check your email",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Forgot_Acitvity.this,Login_Acitvity.class));
                            finish();
                        }
                    }
                });

    }
}