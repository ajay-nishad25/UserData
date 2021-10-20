package com.ktech.userdata.DataAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.ktech.userdata.DataModel.UploadDataModel;
import com.ktech.userdata.R;

// we have to extends the Adapter class with FirebaseRecyclerAdapter which contains model class and viewholder class
public class ViewDataAdapter extends FirebaseRecyclerAdapter<UploadDataModel, ViewDataAdapter.MyViewHolder> {

    // Default constructors
    public ViewDataAdapter(@NonNull FirebaseRecyclerOptions<UploadDataModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull UploadDataModel model) {

        holder.sMobileNoAsID.setText(model.getmMobileNo());
        holder.sName.setText(model.getmName());
        holder.sMobileNo.setText(model.getmMobileNo());
        holder.sRikshawNo.setText(model.getmRikshawNo());

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_data_model_sample, parent, false);
        return new MyViewHolder(view);
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView sMobileNoAsID, sName, sMobileNo, sRikshawNo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            sMobileNoAsID = itemView.findViewById(R.id.numberAsID_textView_sUserDataModel);
            sName = itemView.findViewById(R.id.name_textView_sUserDataModel);
            sMobileNo = itemView.findViewById(R.id.mobileNo_textView_sUserDataModel);
            sRikshawNo = itemView.findViewById(R.id.rikshawNo_textView_sUserDataModel);

        }
    }

}
