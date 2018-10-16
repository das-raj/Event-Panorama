package com.example.atmiya.eventpanorama;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atmiya.eventpanorama.models.EventView;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class EventRegistrationForm extends AppCompatActivity {

    private TextView mEventName;
    private ImageView mCoverImage;
    private Button mSubmitBtn;
    private TextView mRegUserName;
    private TextView mRegClgName;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mFirebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_registration_form);
        mEventName = (TextView) findViewById(R.id.tv_event_form_title);
        mRegUserName = (TextView) findViewById(R.id.et_reg_user_name);
        mRegClgName = (TextView) findViewById(R.id.et_reg_clg_name);

        final EventView eventView = (EventView) getIntent().getSerializableExtra("data");

        mSubmitBtn = (Button) findViewById(R.id.btn_submit_reg_form);
        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirebaseDatabase = FirebaseDatabase.getInstance();
                mDatabaseReference = mFirebaseDatabase.getReference("/events/"+eventView.getDocumentId());

                String registrationUserName = mRegUserName.getText().toString();
                String registrationCollegeName = mRegClgName.getText().toString();
                if(!registrationCollegeName.trim().equals("") && !registrationUserName.trim().equals("")) {
                    String key = mDatabaseReference.child("eventRegistrations").push().getKey();
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("userName", registrationUserName);
                    map.put("collegeName", registrationCollegeName);

                    mDatabaseReference.child("eventRegistrations/" + key).setValue(map);
                    Toast.makeText(EventRegistrationForm.this, "Submitted Successfully!! Mark the date", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(EventRegistrationForm.this, "Enter data, it is blank", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mEventName.setText(eventView.getEventName());
        mCoverImage = (ImageView) findViewById(R.id.img_form_cover);
        Picasso.get()
                .load(eventView.getImageSecondaryUrl())
                .into(mCoverImage);

    }

}
