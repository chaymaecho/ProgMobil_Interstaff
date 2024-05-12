package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.myapplication.Employee.AccepteCandidatureActivity;
import com.example.myapplication.Employee.MenuEmp;



public class DashBoard extends AppCompatActivity {


    String msg,name,img;
    private CardView vacancie, agence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gestion_offres_employeur);


        vacancie = findViewById(R.id.cardview1);
        agence = findViewById(R.id.cardview3);


        Intent i = getIntent();
        msg = i.getStringExtra("email");
        name = i.getStringExtra("name");
        img = i.getStringExtra("img");

        System.out.println(msg);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        vacancie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent().setClass(DashBoard.this, MenuEmp.class);
                startActivity(intent);
            }
        });

        agence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent().setClass(DashBoard.this, AccepteCandidatureActivity.class);
                startActivity(intent);
            }
        });

    }


}