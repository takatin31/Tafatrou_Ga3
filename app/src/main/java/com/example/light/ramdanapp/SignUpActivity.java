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

public class SignUpActivity extends AppCompatActivity {

    private EditText email, password;
    private Button signup;
    FloatingActionButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signup = findViewById(R.id.confirmBtn);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mail, passw;

                mail = email.getText().toString();
                passw = password.getText().toString();
                if (!email.equals("") && !passw.equals("")){
                    FirebaseAuth mAuth;
                    mAuth = FirebaseAuth.getInstance();

                    mAuth.createUserWithEmailAndPassword(mail, passw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText( SignUpActivity.this, "تم التسجيل بنجاح", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this, InfoActivity.class);
                                intent.putExtra("email", mail);
                                intent.putExtra("previous", "signup");
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }else{
                    Toast.makeText( SignUpActivity.this, "الرجاء ملأ كل البيانات", Toast.LENGTH_SHORT).show();

                }



            }
        });
    }
}
