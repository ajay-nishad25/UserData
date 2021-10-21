package com.ktech.userdata;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ktech.userdata.DataAdapter.ViewDataAdapter;
import com.ktech.userdata.DataModel.UploadDataModel;

public class ViewDatabaseActivity extends AppCompatActivity {

    //Firebase related
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("DATA");


    //Imported Classes if any
    ViewDataAdapter adapter;


    //Constant's related


    //View's related
    RecyclerView userDataRecyclerview;
    TextView totalUsers;


    //Datatype related
    int countUsers = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_database);

        //Reference
        userDataRecyclerview = findViewById(R.id.recyclerView_aViewDatabase);
        totalUsers = findViewById(R.id.totalDataValue_textView_aViewDatabase);

        //Code for total number of users present in firebase realtime database
        databaseReference.child("DATA").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                countUsers = (int)snapshot.getChildrenCount();
                totalUsers.setText(Integer.toString(countUsers));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //Code for Recyclerview start's here
        //This is created because ViewDataAdapter constructor required options
        FirebaseRecyclerOptions<UploadDataModel> options = new FirebaseRecyclerOptions.Builder<UploadDataModel>().setQuery(databaseReference, UploadDataModel.class).build();
        adapter = new ViewDataAdapter(options , this);
        userDataRecyclerview.setAdapter(adapter);
        userDataRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        ////Code for Recyclerview end's here

    }

    // onStart and onStop is essential for FirebaseRecyclerOptions
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}