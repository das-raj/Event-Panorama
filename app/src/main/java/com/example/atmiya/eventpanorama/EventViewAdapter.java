package com.example.atmiya.eventpanorama;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.atmiya.eventpanorama.models.EventView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class EventViewAdapter extends RecyclerView.Adapter<EventViewAdapter.EventViewHolder> {

    EventView[] events;
    Context mContext;
    private final EventViewAdapterOnClickHandler mClickHandler;

    public EventViewAdapter(Context context,EventViewAdapterOnClickHandler clickHandler){
        this.mContext = context;
        this.mClickHandler = clickHandler;
    }

    public interface EventViewAdapterOnClickHandler {
        void onClick(int position);
    }

    class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ConstraintLayout row;
        //public ImageView mEventLogo;
        public TextView mEventTitleTextView;
        public TextView mEventOnelinerTextView;
        public CircleImageView mEventLogo;
        public View view;
        public EventViewHolder(View view){
            super(view);
            this.view = view;
            row = (ConstraintLayout) view.findViewById(R.id.cl_row);
            //mEventLogo = (ImageView) view.findViewById(R.id.img_event);
            mEventTitleTextView = (TextView) view.findViewById(R.id.tv_event_title);
            mEventOnelinerTextView = (TextView) view.findViewById(R.id.tv_event_oneliner);
            mEventLogo = (CircleImageView) view.findViewById(R.id.img_event);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(adapterPosition);
        }
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.events_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EventViewHolder eventViewHolder, int i) {
        eventViewHolder.mEventTitleTextView.setText(events[i].getEventName());
        eventViewHolder.mEventOnelinerTextView.setText(events[i].getEventBriefDescription());
        Picasso.get()
                .load(events[i].getImagePrimaryUrl())
                .into(eventViewHolder.mEventLogo);
    }

    @Override
    public int getItemCount() {
        if(events != null)
            return events.length;
        else
            return 0;

    }

    public void setData(EventView[] events){
        this.events = events;
        notifyDataSetChanged();
    }
}
