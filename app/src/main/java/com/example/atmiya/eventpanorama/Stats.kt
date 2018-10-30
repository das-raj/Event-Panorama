package com.example.atmiya.eventpanorama

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.atmiya.eventpanorama.models.EventModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import de.hdodenhof.circleimageview.CircleImageView

class Stats : AppCompatActivity() {

    var totalRegistrations: Int = 0


    private val eventListener = object: ValueEventListener {
        override fun onCancelled(p0: DatabaseError) {

        }

        override fun onDataChange(p0: DataSnapshot) {

            var mEvent: EventModel = intent.getParcelableExtra("mEvent")
            for((i, childSnapshot: DataSnapshot) in p0.children.withIndex())
            {
                val keyId: String = childSnapshot.key!!
                if(keyId.equals(intent.getStringExtra("key")))
                {
                    totalRegistrations = childSnapshot.child("eventRegistrations").childrenCount.toInt()
                    findViewById<TextView>(R.id.registrations_text).text = "Registrations $totalRegistrations"
                    val pr:Int = ((totalRegistrations.toDouble() /mEvent.eventEntries.toDouble()) * 100.0).toInt()
                    findViewById<ProgressBar>(R.id.registration_progress).progress = pr

                    break
                }


            }

        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        supportActionBar!!.hide() // hide the title bar
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)


        val database = FirebaseDatabase.getInstance()
        val myRef = database.reference
        val mEvent: EventModel = intent.getParcelableExtra("mEvent")
        myRef.child("events").addValueEventListener(eventListener)

        val title: TextView = findViewById(R.id.re_event_title)
        title.text = mEvent.eventName


        Glide.with(this@Stats)
                .asBitmap()
                .load(mEvent.imagePrimaryUrl)
                .into(findViewById<CircleImageView>(R.id.stats_event_image))

    }
}
