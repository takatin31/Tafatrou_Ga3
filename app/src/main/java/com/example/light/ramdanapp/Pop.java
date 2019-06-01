package com.example.light.ramdanapp;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Pop extends AppCompatActivity {

    EditText productName;
    EditText quantite;
    Spinner spinner;
    FloatingActionButton confirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);
        final String mail = (String) getIntent().getSerializableExtra("email");
        final ProductModel productModel = (ProductModel) getIntent().getSerializableExtra("product");


        productName = findViewById(R.id.productName);
        quantite = findViewById(R.id.quantite);
        spinner = findViewById(R.id.spinner);
        confirm = findViewById(R.id.confirm);

        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("كغ", 0);
        hashMap.put("لتر", 1);
        hashMap.put("علب", 2);
        hashMap.put("كيس", 3);
        hashMap.put("قارورة", 4);

        productName.setText(productModel.getProduct());
        if (productModel.getQuantite() == 0){
            quantite.setText("");
        }else{
            quantite.setText(String.valueOf(productModel.getQuantite()));
        }

        spinner.setSelection(hashMap.get(productModel.getUnite()));

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = productName.getText().toString();
                String qnt = quantite.getText().toString();
                String unite = spinner.getSelectedItem().toString();

                if (!name.equals("") && !qnt.equals("")){

                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference();

                    DatabaseReference infoRef = myRef.child("Jam3iyates");
                    String mail2 = mail.replace('.', ',');
                    DatabaseReference jamMailRef = infoRef.child(mail2);
                    DatabaseReference productsRef = jamMailRef.child("products");

                    DatabaseReference prodRef = productsRef.child(productModel.getProduct());
                    DatabaseReference qntRef = prodRef.child("Quantite");
                    DatabaseReference unitRef = prodRef.child("Unite");
                    DatabaseReference nameRef = prodRef.child("Name");

                    qntRef.removeValue();
                    unitRef.removeValue();
                    nameRef.removeValue();

                    prodRef = productsRef.child(name);
                    qntRef = prodRef.child("Quantite");
                    unitRef = prodRef.child("Unite");
                    nameRef = prodRef.child("Name");

                    nameRef.setValue(name);
                    qntRef.setValue(qnt);
                    unitRef.setValue(unite);
                    finish();
                }else{
                    Toast.makeText(Pop.this, "الرجاء ملا جميع البيانات", Toast.LENGTH_SHORT).show();
                }

            }
        });


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.9), (int)(height*0.5));
    }
}
