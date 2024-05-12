package com.example.myapplication.Employee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import com.example.myapplication.servicess.VacancyServicesImp;
import com.google.firebase.FirebaseApp;

public class AjoutOffreE extends AppCompatActivity {

    EditText jobTitle, organization, salary, description;
    DatePicker deadline;
    Button submit;
    String msg;

    VacancyServicesImp vacSer = new VacancyServicesImp();

    //job family
    String item[] = {"Banque","Conduite","Informatique","Developpement","Marketing","BTP","comptabilité", "Government", "Other"};
    AutoCompleteTextView atJobFamily;
    ArrayAdapter<String> adapter;


    String item2[] = {"Débutant","Intermédiaire","avancé","expérimenté", "cadre supérieur"};
    AutoCompleteTextView atJobLevel;
    ArrayAdapter<String> adapter2;


    ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialiser FirebaseApp
        FirebaseApp.initializeApp(this);

        // Masquer la barre d'action si nécessaire
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        setContentView(R.layout.activity_ajout_offres_employeur);

        //assign view into variables
        jobTitle = findViewById(R.id.etJobTitle);
        organization = (findViewById(R.id.etOrganization));
        salary = (findViewById(R.id.etSalary));
        description = (findViewById(R.id.etDescription));
        deadline = (findViewById(R.id.dpDeadline));
        submit = (findViewById(R.id.btnAddData));

        backArrow = findViewById(R.id.backArrow);

        ///Job family dropdown menu
        atJobFamily = findViewById(R.id.etJobFamily);
        adapter = new ArrayAdapter<String>(this,R.layout.ensemble,item);
        atJobFamily.setAdapter(adapter);
        atJobFamily.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = Long.toString(parent.getItemIdAtPosition(position));

                Toast.makeText(getApplicationContext(),"Groupe "+item, Toast.LENGTH_SHORT);
            }
        });

        //Job level dropdown menu
        atJobLevel = findViewById(R.id.etJobLevel);
        adapter2 = new ArrayAdapter<String>(this,R.layout.ensemble,item2);
        atJobLevel.setAdapter(adapter2);
        atJobLevel.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item2 = Long.toString(parent.getItemIdAtPosition(position));

                Toast.makeText(getApplicationContext(),"Groupe "+item2, Toast.LENGTH_SHORT);
            }
        });



        //getExtra
        Intent i = getIntent();
        msg = i.getStringExtra("email");
        System.out.println(msg);



        //create onclick to save data
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String date = ""+deadline.getDayOfMonth()+"/"+deadline.getMonth()+"/"+deadline.getYear();

                //add values
                vacSer.addNewVacancy(AjoutOffreE.this,jobTitle, organization, atJobFamily,
                        atJobLevel, description,salary, date,msg) ;



            }
        });


        ActionBar actionBarr = getSupportActionBar();
        if (actionBarr != null) {
            actionBarr.hide();
        }

        ImageView backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AjoutOffreE.this, MenuEmp.class);
                startActivity(intent);
                finish();
            }
        });


    }




}

