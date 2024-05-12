package com.example.myapplication.Employee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.Connection;
import com.example.myapplication.DashBoard;
import com.example.myapplication.models.OffreE;
import com.example.myapplication.common.UpdateOffrePublie;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseApp;

public class OffrePublie extends AppCompatActivity {

    UpdateOffrePublie vpa;
    RecyclerView recyclerView;
    Connection con =  new Connection();
    String msg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offre_publier);
        // Initialiser FirebaseApp
        FirebaseApp.initializeApp(this);

        // Masquer la barre d'action si nécessaire
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        Intent i = getIntent();
        msg = i.getStringExtra("email");
        System.out.println(msg);

        recyclerView = (RecyclerView)findViewById(R.id.rvUpdateVacancy);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<OffreE> options = new FirebaseRecyclerOptions.Builder<OffreE>().
                setQuery(con.getRef().child("OffreE").orderByChild("email").equalTo(msg), OffreE.class).build();

        vpa = new UpdateOffrePublie(options);
        recyclerView.setAdapter(vpa);

        ActionBar actionBarr = getSupportActionBar();
        if (actionBarr != null) {
            actionBarr.hide();
        }

        ImageView backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OffrePublie.this, MenuEmp.class);
                startActivity(intent);
                finish();
            }
        });


        ImageView homeImageView = findViewById(R.id.image_home);
        homeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OffrePublie.this, DashBoard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        ImageView notifImageView = findViewById(R.id.image_notif);
        notifImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OffrePublie.this, DashBoard.class);
                startActivity(intent);
                finish();
            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();
        vpa.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        vpa.stopListening();
    }
}