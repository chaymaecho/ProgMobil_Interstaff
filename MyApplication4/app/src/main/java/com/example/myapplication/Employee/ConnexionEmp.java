package com.example.myapplication.Employee;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.servicess.VacancyServicesImp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class ConnexionEmp extends AppCompatActivity {

    EditText email,pass;
    Button submit,sign,forgotPassword;
    private FirebaseAuth auth;
    VacancyServicesImp vacSer = new VacancyServicesImp();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_employeur);
        // Initialiser FirebaseApp
        FirebaseApp.initializeApp(this);

        // Masquer la barre d'action si nécessaire
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        email = findViewById(R.id.etUserLoginEmail);
        pass = findViewById(R.id.etUserLoginPassword);
        submit = findViewById(R.id.btnLogin);
        sign = findViewById(R.id.btnSignIn);
        forgotPassword = findViewById(R.id.forget);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vacSer.validateUser(ConnexionEmp.this,email,pass);
            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(ConnexionEmp.this, FormulaireEmp.class);
                startActivity(i);
            }
        });

        auth = FirebaseAuth.getInstance();
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ConnexionEmp.this);
                View dialogView = getLayoutInflater().inflate(R.layout.mot_de_pass_oublie, null);
                EditText emailBox = dialogView.findViewById(R.id.emailBox);
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                dialogView.findViewById(R.id.btnReset).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String userEmail = emailBox.getText().toString();
                        if (TextUtils.isEmpty(userEmail) && !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
                            Toast.makeText(ConnexionEmp.this, "Veuillez entrer votre adresse e-mail enregistrée", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        auth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(ConnexionEmp.this, "Veuillez vérifier votre messagerie électronique", Toast.LENGTH_SHORT).show();

                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(ConnexionEmp.this, "Veuillez vérifier votre messagerie électronique", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                dialogView.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                if (dialog.getWindow() != null){
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                dialog.show();
            }
        });


    }
}