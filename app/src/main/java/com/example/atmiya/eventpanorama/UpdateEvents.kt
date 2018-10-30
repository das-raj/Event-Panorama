package com.example.atmiya.eventpanorama

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class UpdateEvents : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_events)

        supportActionBar!!.hide() // hide the title bar
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        var eventTitle = intent.getStringExtra("title")
        val mUpdateTitle: TextView = findViewById(R.id.up_event_title)
        mUpdateTitle.text = eventTitle


        var updateEvent: Button = findViewById(R.id.ue_update_event)
        updateEvent.setOnClickListener{

            val database = FirebaseDatabase.getInstance()
            val myRef = database.reference.child("events").child(intent.getStringExtra("key"))

            val uTitle: EditText = findViewById(R.id.update_title)
            val uLocation: EditText = findViewById(R.id.update_location)
            val uEntries: EditText = findViewById(R.id.update_entries)


            if (!uTitle.text.toString().equals("")) {
                myRef.child("eventName").setValue(uTitle.text.toString())
            }



            if (!uLocation.text.toString().equals("")) {
                myRef.child("location").setValue(uLocation.text.toString())
            }


            if (uEntries.text.toString() != "") {
                myRef.child("eventEntries").setValue(uEntries.text.toString().toInt())
            }

            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show()//

            //back to main activity
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)




        }
    }
}
