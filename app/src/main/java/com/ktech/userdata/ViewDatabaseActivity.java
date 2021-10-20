package com.ktech.userdata;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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


    //Datatype related


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_database);

        //Reference
        userDataRecyclerview = findViewById(R.id.recyclerView_aViewDatabase);

        //This is created because ViewDataAdapter constructor required options
        FirebaseRecyclerOptions<UploadDataModel> options = new FirebaseRecyclerOptions.Builder<UploadDataModel>().setQuery(databaseReference, UploadDataModel.class).build();

        adapter = new ViewDataAdapter(options);
        userDataRecyclerview.setAdapter(adapter);
        userDataRecyclerview.setLayoutManager(new LinearLayoutManager(this));


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