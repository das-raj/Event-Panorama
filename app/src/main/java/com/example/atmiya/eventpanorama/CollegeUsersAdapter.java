package com.example.atmiya.eventpanorama;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.atmiya.eventpanorama.models.UserProfile;
import com.firebase.ui.auth.data.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CollegeUsersAdapter extends RecyclerView.Adapter<CollegeUsersAdapter.CollegeUsersViewHolder> {

    private CollegeUsersOnClickHandler mOnClick;
    private ArrayList<UserProfile> userProfiles;

    CollegeUsersAdapter(CollegeUsersOnClickHandler mOnClick){
        this.mOnClick = mOnClick;
    }

    void setData(ArrayList<UserProfile> usersOfCollege){
        this.userProfiles = usersOfCollege;
        notifyDataSetChanged();
    }

    interface CollegeUsersOnClickHandler{
        void onClick(int i);
    }

    class CollegeUsersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public View view;

        CollegeUsersViewHolder(View view){
            super(view);
            this.view = view;
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mOnClick.onClick(adapterPosition);
        }
    }

    @NonNull
    @Override
    public CollegeUsersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.events_list_item; //change here
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new CollegeUsersAdapter.CollegeUsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollegeUsersViewHolder collegeUsersViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
/*
//INTO THE ASSIGN PRIVILEGES ACTIVITY IMPLEMENTS CollegeUsersAdapter.CollegeUsersOnClick

    //For college assign privileges
    private RecyclerView mCollegeUsersRecyclerView;
    private  CollegeUsersAdapter mCollegeUsersAdapter;
    ArrayList<UserProfile> userProfiles = new ArrayList<UserProfile>();

  void onClick(int i){
    FirebaseDatabase.getInstance().getReference("studentprofile").child("priviliged").setValue(true);
    //HIDE VISIBILITY OF iTH USER AS HE IS ASSIGNED PRIVILEGES
    userProfiles.remove(i);
    mCollegeUsersAdapter.setData(userProfiles);
    }

  onCreate()
    fetchUsers()
    mCollegeUsersRecyclerView = (RecyclerView) findViewById(R.id.rv_college_users); // change recyclyer view id here
    mCollegeUsersAdapter = new CollegeUsersAdapter(MainActivity.this); // change activity name acordingly
    mCollegeUsersRecyclerView.setAdapter(mCollegeUsersAdapter);
    RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext());
    mRecyclerView.setLayoutManager(mLayoutManager1);
    mCollegeUsersAdapter.setData(userProfiles);

  fetchUsers()
    String collegeName = (String) getIntent().getSerializableExtra("collegeName");
    FirebaseDatabase.getInstance().getReference("studentprofile")
                        .orderByChild("collegeName")
                        .equalTo(collegeName)
                        .addValueEventListener(listenerForCollegeUsers);

    ValueEventListener listenerForCollegeUsers = new ValueEventListener() {
        UserProfile[] userProfiles;
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            for(DataSnapshot user:dataSnapshot.getChildren()){
                userProfiles.add(user.getValue(UserProfile.class));
            }
            mCollegeUsersAdapter.setData(userProfiles); // CHANGE VARIABLE
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
//INTO IF CONDITION OF NAVIGATION MENU SELECTION OF MAIN ACTIVITY
    Intent i = new Intent();
    i.putExtra("collegeName",collegeName);
    startActivity(i);
*/