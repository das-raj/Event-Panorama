package com.example.atmiya.eventpanorama;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.atmiya.eventpanorama.models.EventModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";
    public static ArrayList<String> eventNames = new ArrayList<>();
    public static ArrayList<String> eventImages = new ArrayList<>();
    private Context mContext;

    public static ArrayList<String> eventId = new ArrayList<>();
    public static ArrayList<String> eventEntries = new ArrayList<>();
    public static ArrayList<EventModel> mEvent = new ArrayList<>();

    public static String activity = new String("");

    public RecyclerViewAdapter(ArrayList<String> eventNames, ArrayList<String> eventImages, Context mContext) {
        this.eventNames = eventNames;
        this.eventImages = eventImages;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_recycler_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder: ");
        Glide.with(mContext)
                .asBitmap()
                .load(eventImages.get(i))
                .into(viewHolder.image);

        String event_title = eventNames.get(i);
        if(event_title.length() > 27)
        {
            event_title = event_title.substring(0, 24) + "...";
        }
        viewHolder.imageName.setText(eventNames.get(i));
        viewHolder.imageDesc.setText(mEvent.get(i).eventDescription);
        viewHolder.collegeName.setText(mEvent.get(i).college);
        //if 25 or less okay
        //if > 25, substring 22 and append ...
        String longDesc = mEvent.get(i).eventBriefDescription;
        if(longDesc.length() > 24)
        {
            longDesc = longDesc.substring(0, 21) + "...";
        }
        viewHolder.longDesc.setText(longDesc);

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " +eventImages.get(i));

                Intent intent = new Intent();
                intent.setClass(mContext, UpdateEvents.class);
                if(activity.equals("update"))
                {
                    intent.setClass(mContext, UpdateEvents.class);
                }
                else{
                    intent.setClass(mContext, Stats.class);
                }

                intent.putExtra("key", eventId.get(i));
                intent.putExtra("title", eventNames.get(i));
                intent.putExtra("entries", eventEntries.get(i));
                intent.putExtra("imageUrl", eventImages.get(i));
                intent.putExtra("mEvent", mEvent.get(i));
                mContext.startActivity(intent);




                //Toast.makeText(mContext, eventNames.get(i), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView imageName;
        RelativeLayout parentLayout;
        TextView imageDesc;
        TextView collegeName;
        TextView longDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.event_image);
            imageName = itemView.findViewById(R.id.event_title_text);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            imageDesc = itemView.findViewById(R.id.event_desc_text);
            collegeName = itemView.findViewById(R.id.collegeName);
            longDesc  = itemView.findViewById(R.id.long_desc);

        }
    }
}
