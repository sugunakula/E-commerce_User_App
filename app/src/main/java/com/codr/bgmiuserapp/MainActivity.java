package com.codr.bgmiuserapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.codr.bgmiuserapp.Orders.My_Orders_Fragment;
import com.codr.bgmiuserapp.Tickets.Aftternoon_Fragment;
import com.codr.bgmiuserapp.Tickets.Evening_Fragment;
import com.codr.bgmiuserapp.Tickets.Morning_Fragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public FirebaseAuth auth;
    public FirebaseUser user;
    private View header;
    private NavigationView navView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private CardView morningCard,afternoonCard,eveningCard,myOrdersCard;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        navView = findViewById(R.id.navView);
        toolbar=findViewById(R.id.toolbar);
        drawerLayout=findViewById(R.id.navDrawer);
        morningCard=findViewById(R.id.morningCard);
        morningCard.setOnClickListener(this);
        afternoonCard=findViewById(R.id.afternoonCard);
        afternoonCard.setOnClickListener(this);
        eveningCard=findViewById(R.id.eveningCard);
        eveningCard.setOnClickListener(this);
        myOrdersCard=findViewById(R.id.my_OrdersCard);
        myOrdersCard.setOnClickListener(this);

        fragmentManager=getSupportFragmentManager();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toggle=new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navView.bringToFront();

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.profile:
                        Toast.makeText(MainActivity.this,"profile",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.myOrders:
                        Toast.makeText(MainActivity.this,"my orders",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.support:
                        Toast.makeText(MainActivity.this,"help",Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });



        header=navView.getHeaderView(0);
        if (user != null) {
            TextView usrName = header.findViewById(R.id.userName);
            usrName.setText(user.getDisplayName());

            TextView usrEmail = header.findViewById(R.id.userEmail);
            usrEmail.setText(user.getEmail());

            Button logout = header.findViewById(R.id.button);
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    auth.signOut();
                    startActivity(new Intent(MainActivity.this, Login_Acitvity.class));
                    finish();
                }
            });
        } else {
            startActivity(new Intent(MainActivity.this, Login_Acitvity.class));
            finish();
        }
    }



    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.morningCard:
                Morning_Fragment morning_fragment=new Morning_Fragment();
                fragmentManager.beginTransaction().replace(R.id.frame_layout,morning_fragment).addToBackStack("home").commit();
                break;
            case R.id.afternoonCard:
                Aftternoon_Fragment aftternoon_fragment=new Aftternoon_Fragment();
                fragmentManager.beginTransaction().replace(R.id.frame_layout,aftternoon_fragment).addToBackStack("home").commit();
                break;
            case R.id.eveningCard:
                Evening_Fragment evening_fragment=new Evening_Fragment();
                fragmentManager.beginTransaction().replace(R.id.frame_layout,evening_fragment).addToBackStack("home").commit();
                break;
            case R.id.my_OrdersCard:
                My_Orders_Fragment my_orders_fragment=new My_Orders_Fragment();
                fragmentManager.beginTransaction().replace(R.id.frame_layout,my_orders_fragment).addToBackStack("home").commit();
                break;
        }
    }
}