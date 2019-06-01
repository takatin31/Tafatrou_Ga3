package com.example.light.ramdanapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseActivity extends AppCompatActivity {

    private Button clientBtn, jam3iyaBtn, otherBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        clientBtn = findViewById(R.id.clientBtn);
        jam3iyaBtn = findViewById(R.id.jam3iyaBtn);
        otherBtn = findViewById(R.id.otherBtn);

        clientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseActivity.this, MapActivity.class);
                intent.putExtra("previous","client");
                startActivity(intent);
                finish();
            }
        });

        jam3iyaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        otherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseActivity.this, MapActivity.class);
                intent.putExtra("previous","other");
                startActivity(intent);
                finish();
            }
        });
    }
}
