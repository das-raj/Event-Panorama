package com.example.atmiya.eventpanorama;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aarohi.eventpanaroma.CreateEvents;
import com.example.atmiya.eventpanorama.models.CollegeProfile;
import com.example.atmiya.eventpanorama.models.EventView;
import com.example.atmiya.eventpanorama.models.StudentProfile;
import com.example.atmiya.eventpanorama.models.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, EventViewAdapter.EventViewAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private TextView mUsername;
    private TextView mUserEmail;
    private EventViewAdapter mEventViewAdapter;
    private ProgressBar mProgressBar;

    private TextView mUserNameNav;
    private TextView mUserEmailNav;

    EventView[] events;

    //Firebase realtime database
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference databaseReference;
    private String FIREBASE_URL = "/events/";

    //profile
    UserProfile userProfile;

    //sliding menu options
    private MenuItem mNavCreateEvent;
    private MenuItem mNavUpdateEvent;
    private MenuItem mNavViewStats;
    private MenuItem mNavAssignPrivileges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //checking if user authenticated else direct to login activity
        if(FirebaseAuth.getInstance().getCurrentUser() == null)
            startActivity(new Intent(MainActivity.this, LoginHomeActivity.class));
        else {
            setContentView(R.layout.activity_main);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();


            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            View headerView = navigationView.getHeaderView(0);
            mUserNameNav = (TextView) headerView.findViewById(R.id.tv_nav_name);
            mUserEmailNav = (TextView) headerView.findViewById(R.id.tv_nav_email);
            getNavigationUsername();
            mUserEmailNav.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

            Menu menuNav = navigationView.getMenu();
            mNavCreateEvent =(MenuItem) menuNav.findItem(R.id.nav_create_event);
            mNavUpdateEvent =(MenuItem) menuNav.findItem(R.id.nav_update_event);
            mNavViewStats =(MenuItem) menuNav.findItem(R.id.nav_view_stats);
            mNavAssignPrivileges =(MenuItem) menuNav.findItem(R.id.nav_assign_privileges);
            initSlidingBarOptions();

            mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

            initFirebase();
            mRecyclerView = (RecyclerView) findViewById(R.id.rv_events_view);
            mEventViewAdapter = new EventViewAdapter(MainActivity.this, MainActivity.this);
            mRecyclerView.setAdapter(mEventViewAdapter);
            RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(mLayoutManager1);
            mEventViewAdapter.setData(events);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_create_event) {
            startActivity(new Intent(MainActivity.this, CreateEvents.class));
        } else if (id == R.id.nav_update_event) {
            startActivity(new Intent(MainActivity.this, EventList.class));
            RecyclerViewAdapter.activity = "update";

        } else if (id == R.id.nav_view_stats) {
            startActivity(new Intent(MainActivity.this, EventList.class));
            RecyclerViewAdapter.activity = "stats";

        } else if (id == R.id.nav_assign_privileges) {

        } else if (id == R.id.nav_logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, LoginHomeActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void initFirebase() {
        try{
            mProgressBar.setVisibility(View.VISIBLE);
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            //mFirebaseDatabase.setPersistenceEnabled(true);

            databaseReference = mFirebaseDatabase.getReference(FIREBASE_URL);

            databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                events = new EventView[(int)dataSnapshot.getChildrenCount()];
                int i=0;
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    events[i] = new EventView();
                    events[i] = childDataSnapshot.getValue(EventView.class);
                    events[i].setDocumentId(childDataSnapshot.getKey());
                    i++;
                }
                mEventViewAdapter.setData(events);
                mProgressBar.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });}
        catch (Exception e){
            Log.e("firebase","error",e);
        }
    }

    @Override
    public void onClick(int position) {
        Intent i = new Intent(MainActivity.this, EventRegisterActivity.class);
        i.putExtra("data",events[position]);
        startActivity(i);
    }

    void getNavigationUsername(){
        DatabaseReference studentProfile = FirebaseDatabase.getInstance().getReference("studentprofile");
        DatabaseReference collegeProfile = FirebaseDatabase.getInstance().getReference("collegeprofile");

        Query student = studentProfile.orderByChild("studentId")
                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        student.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    StudentProfile profile = childDataSnapshot.getValue(StudentProfile.class);
                    Toast.makeText(MainActivity.this,"profile "+ profile.getName(), Toast.LENGTH_SHORT).show();
                    if(profile.getName() != null){
                        Toast.makeText(MainActivity.this, profile.getName(), Toast.LENGTH_SHORT).show();
                        mUserNameNav.setText(profile.getName());
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       Query college = collegeProfile.orderByChild("collegeId")
                            .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());

        college.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    CollegeProfile profile = childDataSnapshot.getValue(CollegeProfile.class);
                    if(profile.getName() != null){
                        Toast.makeText(MainActivity.this, profile.getName(), Toast.LENGTH_SHORT).show();
                        mUserNameNav.setText(profile.getName());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void initSlidingBarOptions(){
        DatabaseReference studentProfile = FirebaseDatabase.getInstance().getReference("studentprofile");
        DatabaseReference collegeProfile = FirebaseDatabase.getInstance().getReference("collegeprofile");

        Query student = studentProfile.orderByChild("studentId")
                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        student.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    StudentProfile profile = childDataSnapshot.getValue(StudentProfile.class);
                   if(profile.getName() != null){
                       Toast.makeText(MainActivity.this, String.valueOf(profile.getPriviliged()), Toast.LENGTH_SHORT).show();
                       if(profile.getPriviliged() == true){
                            mNavCreateEvent.setVisible(true);
                            mNavUpdateEvent.setVisible(true);
                            mNavViewStats.setVisible(true);
                    }

                }
            }
            }

            /*@Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }*/


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }});

        Query college = collegeProfile.orderByChild("collegeId")
                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());

        college.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    CollegeProfile profile = childDataSnapshot.getValue(CollegeProfile.class);
                    if(profile.getName() != null){
                        mNavCreateEvent.setVisible(true);
                        mNavUpdateEvent.setVisible(true);
                        mNavViewStats.setVisible(true);
                        mNavAssignPrivileges.setVisible(true);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}


