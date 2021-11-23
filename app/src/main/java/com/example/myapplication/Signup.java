package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class Signup extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://kmar-efe69-default-rtdb.firebaseio.com/");
    CircleImageView photo;
    Uri filepath;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_up);


        final EditText name = findViewById(R.id.RegisterInputName);
        final EditText user = findViewById(R.id.InputUser);
        final EditText password = findViewById(R.id.InputPassWord);
        final EditText comfirmpass = findViewById(R.id.InputConfirmPassWord);
        final EditText email = findViewById(R.id.InputEmail);
        final EditText phone = findViewById(R.id.InputPhone);
        final Button sign_up = findViewById(R.id.SignUpButton);
        final TextView sign_in = findViewById(R.id.signInText);
        photo = findViewById(R.id.imageProfile);


        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);

            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nametxt = name.getText().toString();
                final String usertxt = user.getText().toString();
                final String passwordtxt = password.getText().toString();
                final String compasswordtxt = comfirmpass.getText().toString();
                final String emailtxt = email.getText().toString();
                final String phonetxt = phone.getText().toString();
                final String phototxt = photo.toString();
                student Student = new student(usertxt,passwordtxt,emailtxt,nametxt,phonetxt,phototxt);

                if (nametxt.isEmpty() || usertxt.isEmpty() || passwordtxt.isEmpty() || compasswordtxt.isEmpty() || emailtxt.isEmpty() || phonetxt.isEmpty()) {
                    Toast.makeText(Signup.this, "Error", Toast.LENGTH_SHORT).show();

                } else if (!passwordtxt.equals(compasswordtxt)) {
                    Toast.makeText(Signup.this, "Error", Toast.LENGTH_SHORT).show();

                } else {

                    databaseReference.child("Member").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(usertxt)) {
                                Toast.makeText(Signup.this, "User is already registered", Toast.LENGTH_SHORT).show();
                            } else {
                                String pathobject =String.valueOf(Student.getUser());
                                databaseReference.child("Member").child(pathobject).setValue(Student);


                                Toast.makeText(Signup.this, "Win", Toast.LENGTH_SHORT).show();
                                Intent c = new Intent(getApplicationContext(), login.class);
                                startActivity(c);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            }
        });
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(getApplicationContext(), login.class);
                startActivity(a);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            filepath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                photo.setImageBitmap(bitmap);

            } catch (Exception ex) {

            }
        }


    }
}
