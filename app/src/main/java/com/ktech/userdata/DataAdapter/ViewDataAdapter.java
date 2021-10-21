package com.ktech.userdata.DataAdapter;



import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ktech.userdata.DataModel.UploadDataModel;
import com.ktech.userdata.R;
import com.squareup.picasso.Picasso;

// we have to extends the Adapter class with FirebaseRecyclerAdapter which contains model class and viewholder class
public class ViewDataAdapter extends FirebaseRecyclerAdapter<UploadDataModel, ViewDataAdapter.MyViewHolder> {

    public static final int CALL_PERMISSION_CODE = 102;
    Context context;
    

    // Default constructors
    public ViewDataAdapter(@NonNull FirebaseRecyclerOptions<UploadDataModel> options , Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull UploadDataModel model) {

        //Showing user image using picasso implementation
        Picasso.get().load(model.getmUserImageUrl()).into(holder.sImageView);

        //Showing users mobile number as a Id
        holder.sMobileNoAsID.setText(model.getmMobileNo());

        //Showing users name
        holder.sName.setText(model.getmName());

        //Showing users mobile
        holder.sMobileNo.setText(model.getmMobileNo());

        //Showing users rikshaw no
        holder.sRikshawNo.setText(model.getmRikshawNo());

        //Calling current user
        holder.sCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION_CODE);
                } else {
                    String callNumber = "tel:" + model.getmMobileNo();
                    Intent call = new Intent(Intent.ACTION_CALL);
                    call.setData(Uri.parse(callNumber));
                    context.startActivity(call);
                }
            }
        });

        //Deleting user for firebase database
        holder.sDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Delete")
                        .setMessage("Are you sure want to delete")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // this is main step which took my whole day
                                String number = model.getmMobileNo();
                                // for deleting i will take the path of sub child i.e getRef() and getKey()
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                                        .getReference()
                                        .child("DATA")
                                        .child(number);


                                FirebaseDatabase.getInstance()
                                        .getReference()
                                        .child("DATA")
                                        .child(databaseReference.getRef().getKey())
                                        .removeValue()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(context.getApplicationContext(), "User deleted", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context.getApplicationContext(), "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                });

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               dialog.dismiss();
                            }
                        })
                        .create()
                        .show();




            }
        });

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_data_model_sample, parent, false);
        return new MyViewHolder(view);
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView sImageView;
        TextView sMobileNoAsID, sName, sMobileNo, sRikshawNo, sCall, sDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sImageView = itemView.findViewById(R.id.userImage_imageView_sUserDataModel);

            sMobileNoAsID = itemView.findViewById(R.id.numberAsID_textView_sUserDataModel);
            sName = itemView.findViewById(R.id.name_textView_sUserDataModel);
            sMobileNo = itemView.findViewById(R.id.mobileNo_textView_sUserDataModel);
            sRikshawNo = itemView.findViewById(R.id.rikshawNo_textView_sUserDataModel);
            sCall = itemView.findViewById(R.id.call_TextView_sUserDataModel);
            sDelete = itemView.findViewById(R.id.delete_TextView_sUserDataModel);

        }
    }

}
