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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    FloatingActionButton addBtn;
    Button continueBtn;


    RecyclerViewAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        final String mail = (String) getIntent().getSerializableExtra("email");
        recyclerView = findViewById(R.id.list_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final List<ProductModel> list = new ArrayList<>();

        addBtn = findViewById(R.id.add);
        continueBtn = findViewById(R.id.continueBtn);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, MainJAm3iyaActivity.class);
                intent.putExtra("email", mail);
                startActivity(intent);
                finish();
            }
        });


        myAdapter = new RecyclerViewAdapter(list, this, mail);
        recyclerView.setAdapter(myAdapter);

        addBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                list.add(new ProductModel("", 0, "كغ"));
                myAdapter.notifyDataSetChanged();
            }
        });


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        DatabaseReference infoRef = myRef.child("Jam3iyates");
        String mail2 = mail.replace('.', ',');
        DatabaseReference jamMailRef = infoRef.child(mail2);
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
