package com.example.atmiya.eventpanorama;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.atmiya.eventpanorama.models.UserProfile;
import com.example.atmiya.eventpanorama.utils.DatePickerFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class UserProfileSignUp extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    Date mEventDate = new Date();
    boolean dateSet = false;
    Button mSubmit;
    Button dateBtn;
    EditText mUsername;
    EditText mUserAddress;
    EditText mUserContact;
    EditText mUserCollege;
    TextView mDateOfBirth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_sign_up);

        dateBtn = findViewById(R.id.dateBtn);
        mSubmit = findViewById(R.id.btn_submit_reg_form);
        mUsername = findViewById(R.id.tv_username);
        mUserAddress = findViewById(R.id.tv_user_address);
        mUserContact = findViewById(R.id.tv_user_contact);
        mUserCollege = findViewById(R.id.tv_user_college);

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserProfile profile = new UserProfile();
                profile.setName(mUsername.getText().toString());
                profile.setAddress(mUserAddress.getText().toString());
                profile.setCollegeName(mUserCollege.getText().toString());
                profile.setContactNumber(mUserContact.getText().toString());
                profile.setDateOfBirth(mEventDate);
                profile.setPrivileged(true);
                profile.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                profile.setEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                uploadProfile(profile);
                Intent i = new Intent(UserProfileSignUp.this, MainActivity.class);
                i.putExtra("userProfile",profile);
                startActivity(i);
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String dateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        mDateOfBirth = this.findViewById(R.id.dateOfBirth);
        mDateOfBirth.setText(dateString);
        dateSet = true;
        mEventDate = c.getTime();
        //mEventDateString = dateString;
    }

    void uploadProfile(UserProfile profile){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mRef = database.getReference();
        mRef.push().child("userprofile");
        String userKey = mRef.child("userprofile").push().getKey();
        mRef.child("userprofile").child(userKey).setValue(profile);
    }
}
