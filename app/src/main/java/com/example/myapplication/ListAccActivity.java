package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Toast;

import com.example.myapplication.Chat.MemoryData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListAccActivity extends AppCompatActivity {

    private UserAdapter mUserAdapter;
    private final List<student> mListUser = new ArrayList<>();
    private RecyclerView recyclerView;
    private String users;
    private String chatKey="";
    private String lastMessage="";
    private int unseenMessages=0;
    private boolean dataSet = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_acc);

        init();
        getListUserRealtimeDatabase();

        users = getIntent().getStringExtra("user");

    }

    private void init(){

        recyclerView = findViewById(R.id.rcvUser);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        mUserAdapter = new UserAdapter(mListUser,ListAccActivity.this);

        recyclerView.setAdapter(mUserAdapter);

    }

    private void getListUserRealtimeDatabase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Member");

       myRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               mListUser.clear();
               unseenMessages = 0;
               lastMessage = "";
               chatKey = "";
               for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                   final String getUser = dataSnapshot.getKey();
                   dataSet = false;

                   if (!getUser.equals(users)) {
                       final String getName =dataSnapshot.child("name").getValue(String.class);
                       final String getPicture =dataSnapshot.child("picture").getValue(String.class);

                       /**StudentM studentM = new StudentM();
                       studentM.name = dataSnapshot.child("name").getValue(String.class);
                       studentM.user = dataSnapshot.child("user").getValue(String.class);
                       studentM.phone = dataSnapshot.child("phone").getValue(String.class);*/

                       myRef.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot snapshot) {
                               int getChatCounts = (int)snapshot.getChildrenCount();
                               if(getChatCounts>0){
                                   for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                                       final String getKey = dataSnapshot1.getKey();
                                       chatKey=getKey;

                                       if (dataSnapshot1.hasChild("user_1")&& dataSnapshot1.hasChild("user_2")&& dataSnapshot1.hasChild("messages")) {
                                           final String getUserOne = dataSnapshot1.child("user_1").getValue(String.class);
                                           final String getUserTwo = dataSnapshot1.child("user_2").getValue(String.class);

                                           //check dung 2 user moi lay
                                           if (getUserOne.equals(getUser) && getUserTwo.equals(users) || getUserOne.equals(users) && getUserTwo.equals(getUser)) {
                                               for (DataSnapshot chatDataSnapshot : dataSnapshot1.child("messages").getChildren()) {
                                                   final long getMessageKey = Long.parseLong(chatDataSnapshot.getKey());
                                                   final long getLastSeenMessage = Long.parseLong(MemoryData.getLastMsgTS(ListAccActivity.this, getKey));

                                                    //lay tn ve
                                                   lastMessage = chatDataSnapshot.child("mgs").getValue(String.class);
                                                   if (getMessageKey > getLastSeenMessage) {
                                                       unseenMessages++;
                                                   }
                                               }
                                           }
                                       }

                                   }
                               }
                               if (!dataSet){
                                   dataSet = true;
                                   student list1 = new student(getUser,"","",getName,"",getPicture,lastMessage,unseenMessages,chatKey);
                                   mListUser.add(list1);
                                   mUserAdapter.update(mListUser);

                               }



                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError error) {

                           }
                       });

                   }
               }


           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               Toast.makeText(getApplicationContext(),"get list failed",Toast.LENGTH_LONG).show();
           }
       });




    }
}