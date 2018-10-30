package com.example.aarohi.eventpanaroma

import android.Manifest
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityCompat.requestPermissions
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.webkit.MimeTypeMap
import android.widget.*
import com.example.atmiya.eventpanorama.MainActivity
import com.example.atmiya.eventpanorama.R
import com.example.atmiya.eventpanorama.models.EventModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.util.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class CreateEvent2 : AppCompatActivity() {

    private val PICKIMAGE = 1
    private val PICKIMAGE2 = 1
    private var storage: FirebaseStorage? = null
    private var storageRef: StorageReference? = null
    private var imageUploadStart: Boolean = false
    private var imageUploadStart2: Boolean = false
    private var imageUploaded: Boolean = false
    private var imageUploaded2: Boolean = false
    private var imagePrimaryLink: String = ""
    private var imageSecondaryLink: String = ""



    private var eventTitle: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event2)

        supportActionBar!!.hide() // hide the title bar
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)


        eventTitle = intent.getStringExtra("eventTitle")
        storage = FirebaseStorage.getInstance()
        storageRef = storage!!.reference





        val pickImage: Button = this.findViewById<Button>(R.id.image_button)
        pickImage.setOnClickListener{
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            imageUploadStart = true
            startActivityForResult(Intent.createChooser(intent, "Select Cover Picture"), PICKIMAGE)

        }

        val pickImage2: Button = this.findViewById<Button>(R.id.image_button2)
        pickImage2.setOnClickListener{
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            imageUploadStart2 = true
            startActivityForResult(Intent.createChooser(intent, "Select Secondary Picture"), PICKIMAGE2)

        }


        isReadStoragePermissionGranted()


        val publishEvent: Button = this.findViewById<Button>(R.id.publish_event)
        publishEvent.setOnClickListener{
            if(imageUploaded && imageUploaded2)
            {
                val eventDescription: EditText = this.findViewById(R.id.event_description)
                val eventBriefDescription: EditText = findViewById(R.id.event_brief_description)

                when{
                    eventDescription.text.toString().equals("") -> Toast.makeText(this, "Enter One line description", Toast.LENGTH_SHORT).show()
                    eventDescription.text.toString().equals("") -> Toast.makeText(this, "Enter description", Toast.LENGTH_SHORT).show()
                    else -> {
                        val database = FirebaseDatabase.getInstance()
                        val myRef = database.reference

                        //recreating Date
                        val mdate: Date = Date()
                        mdate.time = intent.getLongExtra("eventDate", -1)

                        //recreating entries
                        val mentries: Int = intent.getIntExtra("eventEntries", -1)

                        val mEvent: EventModel = EventModel(intent.getStringExtra("eventTitle"),
                                intent.getStringExtra("eventCollege"),
                                intent.getStringExtra("eventLocation"),
                                mdate,
                                intent.getStringExtra("eventDateString"),
                                mentries,
                                eventDescription.text.toString(),
                                eventBriefDescription.text.toString(),
                                imagePrimaryLink,
                                imageSecondaryLink,
                                FirebaseAuth.getInstance().currentUser!!.uid)


                        myRef.child("events").child(intent.getStringExtra("keyId")).setValue(mEvent)

                        //myRef.child("events").child(intent.getStringExtra("keyId")).child("description").setValue(event2)

                        Toast.makeText(this, mEvent.eventName + " created!!", Toast.LENGTH_SHORT).show()


                        val intent= Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                }

            }
            else
            {
                Toast.makeText(this, "Let images upload...", Toast.LENGTH_SHORT).show()
            }



        }
    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(data != null)
        {
            val imageUri:Uri = data!!.data
            imageUploader(imageUri, requestCode)

        }
        else{
            Toast.makeText(this, "Image not selected", Toast.LENGTH_SHORT).show()//

        }


    }

    private fun imageUploader(imageUri: Uri, requestCode: Int): Boolean{
        if(requestCode == PICKIMAGE2 && imageUploadStart2)
        {
            //val imageStatus2: TextView = findViewById(R.id.image_status2)
            //intent contains the Uri of file which is used for uploading


            val riversRef = storageRef!!.child("images/").child(eventTitle +"/"  + eventTitle+ "_Secondary"+"."+getFileExtension(imageUri))

            val uploadTask = riversRef.putFile(imageUri)


            uploadTask.addOnFailureListener { exception ->
                // Handle unsuccessful uploads

            }.addOnSuccessListener {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...

                Toast.makeText(this, "Image uploaded", Toast.LENGTH_SHORT).show()

                riversRef.downloadUrl.addOnSuccessListener {
                    imageSecondaryLink = it.toString()
                }.addOnFailureListener{
                    imageSecondaryLink = "Exception"
                }
                imageUploaded2 = true
                //imageStatus2.text = msg
            }.addOnProgressListener {
                val progress: Double = (100.0 * it.bytesTransferred / it.totalByteCount)
                //val msg = "Progress: $progress%"
                //imageStatus2.text = msg
                findViewById<ProgressBar>(R.id.images_progress).progress = progress.toInt()
            }
        }
        else if (requestCode == PICKIMAGE && imageUploadStart) {

            //val imageStatus: TextView = findViewById(R.id.image_status)
            //intent contains the Uri of file which is used for uploading


            val riversRef = storageRef!!.child("images/").child(eventTitle +"/" + eventTitle + "_Primary"+"."+getFileExtension(imageUri))
            //imagePrimaryLink = riversRef.downloadUrl.toString()

            val uploadTask = riversRef.putFile(imageUri)

            //val uploadStarted = "Uploading started"
            //imageStatus.text = uploadStarted

            uploadTask.addOnFailureListener { exception ->
                // Handle unsuccessful uploads

                //imageStatus.text = exception.toString()
            }.addOnSuccessListener {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                //val msg = "Done uploading"
                /*****/
                Toast.makeText(this, "Image uploaded", Toast.LENGTH_SHORT).show()

                riversRef.downloadUrl.addOnSuccessListener {
                    imagePrimaryLink = it.toString()
                }.addOnFailureListener{
                    imagePrimaryLink = "Exception"
                }


                imageUploaded = true
                //imageStatus.text = msg

            }.addOnProgressListener {
                val progress: Double = (100.0 * it.bytesTransferred / it.totalByteCount)
                //val msg = "Progress: $progress%"
                //imageStatus.text = msg
                findViewById<ProgressBar>(R.id.imagep_progress).progress = progress.toInt()
            }

        }

        return true
    }
    private fun getFileExtension( uri: Uri): String {
        var cr = contentResolver
        var mime = MimeTypeMap.getSingleton()

        return mime.getExtensionFromMimeType(cr.getType(uri))
    }

    fun isReadStoragePermissionGranted() =// Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                                Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                } else {
                    // No explanation needed; request the permission
                    requestPermissions(this,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            3)

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                // Permission has already been granted
            }
}
