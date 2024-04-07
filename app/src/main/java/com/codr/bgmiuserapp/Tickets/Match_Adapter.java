package com.codr.bgmiuserapp.Tickets;

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

import com.codr.bgmiuserapp.Orders.Buy_Ticket_Activity;
import com.codr.bgmiuserapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Match_Adapter extends RecyclerView.Adapter<Match_Adapter.matchviewAdapter> {
    private Context context;
    private List<Match_Data> list;

    public Match_Adapter(Context context, List<Match_Data> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public matchviewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (context). inflate (R.layout.match_layout, parent,false);
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
        holder.RoomId.setText("Room ID:"+currentItem.getRoom_Id());
        holder.RoomPass.setText("Room Pass:"+currentItem.getRoom_pass());
        holder.price.setText ("Price:"+currentItem.getMatchCharge());
        holder.matchDuration.setText ("Match Duration:"+currentItem.getMatchDuration());
        holder.matchCategory.setText ("Match Category:"+currentItem.getMatchCategory());
        holder.reward.setText("Reward:"+currentItem.getReward());

        holder.mTickets.setOnClickListener(new View.OnClickListener() {
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
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class matchviewAdapter extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView uploadDate, uploadTime, referenceID, RoomId, RoomPass, price, matchDuration, matchCategory,reward, matchDate,matchTime;
        private Button deleteBtn;
        private CardView mTickets;

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
            //deleteBtn=itemView.findViewById(R.id.payBtn);
            mTickets=itemView.findViewById(R.id.mTickets);
        }
    }
}
