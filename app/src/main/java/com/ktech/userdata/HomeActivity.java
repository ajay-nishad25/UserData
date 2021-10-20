package com.ktech.userdata;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ktech.userdata.DataModel.UploadDataModel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    //Firebase related
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    //Imported Classes if any


    //Constants related
    public static final int CAMERA_PERMISSION_CODE = 100;
    public static final int CAMERA_REQUEST_CODE = 101;

    //View's related
    ImageView aUserImage;
    EditText aName, aEmail, aMobileNo, aRikshawNo, aAddress;
    TextView aAddPhoto, aClearData;
    Button aUpload, aViewDatabase;
    RadioButton aMale, aFemale;
    ProgressDialog progressDialog;

    //Datatype related
    String currentPhotoPath;
    String gender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Reference
        aUserImage = findViewById(R.id.userImage_imageView_aHome);
        aName = findViewById(R.id.name_editText_aHome);
        aEmail = findViewById(R.id.email_editText_aHome);
        aMobileNo = findViewById(R.id.mobileNo_editText_aHome);
        aRikshawNo = findViewById(R.id.rikshawNumber_editText_aHome);
        aAddress = findViewById(R.id.address_EditText_aHome);
        aAddPhoto = findViewById(R.id.addPhoto_textView_aHome);
        aClearData = findViewById(R.id.clearAboveData_textView_aHome);
        aUpload = findViewById(R.id.upload_Button_aHome);
        aViewDatabase = findViewById(R.id.viewDatabase_Button_aHome);
        aMale = findViewById(R.id.male_RadioButton_aHome);
        aFemale = findViewById(R.id.female_RadioButton_aHome);

        progressDialog = new ProgressDialog(this);


        //Capturing user image
        aAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermission();
            }
        });

        //Clearing above data
        aClearData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aName.setText(null);
                aEmail.setText(null);
                aMobileNo.setText(null);
                aRikshawNo.setText(null);
                aAddress.setText(null);
                aUserImage.setVisibility(View.GONE);
            }
        });

        // opening ViewDatabaseActivity
        aViewDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ViewDatabaseActivity.class));
            }
        });

    }    // OnCreate end's here

    // asking user for camera permission
    private void askCameraPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        } else {
            dispatchTakePictureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(getApplicationContext(), "Permission Required", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                File f = new File(currentPhotoPath);
                aUserImage.setVisibility(View.VISIBLE);
                aUserImage.setImageURI(Uri.fromFile(f));
                Log.d("tag", "Absolute Url of Image is : " + Uri.fromFile(f));

                // when user click on upload button
                aUpload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        uploadAllData(f.getName(), Uri.fromFile(f));
                    }
                });


            }

        }

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    // taking image url and validating given data and putting to database
    public void uploadAllData(String fileName, Uri imageUri) {

        //start validating given data of user
        String tempName, tempEmail, tempMobileNo, tempRikshawNo, tempAddress;
        tempName = aName.getText().toString();
        tempEmail = aEmail.getText().toString();
        tempMobileNo = aMobileNo.getText().toString();
        tempRikshawNo = aRikshawNo.getText().toString();
        tempAddress = aAddress.getText().toString();


        if (TextUtils.isEmpty(tempName)) {
            aName.setError("Required");
            return;
        }
        if (TextUtils.isEmpty(tempEmail)) {
            aEmail.setError("Required");
            return;
        }
        if (TextUtils.isEmpty(tempMobileNo)) {
            aMobileNo.setError("Required");
            return;
        }

        if (tempMobileNo.length() < 10) {
            aMobileNo.setError("Enter Valid no.");
            return;
        }
        if (TextUtils.isEmpty(tempRikshawNo)) {
            aRikshawNo.setError("Required");
            return;
        }
        if (TextUtils.isEmpty(tempAddress)) {
            aAddress.setError("Required");
            return;
        }
        if (aMale.isChecked()) {
            gender = "Male";
        }
        if (aFemale.isChecked()) {
            gender = "Female";
        }

        // making progressDialog visible
        progressDialog.setTitle("Uploading data...");
        progressDialog.setMessage("Please wait for a second");
        progressDialog.setCancelable(false);
        progressDialog.create();
        progressDialog.show();

        //uploading user image to firebase storage
        StorageReference image = storageReference.child("userImages/ " + fileName);
        image.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //downloading user image url
                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        //Now putting all data to firebase realtime database including user image url
                        UploadDataModel uploadDataModel = new UploadDataModel(uri.toString(), tempName, tempEmail, tempMobileNo, tempRikshawNo, gender, tempAddress);
                        databaseReference.child("DATA").child(tempMobileNo).setValue(uploadDataModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                //Vanishing out all data from activity
                                aName.setText(null);
                                aEmail.setText(null);
                                aMobileNo.setText(null);
                                aRikshawNo.setText(null);
                                aAddress.setText(null);
                                aUserImage.setVisibility(View.GONE);
                                //dismissing progressDialog form screen
                                progressDialog.dismiss();
                                //Toast to announce that task is successful
                                Toast.makeText(getApplicationContext(), "upload successful", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "upload failed !", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "upload successful", Toast.LENGTH_SHORT).show();
            }
        });


    }


}