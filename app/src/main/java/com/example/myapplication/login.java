package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {
   DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://kmar-efe69-default-rtdb.firebaseio.com/");
   student post = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();



        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        final EditText user = findViewById(R.id.edittextaccount);
        final EditText password = findViewById(R.id.edittextpassword);
        final Button btnLogin = findViewById(R.id.btnLogin);
        final TextView textView = findViewById(R.id.RegisterButton);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String usertxt = user.getText().toString();
                final String passwordtxt = password.getText().toString();
                if(usertxt.isEmpty()|| passwordtxt.isEmpty()){
                    Toast.makeText(login.this,"please",Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("Member").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(usertxt)){
                                final String getPassword =snapshot.child(usertxt).child("password").getValue(String.class);
                                if(getPassword.equals(passwordtxt)){
                                    String nameFromDB = snapshot.child(usertxt).child("name").getValue(String.class);
                                    String userFromDB = usertxt;
                                    String emailFromDB = snapshot.child(usertxt).child("email").getValue(String.class);
                                    String phoneFromDB = snapshot.child(usertxt).child("phone").getValue(String.class);
                                    String photoFromDB = snapshot.child(usertxt).child("photo").getValue(String.class);

                                    Toast.makeText(login.this,"successfully logger in",Toast.LENGTH_SHORT).show();
                                    Intent a = new Intent(getApplicationContext(),homeScreen.class);
                                    a.putExtra("name",nameFromDB);
                                    a.putExtra("user",userFromDB);
                                    a.putExtra("email",emailFromDB);
                                    a.putExtra("phone",phoneFromDB);
                                    a.putExtra("photo",photoFromDB);
                                    startActivity(a);
                                    finish();
                                }
                                else{
                                    Toast.makeText(login.this,"wrong password",Toast.LENGTH_SHORT).show();
                                }

                            }
                            else{
                                Toast.makeText(login.this,"wrong user",Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Signup.class);
                startActivity(intent);
                finish();
            }
        });

    }
    public void hideStatusBar() {
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}



