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
import com.google.firebase.FirebaseApp;

public class Login_Acitvity extends AppCompatActivity {
    private EditText loginEmail,loginPassword;
    private Button loginBtn;
    private TextView registerTxtBox,forgotTxtBox;
    public FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acitvity);
        loginBtn=findViewById(R.id.login_button);
        loginEmail=findViewById(R.id.login_email);
        loginPassword=findViewById(R.id.login_password);
        registerTxtBox=findViewById(R.id.signupRedirectText);
        forgotTxtBox=findViewById(R.id.forgotPass);
        auth=FirebaseAuth.getInstance();

        registerTxtBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login_Acitvity.this,Register_Activity.class));
                finish();
            }
        });

        forgotTxtBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login_Acitvity.this,Forgot_Acitvity.class));
                finish();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();
            }
        });
    }

    private void checkValidation() {
        if(loginEmail.getText().toString().isEmpty()||loginPassword.getText().toString().isEmpty()){
            Toast.makeText( this, "Please fill all the forms", Toast.LENGTH_SHORT).show();
        }else{
            loginUser();
        }
    }

    private void loginUser() {
        auth.signInWithEmailAndPassword(loginEmail.getText().toString(), loginPassword.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText( Login_Acitvity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login_Acitvity.this,MainActivity.class));
                            finish();
                        }
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser()!= null){
            Intent intent = new Intent(Login_Acitvity.this,MainActivity.class);
            finish();
        }
    }
}