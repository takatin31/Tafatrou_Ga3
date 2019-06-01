package com.example.light.ramdanapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListActivityOther extends AppCompatActivity {

    private RecyclerView recyclerView;
    TextView name, phone, number, ccp;

    Button continueBtn;

    RecyclerViewAdapter2 myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_other);
        final String mail = (String) getIntent().getSerializableExtra("email");

        name = findViewById(R.id.jamName);
        phone = findViewById(R.id.phoneJam);
        number = findViewById(R.id.numberJam);
        ccp = findViewById(R.id.ccpJam);

        recyclerView = findViewById(R.id.list_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final List<ProductModel> list = new ArrayList<>();

        continueBtn = findViewById(R.id.continueBtn);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivityOther.this, MapActivity.class);
                intent.putExtra("previous", "other");
                startActivity(intent);
                finish();
            }
        });

        myAdapter = new RecyclerViewAdapter2(list, this);
        recyclerView.setAdapter(myAdapter);



        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        DatabaseReference infoRef = myRef.child("Jam3iyates");
        String mail2 = mail.replace('.', ',');
        DatabaseReference jamMailRef = infoRef.child(mail2);

        jamMailRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name.setText(dataSnapshot.child("name").getValue(String.class));
                phone.setText(dataSnapshot.child("phone").getValue(String.class));
                number.setText(dataSnapshot.child("number").getValue(String.class));
                ccp.setText(dataSnapshot.child("ccp").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference productsRef = jamMailRef.child("products");


        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ProductModel> list2 = new ArrayList<>();


                for (DataSnapshot dataS : dataSnapshot.getChildren()){

                    if (dataS.child("Name").getValue() != null && dataS.child("Unite").getValue() != null && dataS.child("Quantite").getValue() != null){
                        String name = dataS.child("Name").getValue(String.class);
                        String unit = dataS.child("Unite").getValue(String.class);
                        int qnt = Integer.parseInt(dataS.child("Quantite").getValue(String.class));
                        ProductModel productModel = new ProductModel(name, qnt, unit);
                        list2.add(productModel);
                    }
                }

                list.clear();
                list.addAll(list2);
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}
