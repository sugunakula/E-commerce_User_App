package com.codr.bgmiuserapp.Orders;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.codr.bgmiuserapp.R;
import com.codr.bgmiuserapp.Tickets.Match_Data;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Order_Adapter extends RecyclerView.Adapter<Order_Adapter.matchviewAdapter> {
    private Context context;
    private List<Match_Data> list;

    public Order_Adapter(Context context, List<Match_Data> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public matchviewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (context). inflate (R.layout.order_layout, parent,false);
        return new matchviewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull matchviewAdapter holder, int position) {
        Match_Data currentItem = list.get(position);
        holder.matchTime.setText("Match Time:"+currentItem.getMatchTime());
        holder.matchDate.setText("Match Date:"+currentItem.getMatchDate());
        holder.uploadTime.setText("Upload Time:"+currentItem.getUploadTime());
        holder. uploadDate.setText("Upload Date:"+currentItem.getUploadDate());
        holder.referenceID.setText("Ref Id:"+currentItem.getReferId());
        holder.price.setText ("Price:"+currentItem.getMatchCharge());
        holder.matchDuration.setText ("Match Duration:"+currentItem.getMatchDuration());
        holder.matchCategory.setText ("Match Category:"+currentItem.getMatchCategory());
        holder.reward.setText("Reward:"+currentItem.getReward());

        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to delete?");
                builder.setCancelable(true);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth auth;
                        FirebaseUser user;
                        DatabaseReference reference;
                        auth=FirebaseAuth.getInstance();
                        user=auth.getCurrentUser();

                        reference= FirebaseDatabase.getInstance().getReference().child(("Orders")).child(user.getUid()).child(currentItem.getReferId());
                        reference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(view.getContext(),"Deleted",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(view.getContext(),"Something Went Wrong",Toast.LENGTH_SHORT).show();
                            }
                        });
                        notifyItemRemoved(holder.getAdapterPosition());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog=null;
                try {

                    dialog = builder.create();
                }catch (Exception e){
                    e.printStackTrace();
                }
                if (dialog!=null){
                    builder.show();
                }
            }
        });

        holder.chooseSquadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,Choose_Squad_Activity.class);
                intent.putExtra("ref_no",currentItem.getReferId());
                context.startActivity(intent);
            }
        });

/*        holder.mTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(), Buy_Ticket_Activity.class);
                intent.putExtra("UT",currentItem.getUploadTime());
                intent.putExtra("UD",currentItem.getUploadDate());
                intent.putExtra("MT",currentItem.getMatchTime());
                intent.putExtra("MD",currentItem.getMatchDate());
                intent.putExtra("MDuration",currentItem.getMatchDuration());
                intent.putExtra("MC",currentItem.getMatchCategory());
                intent.putExtra("Price",currentItem.getMatchCharge());
                intent.putExtra("RID",currentItem.getRoom_Id());
                intent.putExtra("RP",currentItem.getRoom_pass());
                intent.putExtra("ref_no",currentItem.getReferId());
                intent.putExtra("Reward",currentItem.getReward());
                context.startActivity(intent);
            }
        });*/

        DatabaseReference reference;
        reference= FirebaseDatabase.getInstance().getReference().child("Matches").child(currentItem.getMatchDuration()).child(currentItem.getReferId());

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String roomId=snapshot.child("room_Id").getValue(String.class);
                String roomPass=snapshot.child("room_pass").getValue(String.class);

                holder.RoomId.setText("Room ID: " + roomId);
                holder.RoomPass.setText("Room Pass: " + roomPass);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class matchviewAdapter extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView uploadDate, uploadTime, referenceID, RoomId, RoomPass, price, matchDuration, matchCategory,reward, matchDate,matchTime;
        private Button chooseSquadBtn,removeBtn;
  //      private CardView mTickets;

        public matchviewAdapter(@NonNull View itemView) {
            super(itemView);
            uploadDate=itemView.findViewById (R.id.uploadDate);
            uploadTime=itemView.findViewById(R.id.uploadTime);
            referenceID=itemView.findViewById(R.id.referenceID);
            RoomId=itemView.findViewById(R.id.roomID);
            RoomPass=itemView.findViewById(R.id.roomPass);
            price=itemView.findViewById(R.id.price);
            matchDuration=itemView.findViewById(R.id.matchDuration);
            matchCategory=itemView.findViewById(R.id.matchCategory);
            reward=itemView.findViewById(R.id.reward);
            matchDate=itemView.findViewById(R.id.matchDate);
            matchTime=itemView.findViewById(R.id.matchTime);
            chooseSquadBtn=itemView.findViewById(R.id.choosseSquadBtn);
            removeBtn=itemView.findViewById(R.id.removeBtn);
        }
    }
}
