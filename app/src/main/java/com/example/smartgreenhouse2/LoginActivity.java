package com.example.smartgreenhouse2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
private Button btnlogin;
private EditText mEmail;
private EditText mPassword;
private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
//        connection to firebase
        setContentView(R.layout.activity_login);
        btnlogin = (Button) findViewById(R.id.btn_login);
        mEmail =  findViewById(R.id.inp_email);
        mPassword =  findViewById(R.id.inp_password);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String email,password;
        email = mEmail.getText().toString();
        password = mPassword.getText().toString();


        if (TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(),"Tolong Isi Email!!",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),"Tolong masukkan password",Toast.LENGTH_SHORT).show();
            return;
        }
//       berhasil login
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if (task.isSuccessful()){
                   Toast.makeText(getApplicationContext(),"Login Berhasil",Toast.LENGTH_SHORT).show();

                   Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                   startActivity(intent);
               }else {
//                   gagal login
                   Toast.makeText(getApplicationContext(),"Login Gagal",Toast.LENGTH_SHORT).show();
               }
            }
        });
    }
}