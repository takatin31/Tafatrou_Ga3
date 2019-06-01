package com.example.light.ramdanapp;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InfoChangeActivity extends AppCompatActivity {

    EditText nameJam;
    EditText phone;
    EditText number;
    EditText ccp;
    FloatingActionButton confirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_change);
        final String mail = (String) getIntent().getSerializableExtra("email");

        //changing dimensions
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*0.95), (int)(height*0.55));

        //getting widgets
        nameJam = findViewById(R.id.jam3iyaName);
        phone = findViewById(R.id.phone);
        number = findViewById(R.id.number);
        confirm = findViewById(R.id.confirm);
        ccp = findViewById(R.id.ccp);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        DatabaseReference infoRef = myRef.child("Jam3iyates");
        String mail2 = mail.replace('.', ',');
        DatabaseReference jamMailRef = infoRef.child(mail2);
        final DatabaseReference nameRef = jamMailRef.child("name");
        final DatabaseReference phoneRef = jamMailRef.child("phone");
        final DatabaseReference numberRef = jamMailRef.child("number");
        final DatabaseReference ccpRef = jamMailRef.child("ccp");

        jamMailRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nameJam.setText(dataSnapshot.child("name").getValue(String.class));
                phone.setText(dataSnapshot.child("phone").getValue(String.class));
                number.setText(dataSnapshot.child("number").getValue(String.class));
                ccp.setText(dataSnapshot.child("ccp").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameJam.getText().toString();
                String phoneNumber = phone.getText().toString();
                String numberJam = number.getText().toString();
                String sccp = ccp.getText().toString();

                nameRef.setValue(name);
                phoneRef.setValue(phoneNumber);
                numberRef.setValue(numberJam);
                ccpRef.setValue(sccp);

                finish();
            }
        });

    }
}
