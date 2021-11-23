package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.myapplication.databinding.ActivityListAccBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListAccActivity extends AppCompatActivity {

    private UserAdapter mUserAdapter;
    private List<student> mListUser;
    private ActivityListAccBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListAccBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mListUser =new ArrayList<>();
        mUserAdapter = new UserAdapter(mListUser);
        binding.rcvUser.setAdapter(mUserAdapter);
        getListUserreamtimeDatabase();
    }
    private void getListUserreamtimeDatabase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Member");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (mListUser != null){
                    mListUser.clear();
                }
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    student Student = dataSnapshot.child("Member").getValue(student.class);
                    mListUser.add(Student);
                }
                if(mListUser.size() > 0){
                    mUserAdapter.notifyDataSetChanged();

                }
                else {
                    Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ListAccActivity.this,"error",Toast.LENGTH_SHORT).show();

            }
        });

    }
}