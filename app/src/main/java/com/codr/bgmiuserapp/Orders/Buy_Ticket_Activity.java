package com.codr.bgmiuserapp.Orders;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.Printer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.codr.bgmiuserapp.R;
import com.codr.bgmiuserapp.Tickets.Match_Data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class Buy_Ticket_Activity extends AppCompatActivity implements PaymentResultListener{

    private TextView uploadDate, uploadTime, referenceID, RoomId, RoomPass, price, matchDuration, matchCategory,reward, matchDate,matchTime;
    private Button payBtn;
    private String UT,UD,MD,MT,MDuration,ref_no,RP,RID,MC,Price,Reward;
    public FirebaseAuth auth;
    public FirebaseUser user;
    private DatabaseReference reference1, reference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Checkout.preload(getApplicationContext());
        setContentView(R.layout.activity_buy_ticket);

        reference1= FirebaseDatabase.getInstance().getReference().child("Orders");
        reference2= FirebaseDatabase.getInstance().getReference().child("TotalOrders");

        auth=FirebaseAuth.getInstance();
        user= auth.getCurrentUser();
        uploadDate=findViewById(R.id.uploadDate);
        uploadTime=findViewById(R.id.uploadTime);
        referenceID=findViewById(R.id.referenceID);
        RoomId=findViewById(R.id.roomID);
        RoomPass=findViewById(R.id.roomPass);
        price=findViewById(R.id.price);
        matchDuration=findViewById(R.id.matchDuration);
        matchCategory=findViewById(R.id.matchCategory);
        reward=findViewById(R.id.reward);
        matchDate=findViewById(R.id.matchDate);
        matchTime=findViewById(R.id.matchTime);
        payBtn=findViewById(R.id.payyBtn);

        UT=getIntent().getStringExtra("UT");
        UD=getIntent().getStringExtra("UD");
        MD=getIntent().getStringExtra("MD");
        MT=getIntent().getStringExtra("MT");
        MDuration=getIntent().getStringExtra("MDuration");
        ref_no=getIntent().getStringExtra("ref_no");
        RP=getIntent().getStringExtra("RP");
        RID=getIntent().getStringExtra("RID");
        MC=getIntent().getStringExtra("MC");
        Price=getIntent().getStringExtra("Price");
        Reward=getIntent().getStringExtra("Reward");

        uploadTime.setText("Upload Time: "+UT);
        uploadDate.setText("Upload Date: "+UD);
        matchTime.setText(MT);
        matchDate.setText(MD);
        matchDuration.setText(MDuration);
        matchCategory.setText(MC);
        RoomId.setText("Room ID: "+RID);
        RoomPass.setText("Room Pass: "+RP);
        reward.setText("Prize pool: "+Reward);
        referenceID.setText("Ref ID: "+ref_no);
        price.setText(Price);


        payBtn.setText("Pay "+Price);

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePayment();
            }
        });
    }

    private void makePayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_3tDvF7pS5B74sS");

        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.cart);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "BGMI rooms");
            options.put("description", ref_no+matchCategory);
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
           // options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount",Integer.parseInt(Price)*100);//pass amount in currency subunits
            options.put("prefill.email", user.getEmail());
           // options.put("prefill.contact","9988776655");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(Buy_Ticket_Activity.this,"Successful",Toast.LENGTH_SHORT).show();
        saveOrder();
    }

    private void saveOrder() {

        Match_Data match_data=new Match_Data(UD,UT,ref_no,Price,"",MT,MD,MDuration,MC,RID,RP,Reward);
        reference1.child(user.getUid()).child(ref_no).setValue(match_data);

        Choose_Squad_Data choose_squad_data=new Choose_Squad_Data(user.getUid(),user.getDisplayName(),"","","","","","","","",ref_no);
        reference2.child(ref_no).child(user.getUid()).setValue(choose_squad_data);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(Buy_Ticket_Activity.this,"UnSuccessful",Toast.LENGTH_SHORT).show();

    }
}