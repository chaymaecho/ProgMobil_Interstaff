package com.example.myapplication.common;

import androidx.annotation.NonNull;

import com.example.myapplication.database.Connection;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class OutilsCommun {

    Connection con = new Connection();
    DatabaseReference ref = con.getRef().child(ConstantsCommun.VACANCY);
    DatabaseReference ref2 = con.getRef().child(ConstantsCommun.APP_USER);
    DatabaseReference ref3 = con.getRef().child(ConstantsCommun.APP_AGENCE);
    private long maxID = 0;
    private long cusID = 0;

    public OutilsCommun() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String val = "";
                if(dataSnapshot.exists()) {
                    maxID = dataSnapshot.getChildrenCount(); //get data count

                    String nextID = Long.toString(maxID); // convert into string

                    for(DataSnapshot temp : dataSnapshot.getChildren()){
                        val = temp.getKey();
                        //check if it's in the database
                        while (val.equals(ConstantsCommun.VACANCY_ID +nextID)){
                            maxID++;
                            nextID = Long.toString(maxID);
                        }
                    }

                    maxID = Long.parseLong(nextID);
                }else{
                    maxID = 1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    String val = "";
                    cusID = dataSnapshot.getChildrenCount();

                    String nextID = Long.toString(cusID);
                    for(DataSnapshot temp : dataSnapshot.getChildren()){
                        val = temp.getKey();
                        while (val.equals(ConstantsCommun.APP_USER_ID +nextID)){
                            cusID++;
                            nextID = Long.toString(cusID);
                        }
                    }


                    cusID = Long.parseLong(nextID);

                }else{
                    cusID = 1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    String val = "";
                    cusID = dataSnapshot.getChildrenCount();

                    String nextID = Long.toString(cusID);
                    for(DataSnapshot temp : dataSnapshot.getChildren()){
                        val = temp.getKey();
                        while (val.equals(ConstantsCommun.APP_AGENCE_ID +nextID)){
                            cusID++;
                            nextID = Long.toString(cusID);
                        }
                    }


                    cusID = Long.parseLong(nextID);

                }else{
                    cusID = 1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public long getNextID(){

        return maxID;
    }

    public long getCusID() {
        return cusID;
    }
}
