package com.example.smartgreenhouse2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityAtap extends AppCompatActivity {
    Button btnOpen,btnClose;
    DatabaseReference dref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atap);
        btnClose = (Button) findViewById(R.id.btn_menutup_atap);
        btnOpen = (Button) findViewById(R.id.btn_membuka_atap);

        dref = FirebaseDatabase.getInstance().getReference();

        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference motor = FirebaseDatabase.getInstance().getReference("Motor");
                motor.setValue("On");
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference motor = FirebaseDatabase.getInstance().getReference("Motor");
                motor.setValue("Off");
            }
        });

    }
}