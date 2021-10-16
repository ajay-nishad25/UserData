package com.ktech.userdata;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    EditText aName, aEmail, aMobileNo, aRikshawNo, aAddress;
    TextView aAddPhoto, aClearData;
    Button aUpload, aViewDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // opening ViewActivity
        aViewDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this , ViewDatabaseActivity.class));
            }
        });

    }
}