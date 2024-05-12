package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Employee.ConnexionEmp;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class MainAPLI extends AppCompatActivity {

    private FirebaseAuth auth;
   // VacancyServicesImp vacSer = new VacancyServicesImp();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_apli);

        // Initialiser FirebaseApp
        FirebaseApp.initializeApp(this);

        // Masquer la barre d'action si nécessaire
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        // Initialisation des vues
        ImageView backgroundImage = findViewById(R.id.backgroundImage);
        ImageView imageView = findViewById(R.id.imageView);
        TextView textView = findViewById(R.id.textView);
        //Button btnAgency = findViewById(R.id.btnAgency);
        Button btnCandidat = findViewById(R.id.btnCandidat);
        Button btnEmployee = findViewById(R.id.btnEmployee);

        // Définition des écouteurs d'événements pour les boutons
      /*  btnAgency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code pour le bouton Agence
                // Ajoutez ici le code pour gérer le clic sur le bouton Agence
            }
        });*/

        btnCandidat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /* Intent i  = new Intent(MainAPLI.this, Candidat_LoginActivity.class);
                startActivity(i);*/
            }
        });

        btnEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i  = new Intent(MainAPLI.this, ConnexionEmp.class);
                startActivity(i);
            }
        });
    }
}
