package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.Chat.Chat;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter  extends  RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    private List<student> students;
    private final Context context;


    public UserAdapter(List<student> students, Context context) {
        this.students = students;
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_profile,parent,false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        student list1 = students.get(position);
        if (list1 == null){
            return;
        }
        holder.tvTen.setText(list1.getName());
        holder.lastMess.setText(list1.getLastMessage());

        if(list1.getUnseenMessages()==0){
            holder.unseenMess.setVisibility(View.GONE);
            holder.lastMess.setTextColor(Color.parseColor("#959595"));
        }else{
            holder.unseenMess.setVisibility(View.VISIBLE);
            holder.unseenMess.setText(list1.getUnseenMessages()+"");
            holder.lastMess.setTextColor(context.getResources().getColor(R.color.black));
        }
        holder.rootLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(context, Chat.class);
                intent.putExtra("user",list1.getUser());
                intent.putExtra("name",list1.getName());
                intent.putExtra("picture",list1.getPicture());
                intent.putExtra("chat_key",list1.getChatKey());
                context.startActivity(intent);
            }
        });
    }
    public void update (List<student> students){
        this.students =students;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if(students != null){
            return students.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTen;
        private TextView lastMess;
        private TextView unseenMess;
        private CircleImageView tvimage;
        private LinearLayout rootLn;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTen = itemView.findViewById(R.id.tv_ten);
            lastMess = itemView.findViewById(R.id.lastMessage);
            unseenMess = itemView.findViewById(R.id.unseenMessages);
            tvimage =itemView.findViewById(R.id.tv_imageProfile);
            rootLn =itemView.findViewById(R.id.member);
        }
    }
}
