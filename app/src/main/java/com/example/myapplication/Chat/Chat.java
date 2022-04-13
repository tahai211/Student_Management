package com.example.myapplication.Chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.ListAccActivity;
import com.example.myapplication.R;
import com.example.myapplication.student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://kmar-efe69-default-rtdb.firebaseio.com/");
    private String chatKey;
    private final List<ChatList> chatLists = new ArrayList<>();
    String getUserMbl = "";
    private RecyclerView chattingRecyclerView;
    private ChatAdapter chatAdapter;
    private boolean loadingFistTime = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        final ImageView backBtn = findViewById(R.id.backBtn);
        final TextView nameMess = findViewById(R.id.nameMess);
        final EditText messageEditText = findViewById(R.id.messigeEditText);
        final ImageView sendBtn = findViewById(R.id.send);
        final CircleImageView imgeprt = findViewById(R.id.imageProfileMess);


        chattingRecyclerView = findViewById(R.id.chattingRecyclerView);
        final String getName = getIntent().getStringExtra("name");
        final String getProfile = getIntent().getStringExtra("picture");
        final String getUser = getIntent().getStringExtra("user");
        chatKey = getIntent().getStringExtra("chat_key");


        getUserMbl = MemoryData.getData(Chat.this);
        nameMess.setText(getName);
        Picasso.get().load(getProfile).into(imgeprt);

        chattingRecyclerView.setHasFixedSize(true);
        chattingRecyclerView.setLayoutManager(new LinearLayoutManager(Chat.this));
        chattingRecyclerView.setAdapter(chatAdapter);
        chatAdapter = new ChatAdapter(chatLists,Chat.this);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (chatKey.isEmpty()) {
                    chatKey = "1";
                    if (snapshot.hasChild("chat")) {
                        chatKey = String.valueOf(snapshot.child("chat").getChildrenCount() + 1);

                    }
                    sendBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final String getTxtMessage = messageEditText.getText().toString();

                            //lay dau time
                            final String currentTimestamp = String.valueOf(System.currentTimeMillis()).substring(0, 10);


                            databaseReference.child("chat").child(chatKey).child("user_1").setValue(getUserMbl);
                            databaseReference.child("chat").child(chatKey).child("user_2").setValue(getUser);
                            databaseReference.child("chat").child(chatKey).child("messages").child(currentTimestamp).child("msg").setValue(getTxtMessage);
                            databaseReference.child("chat").child(chatKey).child("messages").child(currentTimestamp).child("user").setValue(getUserMbl);
                            messageEditText.setText("");

                        }
                    });

                } else {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        final String getKey = dataSnapshot.getKey();
                        chatKey = getKey;

                        if (dataSnapshot.hasChild("user_1") && dataSnapshot.hasChild("user_2") && dataSnapshot.hasChild("messages")) {
                            final String getUserOne = dataSnapshot.child("user_1").getValue(String.class);
                            final String getUserTwo = dataSnapshot.child("user_2").getValue(String.class);

                            //check dung 2 user moi lay
                            if (getUserOne.equals(getUser) && getUserTwo.equals(getUserMbl) || getUserOne.equals(getUserMbl) && getUserTwo.equals(getUser)) {
                                sendBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        final String getTxtMessage = messageEditText.getText().toString();

                                        //lay dau time
                                        final String currentTimestamp = String.valueOf(System.currentTimeMillis()).substring(0, 10);
                                        databaseReference.child("chat").child(chatKey).child("messages").child(currentTimestamp).child("msg").setValue(getTxtMessage);
                                        databaseReference.child("chat").child(chatKey).child("messages").child(currentTimestamp).child("user").setValue(getUserMbl);
                                        messageEditText.setText("");
                                    }
                                });

                            }
                            else{
                                if (snapshot.hasChild("chat")) {
                                    chatKey = String.valueOf(snapshot.child("chat").getChildrenCount() + 1);

                                }
                                sendBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        final String getTxtMessage = messageEditText.getText().toString();

                                        //lay dau time
                                        final String currentTimestamp = String.valueOf(System.currentTimeMillis()).substring(0, 10);


                                        databaseReference.child("chat").child(chatKey).child("user_1").setValue(getUserMbl);
                                        databaseReference.child("chat").child(chatKey).child("user_2").setValue(getUser);
                                        databaseReference.child("chat").child(chatKey).child("messages").child(currentTimestamp).child("msg").setValue(getTxtMessage);
                                        databaseReference.child("chat").child(chatKey).child("messages").child(currentTimestamp).child("user").setValue(getUserMbl);
                                        messageEditText.setText("");

                                    }
                                });
                            }
                        }

                    }
                }


            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.hasChild("chat")) {
                    if (snapshot.child("chat").child(chatKey).hasChild("messages")) {
                        chatLists.clear();
                        for (DataSnapshot messagesSnapshot : snapshot.child("chat").child(chatKey).child("messages").getChildren()) {
                            if (messagesSnapshot.hasChild("msg") && messagesSnapshot.hasChild("user")) {
                                final String messageTimestamps = messagesSnapshot.getKey();
                                final String getUser = messagesSnapshot.child("user").getValue(String.class);
                                final String getMsg = messagesSnapshot.child("msg").getValue(String.class);
                                Timestamp timestamp = new Timestamp(Long.parseLong(messageTimestamps));
                                Date date = new Date(timestamp.getTime());
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy ", Locale.getDefault());
                                SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
                                ChatList chatList = new ChatList(getUser, getName, getMsg, simpleDateFormat.format(date), simpleTimeFormat.format(date));

                                chatLists.add(chatList);
                                if (loadingFistTime || Long.parseLong(messageTimestamps) > Long.parseLong(MemoryData.getLastMsgTS(Chat.this, chatKey))) {
                                    loadingFistTime = false;
                                    MemoryData.saveLastMsgTS(messageTimestamps, chatKey, Chat.this);
                                    chatAdapter.updateChatList(chatLists);
                                    chattingRecyclerView.scrollToPosition(chatLists.size() - 1);
                                }
                            }
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}