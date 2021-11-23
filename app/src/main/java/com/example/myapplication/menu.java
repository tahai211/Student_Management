package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class menu extends AppCompatActivity {
    TextView name1,phone1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        //getSupportActionBar().hide();
        setContentView(R.layout.menu);
        name1 =findViewById(R.id.name);
        phone1=findViewById(R.id.phone);
        showProfile();
    }
    private void showProfile() {
        Intent intent = getIntent();
        String user_phone = intent.getStringExtra("phone");
        String user_name = intent.getStringExtra("name");
        name1.setText(user_name);
        phone1.setText(user_phone);
    }
}
