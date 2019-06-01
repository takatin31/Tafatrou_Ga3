package com.example.light.ramdanapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainJAm3iyaActivity extends AppCompatActivity {

    private Button changeInfoBtn;
    private Button changeLocationBtn;
    private Button changeNeedsBtn;
    private FloatingActionButton close;
    String name, phone, number, ccp;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_jam3iya);
        final String mail = (String) getIntent().getSerializableExtra("email");
        final String previous = (String) getIntent().getSerializableExtra("previous");
        name = (String) getIntent().getSerializableExtra("name");
        phone = (String) getIntent().getSerializableExtra("phone");
        number = (String) getIntent().getSerializableExtra("number");
        ccp = (String) getIntent().getSerializableExtra("ccp");


        if (previous != null && previous.equals("map")){
            String position = (String) getIntent().getSerializableExtra("position");
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();


            DatabaseReference infoRef = myRef.child("Jam3iyates");
            String mail2 = mail.replace('.', ',');
            DatabaseReference jamMailRef = infoRef.child(mail2);
            DatabaseReference positionRef = jamMailRef.child("position");
            positionRef.setValue(position);

        }

        changeInfoBtn = findViewById(R.id.infoChangeBtn);
        changeLocationBtn = findViewById(R.id.placeChangeBtn);
        changeNeedsBtn = findViewById(R.id.needsChangeBtn);
        close = findViewById(R.id.close);

        changeInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainJAm3iyaActivity.this, InfoChangeActivity.class);
                intent.putExtra("email", mail);
                startActivity(intent);
            }
        });

        changeLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainJAm3iyaActivity.this, MapActivity.class);
                intent.putExtra("previous", "change");
                intent.putExtra("email", mail);
                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                intent.putExtra("number", number);
                intent.putExtra("ccp", ccp);
                startActivity(intent);
                finish();
            }
        });

        changeNeedsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainJAm3iyaActivity.this, ListActivity.class);
                intent.putExtra("email", mail);
                startActivity(intent);
                finish();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth;
                mAuth = FirebaseAuth.getInstance();

                mAuth.signOut();
                Toast.makeText(MainJAm3iyaActivity.this, "تم الخروج بنجاح", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainJAm3iyaActivity.this, Login2Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
