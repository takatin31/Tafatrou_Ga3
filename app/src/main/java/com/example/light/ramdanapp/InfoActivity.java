package com.example.light.ramdanapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InfoActivity extends AppCompatActivity {

    private Button confirmBtn , mapBtn;
    private TextView nom, phone, number, ccp;
    boolean everythingOk;
    String mail;
    String sname, sphone, snumber, sccp, sposition;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        mail = (String) getIntent().getSerializableExtra("email");
        final String previous = (String) getIntent().getSerializableExtra("previous");


        sname = "";
        snumber = "";
        sphone = "";
        sccp = "";


        confirmBtn = findViewById(R.id.confirmBtn);
        mapBtn = findViewById(R.id.mapBtn);

        nom = findViewById(R.id.nom);
        phone = findViewById(R.id.phone);
        number = findViewById(R.id.number);
        ccp = findViewById(R.id.ccp);

        if (previous.equals("map")){
            sname = (String) getIntent().getSerializableExtra("name");
            sphone = (String) getIntent().getSerializableExtra("phone");
            snumber = (String) getIntent().getSerializableExtra("number");
            sccp = (String) getIntent().getSerializableExtra("ccp");
            sposition = (String) getIntent().getSerializableExtra("position");

            nom.setText(sname);
            phone.setText(sphone);
            number.setText(snumber);
            ccp.setText(sccp);
        }


        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sname = nom.getText().toString();
                sphone = phone.getText().toString();
                snumber = number.getText().toString();
                sccp = ccp.getText().toString();
                Intent intent = new Intent(InfoActivity.this, MapActivity.class);
                intent.putExtra("previous", "info");
                intent.putExtra("email", mail);
                intent.putExtra("name", sname);
                intent.putExtra("phone", sphone);
                intent.putExtra("number", snumber);
                intent.putExtra("ccp", sccp);

                startActivity(intent);
                finish();
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                everythingOk = true;

                if (sname.equals("") || sphone.equals("") || snumber.equals("") || !previous.equals("map")){
                    everythingOk = false;
                }

                if (everythingOk){
                    sname = nom.getText().toString();
                    sphone = phone.getText().toString();
                    snumber = number.getText().toString();
                    sccp = ccp.getText().toString();


                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference();


                    DatabaseReference infoRef = myRef.child("Jam3iyates");
                    String mail2 = mail.replace('.', ',');

                    DatabaseReference jamMailRef = infoRef.child(mail2);
                    DatabaseReference nameRef = jamMailRef.child("name");
                    DatabaseReference phoneRef = jamMailRef.child("phone");
                    DatabaseReference numberRef = jamMailRef.child("number");
                    DatabaseReference ccpRef = jamMailRef.child("ccp");
                    DatabaseReference position = jamMailRef.child("position");

                    nameRef.setValue(sname);
                    phoneRef.setValue(sphone);
                    numberRef.setValue(snumber);
                    ccpRef.setValue(sccp);
                    position.setValue(sposition);

                    Intent intent = new Intent(InfoActivity.this, MainJAm3iyaActivity.class);
                    intent.putExtra("email", mail);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }
}
