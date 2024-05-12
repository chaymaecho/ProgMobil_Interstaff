package com.example.myapplication.servicess;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Employee.ConnexionEmp;
import com.example.myapplication.common.ConstantsCommun;
import com.example.myapplication.common.OffreValide;
import com.example.myapplication.database.Connection;
import com.example.myapplication.DashBoard;

import com.example.myapplication.models.OffreE;
import com.example.myapplication.models.Utilisateurs;
import com.example.myapplication.common.OutilsCommun;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;


public class VacancyServicesImp implements VacancyServices {
    //get model class instance
    OffreE offreE = new OffreE();
    Utilisateurs utilisateurs = new Utilisateurs();

    //firebase instance
    Connection con = new Connection();
    OutilsCommun cu = new OutilsCommun();

    RecyclerView rvAll;
    OffreValide validerOffre = new OffreValide();
    private StorageTask mUploadTask;


    @Override
    public void addNewVacancy(Context c, EditText jobTitle, EditText organization, AutoCompleteTextView jobFamily,
                              AutoCompleteTextView jobLevel, EditText description, EditText salary, String deadline, String email) {


        String DES_PATTERN = ".{1,2000}"; // creating pattern for description
        try {
            //check input filed are empty or not
            if (TextUtils.isEmpty(jobTitle.getText().toString()))
                Toast.makeText(c, ConstantsCommun.PLEASE_ENTER_JOB_TITLE, Toast.LENGTH_LONG).show();

            else if (TextUtils.isEmpty(jobFamily.getText().toString()))
                Toast.makeText(c, ConstantsCommun.PLEASE_ENTER_JOB_FAMILY, Toast.LENGTH_LONG).show();

            else if (TextUtils.isEmpty(jobLevel.getText().toString()))
                Toast.makeText(c, ConstantsCommun.PLEASE_ENTER_JOB_LEVEL, Toast.LENGTH_LONG).show();

            else if (TextUtils.isEmpty(organization.getText().toString()))
                Toast.makeText(c, ConstantsCommun.PLEASE_ENTER_JOB_ORGANIZATION, Toast.LENGTH_LONG).show();

            else if (TextUtils.isEmpty(salary.getText().toString()))
                Toast.makeText(c, ConstantsCommun.PLEASE_ENTER_JOB_SALARY, Toast.LENGTH_LONG).show();

            else if (TextUtils.isEmpty(description.getText().toString()))
                Toast.makeText(c, ConstantsCommun.PLEASE_ENTER_JOB_DESCRIPTION, Toast.LENGTH_LONG).show();

            else if (!(description.getText().toString()).matches(DES_PATTERN))
                Toast.makeText(c, ConstantsCommun.PLEASE_ENTER_JOB_DESCRIPTION_LIMIT, Toast.LENGTH_LONG).show();

            else {
                offreE.setJobTitle(jobTitle.getText().toString().trim());
                offreE.setJobFamily(jobFamily.getText().toString().trim());
                offreE.setJobLevel(jobLevel.getText().toString().trim());
                offreE.setOrganization(organization.getText().toString().trim());
                offreE.setSalary(salary.getText().toString().trim());
                offreE.setDescription(description.getText().toString().trim());
                offreE.setDeadline(deadline);
                offreE.setEmail(email);

                //insert value in to database

                con.getRef().child(ConstantsCommun.VACANCY).child(ConstantsCommun.VACANCY_ID + (cu.getNextID())).setValue(offreE);

                Toast.makeText(c, ConstantsCommun.DATA_INSERT_SUCCESSFULLY, Toast.LENGTH_LONG).show();


                //clear entered values
                clearVacancyForm(jobTitle, organization, jobFamily,
                        jobLevel, description, salary);
            }
        } catch (Exception e) {
            Toast.makeText(c, ConstantsCommun.DATA_INSERT_UNSUCCESSFULLY, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public FirebaseRecyclerOptions<OffreE> txtSearch(String str) {
        int val = 0;
        String val2 = "";

        //check the value is number or string
        try {
            val = Integer.parseInt(str);
            val2 = "" + val;
            //find values by salary
            FirebaseRecyclerOptions<OffreE> options = new FirebaseRecyclerOptions.Builder<OffreE>().
                    setQuery(con.getRef().child(ConstantsCommun.VACANCY).orderByChild("Salaire").startAt(val2).endAt(val2 + "~"), OffreE.class).build();
            return options;
        } catch (Exception e) {
            //find values by job title
            FirebaseRecyclerOptions<OffreE> options = new FirebaseRecyclerOptions.Builder<OffreE>().
                    setQuery(con.getRef().child(ConstantsCommun.VACANCY).orderByChild("Titre du poste").startAt(str).endAt(str + "~"), OffreE.class).build();
            return options;
        }

    }
    @Override
    public void addNewUser(Context c, EditText society, EditText name,EditText name2, EditText tp,EditText tp2, EditText email,EditText email2,EditText numNational,EditText link,EditText adresse, EditText service,EditText sService, EditText password, EditText repassword, Uri mImageUri, ProgressBar pb) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String passwordPatter = "^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

        try {
            if (validerOffre.isNameEmpty((name.getText().toString())))
                Toast.makeText(c, ConstantsCommun.PLEASE_ENTER_YOUR_NAME, Toast.LENGTH_LONG).show();

            else if (TextUtils.isEmpty(tp.getText().toString()))
                Toast.makeText(c, ConstantsCommun.PLEASE_ENTER_YOUR_MOBILE_NUMBER, Toast.LENGTH_LONG).show();

            else if((tp.getText().toString().length() < 10))
                Toast.makeText(c, ConstantsCommun.PLEASE_ENTER_YOUR_MOBILE_NUMBER, Toast.LENGTH_LONG).show();

            else if (TextUtils.isEmpty(email.getText().toString()))
                Toast.makeText(c, ConstantsCommun.PLEASE_ENTER_YOUR_EMAIL, Toast.LENGTH_LONG).show();

            else if (TextUtils.isEmpty(password.getText().toString()))
                Toast.makeText(c, ConstantsCommun.PLEASE_ENTER_YOUR_PASSWORD, Toast.LENGTH_LONG).show();

            else if (TextUtils.isEmpty(service.getText().toString()))
                Toast.makeText(c, ConstantsCommun.PLEASE_ENTER_SERVICE, Toast.LENGTH_LONG).show();

            else if (TextUtils.isEmpty(adresse.getText().toString()))
                Toast.makeText(c, ConstantsCommun.PLEASE_ENTER_ADDRESS, Toast.LENGTH_LONG).show();

            else if (TextUtils.isEmpty(repassword.getText().toString()))
                Toast.makeText(c, ConstantsCommun.PLEASE_ENTER_YOUR_PASSWORD, Toast.LENGTH_LONG).show();

            else if (!password.getText().toString().equals(repassword.getText().toString()))
                Toast.makeText(c, ConstantsCommun.PASSWORD_MISMATCHED, Toast.LENGTH_LONG).show();

            else if (!validerOffre.isEmailValid(email.getText().toString()))
                Toast.makeText(c, ConstantsCommun.INCORRECT_EMAIL, Toast.LENGTH_LONG).show();

            else if(!validerOffre.isPasswordValid(password.getText().toString())){
                Toast.makeText(c, ConstantsCommun.STRONG_PASSWORD, Toast.LENGTH_LONG).show();

            } else {

                String key = email.getText().toString().trim();
                utilisateurs.setName(name.getText().toString().trim());
                utilisateurs.setTel(tp.getText().toString().trim());
                utilisateurs.setEmail(email.getText().toString().trim());
                utilisateurs.setPassword(password.getText().toString().trim());
                utilisateurs.setSociety(society.getText().toString().trim());
                utilisateurs.setAddress(adresse.getText().toString().trim());
                utilisateurs.setService(service.getText().toString().trim());
                utilisateurs.setSservice(sService.getText().toString().trim());
                utilisateurs.setEmail2(email2.getText().toString().trim());
                utilisateurs.setTel2(tp2.getText().toString().trim());
                utilisateurs.setName2(name2.getText().toString().trim());
                utilisateurs.setLink(link.getText().toString().trim());
                utilisateurs.setNumVacancy(numNational.getText().toString().trim());

                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(c, "Progressing...", Toast.LENGTH_SHORT);
                } else {
                    uploadFile(mImageUri, pb, c, utilisateurs);
                }


            }

        } catch (Exception e) {

        }


    }

/*
    @Override
    public void addNewAgence(Context c, EditText interim,EditText tp,EditText email,EditText link,EditText adresse, EditText password, EditText repassword, Uri mImageUri, ProgressBar pb) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String passwordPatter = "^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

        try {
            if (validerOffre.isNameEmpty((interim.getText().toString())))
                Toast.makeText(c, ConstantsCommun.PLEASE_ENTER_YOUR_NAME, Toast.LENGTH_LONG).show();

            else if (TextUtils.isEmpty(tp.getText().toString()))
                Toast.makeText(c, ConstantsCommun.PLEASE_ENTER_YOUR_MOBILE_NUMBER, Toast.LENGTH_LONG).show();

            else if((tp.getText().toString().length() < 10))
                Toast.makeText(c, ConstantsCommun.PLEASE_ENTER_YOUR_MOBILE_NUMBER, Toast.LENGTH_LONG).show();

            else if (TextUtils.isEmpty(email.getText().toString()))
                Toast.makeText(c, ConstantsCommun.PLEASE_ENTER_YOUR_EMAIL, Toast.LENGTH_LONG).show();

            else if (TextUtils.isEmpty(password.getText().toString()))
                Toast.makeText(c, ConstantsCommun.PLEASE_ENTER_YOUR_PASSWORD, Toast.LENGTH_LONG).show();

            else if (TextUtils.isEmpty(adresse.getText().toString()))
                Toast.makeText(c, ConstantsCommun.PLEASE_ENTER_ADDRESS, Toast.LENGTH_LONG).show();

            else if (TextUtils.isEmpty(repassword.getText().toString()))
                Toast.makeText(c, ConstantsCommun.PLEASE_ENTER_YOUR_PASSWORD, Toast.LENGTH_LONG).show();

            else if (!password.getText().toString().equals(repassword.getText().toString()))
                Toast.makeText(c, ConstantsCommun.PASSWORD_MISMATCHED, Toast.LENGTH_LONG).show();

            else if (!validerOffre.isEmailValid(email.getText().toString()))
                Toast.makeText(c, ConstantsCommun.INCORRECT_EMAIL, Toast.LENGTH_LONG).show();

            else if(!validerOffre.isPasswordValid(password.getText().toString())){
                Toast.makeText(c, ConstantsCommun.STRONG_PASSWORD, Toast.LENGTH_LONG).show();

            } else {

                String key = email.getText().toString().trim();
                agence.setTel(tp.getText().toString().trim());
                agence.setEmail(email.getText().toString().trim());
                agence.setPassword(password.getText().toString().trim());
                agence.setInterim(interim.getText().toString().trim());
                agence.setAddress(adresse.getText().toString().trim());
                agence.setLink(link.getText().toString().trim());

                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(c, "Progressing...", Toast.LENGTH_SHORT);
                } else {
                    uploadFileA(mImageUri, pb, c, agence);
                }


            }

        } catch (Exception e) {

        }


    }
*/

    @Override
    public void validateUser(Context c, EditText email, EditText password) {
        //create patter for email
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        try {
            //check input fields are empty or not
            if (TextUtils.isEmpty(email.getText().toString()))
                Toast.makeText(c, ConstantsCommun.PLEASE_ENTER_YOUR_EMAIL, Toast.LENGTH_LONG).show();

            else if (TextUtils.isEmpty(password.getText().toString()))
                Toast.makeText(c, ConstantsCommun.PLEASE_ENTER_YOUR_PASSWORD, Toast.LENGTH_LONG).show();

            else if (!(email.getText().toString()).matches(emailPattern))
                Toast.makeText(c, ConstantsCommun.INCORRECT_EMAIL, Toast.LENGTH_LONG).show();

            else {
                String enteredEmail = email.getText().toString();
                String enteredPassword = password.getText().toString();
                //validate user
                Query checkUser = con.getRef().child(ConstantsCommun.APP_USER).orderByChild("email").equalTo(enteredEmail);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String val = "ok";
                            for (DataSnapshot temp : snapshot.getChildren()) {
                                val = temp.getKey();
                            }

                            String dbPassword = snapshot.child(val).child("password").getValue(String.class);
                            //get user values
                            if (dbPassword.equals(enteredPassword)) {
                                String dbName = snapshot.child(val).child("name").getValue(String.class);
                                String dbTel = snapshot.child(val).child("tel").getValue(String.class);
                                String dbEmail = snapshot.child(val).child("email").getValue(String.class);
                                String img =snapshot.child(val).child("img").getValue(String.class);
                                String society =snapshot.child(val).child("society").getValue(String.class);
                                Intent i = new Intent(c, DashBoard.class);

                                //i.putExtra("pass",dbPassword);
                                i.putExtra("name", dbName);
                                i.putExtra("tel", dbTel);
                                i.putExtra("email", dbEmail);
                                i.putExtra("img",img);

                                c.startActivity(i);
                            } else {
                                Toast.makeText(c, ConstantsCommun.ENTERED_PASSWORD_INCORRECT, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(c, ConstantsCommun.NO_SUCH_USER, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        } catch (Exception e) {
            Toast.makeText(c, ConstantsCommun.TRY_AGAIN , Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void addNewAgence(Context c, EditText interim, EditText tp, EditText email, EditText link, EditText adresse, EditText password, EditText repassword, Uri mImageUri, ProgressBar pb) {

    }

    @Override
    public void validateAgency(Context c, EditText email, EditText password) {
        //create patter for email
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        try {
            //check input fields are empty or not
            if (TextUtils.isEmpty(email.getText().toString()))
                Toast.makeText(c, ConstantsCommun.PLEASE_ENTER_YOUR_EMAIL, Toast.LENGTH_LONG).show();

            else if (TextUtils.isEmpty(password.getText().toString()))
                Toast.makeText(c, ConstantsCommun.PLEASE_ENTER_YOUR_PASSWORD, Toast.LENGTH_LONG).show();

            else if (!(email.getText().toString()).matches(emailPattern))
                Toast.makeText(c, ConstantsCommun.INCORRECT_EMAIL, Toast.LENGTH_LONG).show();

            else {
                String enteredEmail = email.getText().toString();
                String enteredPassword = password.getText().toString();
                //validate user
                Query checkAgency = con.getRef().child(ConstantsCommun.APP_AGENCE).orderByChild("email").equalTo(enteredEmail);
                checkAgency.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String val = "ok";
                            for (DataSnapshot temp : snapshot.getChildren()) {
                                val = temp.getKey();
                            }

                            String dbPassword = snapshot.child(val).child("password").getValue(String.class);
                            //get user values
                            if (dbPassword.equals(enteredPassword)) {
                                String dbLink = snapshot.child(val).child("link").getValue(String.class);
                                String dbTel = snapshot.child(val).child("tel").getValue(String.class);
                                String dbEmail = snapshot.child(val).child("email").getValue(String.class);
                                String img =snapshot.child(val).child("img").getValue(String.class);
                                String dbInterim =snapshot.child(val).child("interim").getValue(String.class);

                            } else {
                                Toast.makeText(c, ConstantsCommun.ENTERED_PASSWORD_INCORRECT, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(c, ConstantsCommun.NO_SUCH_USER, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        } catch (Exception e) {
            Toast.makeText(c, ConstantsCommun.TRY_AGAIN , Toast.LENGTH_LONG).show();
        }

    }

    public void addNewCandidat(Context c, EditText name,EditText prenom,EditText nationalite,EditText datenaiss,EditText tel,EditText ville,EditText email,Uri cv, EditText password, EditText repassword, Uri mImageUri, ProgressBar pb) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String passwordPatter = "^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

        try {

            if (TextUtils.isEmpty(email.getText().toString()))
                Toast.makeText(c, ConstantsCommun.PLEASE_ENTER_YOUR_EMAIL, Toast.LENGTH_LONG).show();

            else if (TextUtils.isEmpty(password.getText().toString()))
                Toast.makeText(c, ConstantsCommun.PLEASE_ENTER_YOUR_PASSWORD, Toast.LENGTH_LONG).show();

            else if (TextUtils.isEmpty(repassword.getText().toString()))
                Toast.makeText(c, ConstantsCommun.PLEASE_ENTER_YOUR_PASSWORD, Toast.LENGTH_LONG).show();

            else if (!password.getText().toString().equals(repassword.getText().toString()))
                Toast.makeText(c, ConstantsCommun.PASSWORD_MISMATCHED, Toast.LENGTH_LONG).show();


            if (mUploadTask != null && mUploadTask.isInProgress()) {
                Toast.makeText(c, "Progressing...", Toast.LENGTH_SHORT);
            }



        } catch (Exception e) {

        }


    }

    @Override
    public void validateCandidat(Context c, EditText email, EditText password) {
        //create patter for email
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        try {
            //check input fields are empty or not
            if (TextUtils.isEmpty(email.getText().toString()))
                Toast.makeText(c, ConstantsCommun.PLEASE_ENTER_YOUR_EMAIL, Toast.LENGTH_LONG).show();

            else if (TextUtils.isEmpty(password.getText().toString()))
                Toast.makeText(c, ConstantsCommun.PLEASE_ENTER_YOUR_PASSWORD, Toast.LENGTH_LONG).show();

            else if (!(email.getText().toString()).matches(emailPattern))
                Toast.makeText(c, ConstantsCommun.INCORRECT_EMAIL, Toast.LENGTH_LONG).show();

            else {
                String enteredEmail = email.getText().toString();
                String enteredPassword = password.getText().toString();
                //validate user
                Query checkUser = con.getRef().child(ConstantsCommun.APP_CANDIDATE).orderByChild("email").equalTo(enteredEmail);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String val = "ok";
                            for (DataSnapshot temp : snapshot.getChildren()) {
                                val = temp.getKey();
                            }

                            String dbPassword = snapshot.child(val).child("password").getValue(String.class);
                            //get user values
                            if (dbPassword.equals(enteredPassword)) {
                                String dbName = snapshot.child(val).child("name").getValue(String.class);
                                String dbTel = snapshot.child(val).child("tel").getValue(String.class);
                                String dbEmail = snapshot.child(val).child("email").getValue(String.class);
                                String img =snapshot.child(val).child("img").getValue(String.class);
                                String prenom =snapshot.child(val).child("prenom").getValue(String.class);

                            } else {
                                Toast.makeText(c, ConstantsCommun.ENTERED_PASSWORD_INCORRECT, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(c, ConstantsCommun.NO_SUCH_USER, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        } catch (Exception e) {
            Toast.makeText(c, ConstantsCommun.TRY_AGAIN , Toast.LENGTH_LONG).show();
        }

    }



    public void clearVacancyForm(EditText jobTitle, EditText organization, EditText jobFamily,
                                 EditText jobLevel, EditText description, EditText salary) {
        jobTitle.setText("");
        organization.setText("");
        jobFamily.setText("");
        jobLevel.setText("");
        description.setText("");
        salary.setText("");
    }


    private String getFileExtension(Uri uri, Context c) {
        ContentResolver cR = c.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void uploadFile(Uri mImageUri, ProgressBar pb, Context c, Utilisateurs utilisateurs) {
        StorageReference mStorageRef = FirebaseStorage.getInstance(ConstantsCommun.FIREBASE_STORAGE_1).getReference("uploads");

        if (mImageUri != null) {

            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri, c));

            mUploadTask = fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pb.setProgress(0);
                        }
                    }, 500);

                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri downloadUrl = uri;
                            //Do what you want with the url

                            utilisateurs.setImg(downloadUrl.toString());

                            con.getRef().child(ConstantsCommun.APP_USER).child(ConstantsCommun.APP_USER_ID + (cu.getCusID())).setValue(utilisateurs);


                            Toast.makeText(c, ConstantsCommun.SUCCESS_LOGIN, Toast.LENGTH_LONG).show();
                            Intent i = new Intent(c, ConnexionEmp.class);
                            c.startActivity(i);
                        }

                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    pb.setProgress((int) progress);
                }
            });
        } else {
            Toast.makeText(c, ConstantsCommun.NO_FILE_SELECTED, Toast.LENGTH_SHORT).show();
        }
    }
}
