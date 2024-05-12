package com.example.myapplication.Employee;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.servicess.VacancyServicesImp;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class FormulaireEmp extends AppCompatActivity {

    EditText name,name2,email,email2,tp,tp2,link,numNational,service,sService,pass,repass,society,adresse;
    Button submit,chooseImg;
    ProgressBar pb;
    ImageView imgUpload;

    VacancyServicesImp vacSer = new VacancyServicesImp();
    private StorageReference mStorageRef;
    private static final int PICK_IMAGE_REQUEST = 1;


    Uri mImageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire_employeur);
        // Initialiser FirebaseApp
        FirebaseApp.initializeApp(this);

        // Masquer la barre d'action si nécessaire
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        name = findViewById(R.id.etUserName);
        name2 = findViewById(R.id.etUserName2);
        society = findViewById(R.id.etUserOrganism);
        email = findViewById(R.id.etUserEmail);
        email2 = findViewById(R.id.etUserEmail2);
        tp = findViewById(R.id.etUserContact);
        tp2 = findViewById(R.id.etUserContact2);
        link = findViewById(R.id.etUserLink);
        numNational = findViewById(R.id.etUserNumber);
        service = findViewById(R.id.etUserService);
        sService = findViewById(R.id.etUserSService);
        pass = findViewById(R.id.etUserPassword);
        repass = findViewById(R.id.etReUserPassword);
        submit = findViewById(R.id.btnRegister);
        imgUpload = findViewById(R.id.ivUploadProfImg);
        chooseImg = findViewById(R.id.btnUploadProfImg);
        adresse = findViewById(R.id.etUserAddress);
        pb = findViewById(R.id.pbUploadImage);

        mStorageRef = FirebaseStorage.getInstance("gs://mobilappli.appspot.com").getReference("uploads");

        chooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //   uploadFile();
                vacSer.addNewUser(FormulaireEmp.this,society,name,name2,tp,tp2,email,email2,link,numNational,service,sService,adresse,pass,repass,mImageUri,pb);

            }
        });


    }

    private void openFileChooser(){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK &&
                data != null && data.getData() != null){
            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).into(imgUpload);

        }
    }



}