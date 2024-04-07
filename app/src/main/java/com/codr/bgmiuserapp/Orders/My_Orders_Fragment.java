package com.codr.bgmiuserapp.Orders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.codr.bgmiuserapp.R;
import com.codr.bgmiuserapp.Tickets.Match_Adapter;
import com.codr.bgmiuserapp.Tickets.Match_Data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class My_Orders_Fragment extends Fragment {
    private RecyclerView orderRecycler;
    private ProgressBar progresssBar;
    private ArrayList<Match_Data> list;
    private Order_Adapter adapter;
    private DatabaseReference reference;
    public FirebaseAuth auth;
    public FirebaseUser user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my__orders_, container, false);

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();

        orderRecycler=view.findViewById(R.id.orderRecycler);
        progresssBar=view.findViewById(R.id.progresssbar);
        reference= FirebaseDatabase.getInstance().getReference().child("Orders").child(user.getUid());
        orderRecycler.setLayoutManager (new LinearLayoutManager(getContext()));
        orderRecycler.setHasFixedSize(true);
        getData();
        return view;
    }

    private void getData() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list=new ArrayList<>();
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    Match_Data data=snapshot1.getValue(Match_Data.class);
                    list.add(0,data);
                }
                adapter=new Order_Adapter(getContext(),list);
                adapter.notifyDataSetChanged();
                progresssBar.setVisibility(View.GONE);
                orderRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "No data found"+error.getMessage(), Toast.LENGTH_SHORT).show();
                progresssBar.setVisibility(View.GONE);
            }
        });
    }

}