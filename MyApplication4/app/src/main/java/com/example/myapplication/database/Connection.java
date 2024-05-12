package com.example.myapplication.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Connection {

    private DatabaseReference ref;

    public Connection(){
        ref = FirebaseDatabase.getInstance("https://mobilappli-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

    }


    public DatabaseReference getRef() {
        return ref;
    }

    public void setRef(DatabaseReference ref) {
        this.ref = ref;
    }




}
