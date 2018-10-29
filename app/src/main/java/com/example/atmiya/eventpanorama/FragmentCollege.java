package com.example.atmiya.eventpanorama;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atmiya.eventpanorama.models.CollegeProfile;
import com.example.atmiya.eventpanorama.models.StudentProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentCollege extends Fragment {
    private EditText mCollegeNameEditText;
    private EditText mCollegeAddressEditText;
    private EditText mCollegePOCEditText;
    private EditText mCollegePOCEmailEditText;
    private EditText mCollegePOCPhoneEditText;
    private EditText mCollegePassword;

    private Button mCollegeProfileSubmit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_college_login, container, false);

        mCollegeNameEditText = rootView.findViewById(R.id.et_college_name);
        mCollegeAddressEditText = rootView.findViewById(R.id.et_college_address);
        mCollegePOCEditText = rootView.findViewById(R.id.et_college_poc);
        mCollegePOCEmailEditText = rootView.findViewById(R.id.et_college_email);
        mCollegePOCPhoneEditText = rootView.findViewById(R.id.et_college_phone);
        mCollegePassword = rootView.findViewById(R.id.et_college_password);

        mCollegeProfileSubmit = rootView.findViewById(R.id.btn_college_submit);
        mCollegeProfileSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mCollegePOCEmailEditText.getText().toString();
                String password = mCollegePassword.getText().toString();
                createUser(email, password);
            }
        });

        return rootView;
    }

    void createUser(String email, String password){
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            CollegeProfile collegeProfile = new CollegeProfile();

                            collegeProfile.setCollegeId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            collegeProfile.setName(mCollegeNameEditText.getText().toString());
                            collegeProfile.setAddress(mCollegeAddressEditText.getText().toString());
                            collegeProfile.setPointOfContact(mCollegePOCEditText.getText().toString());
                            collegeProfile.setPocEmail(mCollegePOCEmailEditText.getText().toString());
                            collegeProfile.setPocPhone(mCollegePOCPhoneEditText.getText().toString());

                            uploadProfile(collegeProfile);

                        } else {
                            // If sign in fails, display a message to the user.
                            FirebaseAuthException e = (FirebaseAuthException )task.getException();
                            Toast.makeText(getActivity(), "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }


    void uploadProfile(CollegeProfile profile){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mRef = database.getReference();
        String userKey = mRef.child("collegeprofile").push().getKey();
        mRef.child("collegeprofile").child(userKey).setValue(profile);
        Toast.makeText(getContext(), "Welcome", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getActivity(), MainActivity.class));
    }
}
