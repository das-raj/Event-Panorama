package com.example.atmiya.eventpanorama;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.atmiya.eventpanorama.models.EventView;
import com.squareup.picasso.Picasso;

public class EventRegisterActivity extends AppCompatActivity {
    private TextView mEventTitle;
    private TextView mEventDate;
    private TextView mEventLocation;
    private TextView mEventOneliner;
    private TextView mEventDescription;
    private ImageView mEventCoverPhoto;
    private Button mEventRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_register);

        mEventTitle = (TextView) findViewById(R.id.tv_event_registration_event_title);
        mEventDate = (TextView) findViewById(R.id.tv_event_registration_date);
        mEventLocation = (TextView) findViewById(R.id.tv_event_registration_location);
        mEventOneliner = (TextView) findViewById(R.id.tv_event_registration_onliner);
        mEventDescription = (TextView) findViewById(R.id.tv_event_registration_description);
        mEventCoverPhoto = (ImageView) findViewById(R.id.imageView2);
        mEventRegistration = (Button) findViewById(R.id.btn_register);
        mEventRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EventRegisterActivity.this, EventRegistrationForm.class);
                i.putExtra("data",getIntent().getSerializableExtra("data"));
                startActivity(i);
            }
        });

        Intent i = getIntent();
        EventView eventData = (EventView) i.getSerializableExtra("data");
        Picasso.get()
                .load(eventData.getImageSecondaryUrl())
                .into(mEventCoverPhoto);
        setData(eventData);
    }

    public void setData(EventView eventData){
        mEventTitle.setText(eventData.getEventName());
        mEventDate.setText(eventData.getEventDateString());
        mEventLocation.setText(eventData.getLocation());
        mEventOneliner.setText(eventData.getEventBriefDescription());
        mEventDescription.setText(eventData.getEventDescription());

    }
}
