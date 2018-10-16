package com.example.atmiya.eventpanorama;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.atmiya.eventpanorama.models.EventView;
import com.squareup.picasso.Picasso;

public class EventRegistrationForm extends AppCompatActivity {

    private TextView mEventName;
    private ImageView mCoverImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_registration_form);
        mEventName = (TextView) findViewById(R.id.tv_event_form_title);
        EventView eventView = (EventView) getIntent().getSerializableExtra("data");
        mEventName.setText(eventView.getEventName());
        mCoverImage = (ImageView) findViewById(R.id.img_form_cover);
        Picasso.get()
                .load(eventView.getImageSecondaryUrl())
                .into(mCoverImage);
    }
}
