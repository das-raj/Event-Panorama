package com.example.aarohi.eventpanaroma

import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.WindowManager
import android.widget.*
import com.example.atmiya.eventpanorama.R
import com.example.atmiya.eventpanorama.utils.DatePickerFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.DateFormat
import java.util.*


class CreateEvents : AppCompatActivity(), DatePickerDialog.OnDateSetListener  {

    var dateSet: Boolean = false
    var mEventDate: Date = Date()
    var mEventDateString: String = ""
    var keyId: String = ""

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

        val c = Calendar.getInstance()
        c.set(Calendar.YEAR, year)
        c.set(Calendar.MONTH, month)
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        val dateString: String = DateFormat.getDateInstance(DateFormat.FULL).format(c.time)
        val eventDate: TextView = this.findViewById(R.id.event_date_text)
        eventDate.text = dateString
        dateSet = true
        mEventDate = c.time
        mEventDateString = dateString
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_create_events)


        supportActionBar!!.hide() // hide the title bar
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        //datepicker
        val dateBtn: Button = this.findViewById(R.id.pick_date)
        dateBtn.setOnClickListener{
            val datePicker: DialogFragment = DatePickerFragment()
            datePicker.show(supportFragmentManager, "date picker")

        }


        val createEvent: Button = findViewById(R.id.ce_create_event)
        createEvent.setOnClickListener{

            if(dateSet){
                //store Data
                val eventTitle: EditText = findViewById(R.id.event_title)
                val eventLocation: EditText = findViewById(R.id.event_location)
                val eventEntries: EditText = findViewById(R.id.event_entries)
                val eventCollege: EditText = findViewById(R.id.event_college)

                when {
                    eventTitle.text.toString().equals("") -> Toast.makeText(this, "Select a title", Toast.LENGTH_SHORT).show()
                    eventLocation.text.toString().equals("") -> Toast.makeText(this, "Select a Location", Toast.LENGTH_SHORT).show()
                    eventCollege.text.toString().equals("") -> Toast.makeText(this, "Select a College", Toast.LENGTH_SHORT).show()
                    eventEntries.text.toString().equals("") -> Toast.makeText(this, "Enter entries allowed", Toast.LENGTH_SHORT).show()
                    else -> {
                        val database = FirebaseDatabase.getInstance()
                        val myRef = database.reference


                        val id = myRef.child("events").push().key
                        keyId = id!!


                        //end store data


                        val intent= Intent(this, CreateEvent2::class.java)
                        intent.putExtra("keyId", keyId)
                        intent.putExtra("eventTitle", eventTitle.text.toString())
                        intent.putExtra("eventCollege", eventCollege.text.toString())
                        intent.putExtra("eventLocation", eventLocation.text.toString())
                        intent.putExtra("eventDate", mEventDate.time)
                        intent.putExtra("eventDateString", mEventDateString)
                        intent.putExtra("eventEntries", Integer.valueOf(eventEntries.text.toString()))

                        startActivity(intent)

                    }
                }
                }

            else{
                Toast.makeText(this, "Pick date first", Toast.LENGTH_SHORT).show()
            }


        }



    }
}







