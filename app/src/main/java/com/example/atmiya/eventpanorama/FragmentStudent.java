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

import com.example.atmiya.eventpanorama.models.StudentProfile;
import com.example.atmiya.eventpanorama.models.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentStudent extends Fragment {
    private EditText mStudentNameEditText;
    private EditText mStudentAddressEditText;
    private EditText mStudentCollegeNameEditText;
    private EditText mStudentEmailEditText;
    private EditText mStudentContactEditText;
    private EditText mStudentBirthdateEditText;
    private EditText mStudentPasswordEditText;

    private Button mStudentProfileSubmit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_student_login, container, false);

        mStudentNameEditText = rootView.findViewById(R.id.et_student_name);
        mStudentAddressEditText = rootView.findViewById(R.id.et_student_address);
        mStudentCollegeNameEditText = rootView.findViewById(R.id.et_student_college_name);
        mStudentContactEditText = rootView.findViewById(R.id.et_student_contact);
        mStudentBirthdateEditText = rootView.findViewById(R.id.et_student_birthdate);
        mStudentEmailEditText = rootView.findViewById(R.id.et_student_email);
        mStudentPasswordEditText = rootView.findViewById(R.id.et_student_password);

        mStudentProfileSubmit = rootView.findViewById(R.id.btn_student_submit);
        mStudentProfileSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mStudentEmailEditText.getText().toString();
                String password = mStudentPasswordEditText.getText().toString();
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
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getContext(), "Welcome!!", Toast.LENGTH_LONG).show();
                            StudentProfile studentProfile = new StudentProfile();

                            studentProfile.setStudentId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            studentProfile.setName(mStudentNameEditText.getText().toString());
                            studentProfile.setAddress(mStudentAddressEditText.getText().toString());
                            studentProfile.setCollegeName(mStudentCollegeNameEditText.getText().toString());
                            studentProfile.setContactNumber(mStudentContactEditText.getText().toString());
                            studentProfile.setBirthdate(mStudentBirthdateEditText.getText().toString());
                            studentProfile.setPriviliged(false);

                            uploadProfile(studentProfile);
                        } else {
                            // If sign in fails, display a message to the user.
                            FirebaseAuthException e = (FirebaseAuthException )task.getException();
                            Toast.makeText(getActivity(), "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }

    void uploadProfile(StudentProfile profile){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mRef = database.getReference();
        String userKey = mRef.child("studentprofile").push().getKey();
        mRef.child("studentprofile").child(userKey).setValue(profile);
        startActivity(new Intent(getActivity(), MainActivity.class));
    }
}
