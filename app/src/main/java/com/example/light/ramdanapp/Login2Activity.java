package com.example.light.ramdanapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login2Activity extends AppCompatActivity {

    private EditText email, password;
    private Button login;
    FirebaseAuth mAuth;
    FloatingActionButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        mAuth = FirebaseAuth.getInstance();

        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login2Activity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        login = findViewById(R.id.confirmBtn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mail, passw;

                mail = email.getText().toString();
                passw = password.getText().toString();

                if (!email.equals("") && !passw.equals("")){
                    FirebaseAuth mAuth;
                    mAuth = FirebaseAuth.getInstance();

                    mAuth.signInWithEmailAndPassword(mail, passw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                boolean registrationCompleted = false;

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference();

                                Toast.makeText(Login2Activity.this, "تم التسجيل بنجاح", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login2Activity.this, MainJAm3iyaActivity.class);
                                intent.putExtra("email", mail);
                                startActivity(intent);
                                finish();
                            }else {
                                Toast.makeText(Login2Activity.this, "مشكل في التسجيل", Toast.LENGTH_SHORT).show();
                                Toast.makeText(Login2Activity.this, "الرجاء التحقق من العنوان البريدي و كلمة السر", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }





            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            Intent intent = new Intent(Login2Activity.this, MainJAm3iyaActivity.class);
            intent.putExtra("email", currentUser.getEmail());
            startActivity(intent);
            finish();
        }

    }
}
