package com.example.myapplication.candidatAnonym;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class CandidatLogin extends AppCompatActivity {

    private EditText etUserLoginEmail, etUserLoginPassword;
    private Button btnLogin, btnSignIn, btnForget;
    private ImageView backgroundImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_candidat);

        // Initialisation des vues
        etUserLoginEmail = findViewById(R.id.etUserLoginEmail);
        etUserLoginPassword = findViewById(R.id.etUserLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnForget = findViewById(R.id.forget);
        backgroundImage = findViewById(R.id.backgroundImage);

        // Définition des écouteurs d'événements
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code pour le bouton de connexion
                String email = etUserLoginEmail.getText().toString().trim();
                String password = etUserLoginPassword.getText().toString().trim();

                // Vérification des champs email et mot de passe
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(CandidatLogin.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                } else {
                    // Authentification de l'utilisateur
                    // Ajoutez votre logique d'authentification ici
                    // Par exemple, vous pouvez appeler une méthode pour vérifier les informations d'identification
                }
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code pour le bouton d'inscription
                // Ajoutez ici le code pour rediriger vers l'activité d'inscription
            }
        });

        btnForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code pour le bouton de récupération de mot de passe
                // Ajoutez ici le code pour gérer la récupération de mot de passe
            }
        });
    }
}
