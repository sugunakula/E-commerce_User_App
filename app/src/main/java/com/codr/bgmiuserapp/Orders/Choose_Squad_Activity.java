package com.codr.bgmiuserapp.Orders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codr.bgmiuserapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Choose_Squad_Activity extends AppCompatActivity {

    private String referenceID;
    private EditText player1ID,player2ID,player3ID,player4ID,player1N,player2N,player3N,player4N;
    private Button joinBtn;
    public DatabaseReference reference1,reference2,reference3;
    public FirebaseAuth auth;
    public FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_squad);

        referenceID=getIntent().getStringExtra("ref_no");
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        player1ID=findViewById(R.id.player1ID);
        player2ID=findViewById(R.id.player2ID);
        player3ID=findViewById(R.id.player3ID);
        player4ID=findViewById(R.id.player4ID);
        player1N=findViewById(R.id.player1N);
        player2N=findViewById(R.id.player2N);
        player3N=findViewById(R.id.player3N);
        player4N=findViewById(R.id.player4N);
        joinBtn=findViewById(R.id.joinBtn);

        reference1= FirebaseDatabase.getInstance().getReference().child("TotalOrders");

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                joinSquad();
            }
        });
    }

    private void joinSquad() {
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap hp= new HashMap();
                hp.put("p1ID",player1ID.getText().toString());
                hp.put("p2ID",player2ID.getText().toString());
                hp.put("p3ID",player3ID.getText().toString());
                hp.put("p4ID",player4ID.getText().toString());
                hp.put("p1N",player1N.getText().toString());
                hp.put("p2N",player2N.getText().toString());
                hp.put("p3N",player3N.getText().toString());
                hp.put("p4N",player4N.getText().toString());

                reference1.child(referenceID).child(user.getUid()).updateChildren(hp).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(Choose_Squad_Activity.this,"Success",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}