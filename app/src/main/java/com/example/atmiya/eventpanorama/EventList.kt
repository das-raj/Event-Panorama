package com.example.atmiya.eventpanorama

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ProgressBar
import com.example.atmiya.eventpanorama.models.EventModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.concurrent.timerTask


class EventList : AppCompatActivity() {


    private val mNames = ArrayList<String>()
    private val mImageUrls = ArrayList<String>()
    private val mEvents2 = ArrayList<EventModel>()
    private var mTotalEvents: Int = 0


    private val eventListener = object: ValueEventListener{
        override fun onCancelled(p0: DatabaseError) {

        }

        override fun onDataChange(p0: DataSnapshot) {
            mTotalEvents = p0.childrenCount.toInt()
            val mEvents: MutableList<EventModel> = MutableList(p0.childrenCount.toInt()) {EventModel()}
            for((i, childSnapshot: DataSnapshot) in p0.children.withIndex())
            {


                RecyclerViewAdapter.eventId.add(childSnapshot.key)
                val a:String = childSnapshot.child("eventEntries").getValue().toString()
                RecyclerViewAdapter.eventEntries.add(a)
                mEvents[i] = childSnapshot.getValue<EventModel>(EventModel::class.java)!!
                mEvents2.add(mEvents[i])
                RecyclerViewAdapter.mEvent.add(mEvents[i])


            }

            initImageBitmaps()

        }

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)


        //requestWindowFeature(Window.FEATURE_NO_TITLE) //will hide the title
        supportActionBar!!.hide() // hide the title bar
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)


        val database = FirebaseDatabase.getInstance()
        val myRef = database.reference

        //myRef.addListenerForSingleValueEvent(eventListener)
        myRef.child("events").orderByChild("createdUserId").equalTo(FirebaseAuth.getInstance().currentUser!!.uid)
                .addValueEventListener(eventListener)




    }

    private fun initImageBitmaps(){

        for (i in 0 until mEvents2.size) {
            mImageUrls.add(mEvents2[i].imagePrimaryUrl)
            val desc: String = mEvents2[i].eventName
            mNames.add(desc)
        }



        initRecyclerView()

    }

    private fun initRecyclerView() {
        val recyclerView: RecyclerView = this.findViewById(R.id.event_recycler)
        val adapter = RecyclerViewAdapter(mNames, mImageUrls, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        findViewById<ProgressBar>(R.id.recycler_loading).visibility = View.GONE


    }





}

