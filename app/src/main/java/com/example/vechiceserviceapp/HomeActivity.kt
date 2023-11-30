package com.example.vechiceserviceapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView.ScaleType
import android.widget.RelativeLayout


import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast

import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel


import com.example.vechiceserviceapp.Bikes.BikeDetailedActivity
import com.example.vechiceserviceapp.Cars.CarDetailedActivity
import com.example.vechiceserviceapp.Chat.MainActivityJ


import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


const val CHANNEL_ID = "channelId"

class HomeActivity : AppCompatActivity() {

    private lateinit var imageSlider: ImageSlider
    private lateinit var VecNo: EditText
    private lateinit var Vectype: EditText
    private lateinit var VecModel: EditText
    private lateinit var VecBrand: EditText
    private lateinit var VecChasNo: EditText
    private lateinit var ConNo: EditText

    private lateinit var btnSavedData: Button

    private lateinit var dbRef: DatabaseReference


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.title = "Welcome ! Book your Services"

        val address = intent.getStringExtra("address")
        val addressTextView = findViewById<TextView>(R.id.addressTextView)
        addressTextView.text = address

        runOnUiThread {
            addressTextView.text = address
        }

        val additionalTextView = findViewById<TextView>(R.id.additionalTextView)
        additionalTextView.text = "Are you are looking for ?"

        setupAvatars()

        createNotificationChannel()


        VecNo = findViewById(R.id.vno)
        Vectype = findViewById(R.id.vtno)
        VecModel = findViewById(R.id.vmno)
        VecBrand = findViewById(R.id.vbnno)
        VecChasNo = findViewById(R.id.vcno)
        ConNo = findViewById(R.id.vconno)
        btnSavedData = findViewById(R.id.book)


        dbRef = FirebaseDatabase.getInstance().getReference("Vechicles")

        btnSavedData.setOnClickListener {
            saveVechicleData()

        }

        imageSlider = findViewById(R.id.image_slider)

        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.image1))
        imageList.add(SlideModel(R.drawable.image2))
        imageList.add(SlideModel(R.drawable.image3))
        imageList.add(SlideModel(R.drawable.image4))

        imageSlider.setImageList(imageList, ScaleTypes.CENTER_INSIDE)

        val chatLayout = findViewById<RelativeLayout>(R.id.chat_layout)

        chatLayout.setOnClickListener {

            val intent = Intent(this, MainActivityJ::class.java)

            startActivity(intent)
        }


        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
        builder.setSmallIcon(R.drawable.baseline_directions_car_filled_24)
            .setContentTitle("Vechicle Service App")
            .setContentText("Welcome to the Vechicle Service App")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                return
            }
            notify(1, builder.build())
        }


    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "FIRST CHANNEL",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "Test description for my channel"

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun saveVechicleData() {

        val VechRegNo = VecNo.text.toString()
        val Vechtype = Vectype.text.toString()
        val VechModel = VecModel.text.toString()
        val VechBrand = VecBrand.text.toString()
        val VechChasNo = VecChasNo.text.toString()
        val ContNo = ConNo.text.toString()

        if (VechRegNo.isEmpty()) {
            VecNo.error = "Please Enter Vechicle Registration No"
        }
        if (VechModel.isEmpty()) {
            VecModel.error = "Please Enter Vechicle Model / type"
        }
        if (VechBrand.isEmpty()) {
            VecBrand.error = "Please Enter Vechicle Brand"
        }
        if (VechChasNo.isEmpty()) {
            VecChasNo.error = "Please Enter Date of Vechicle Service"
        }
        if (ContNo.isEmpty()) {
            ConNo.error = "Please Enter Contact No"
        }
        if (Vechtype.isEmpty()) {
            Vectype.error = "Four wheeler/two wheeler"
        }

        if (VechRegNo.isEmpty() || VechModel.isEmpty() || VechBrand.isEmpty() || VechChasNo.isEmpty() || ContNo.isEmpty() || Vechtype.isEmpty()) {
            // Show a general error message for any empty field
           showToast("Service booked unsuccessfully. Please fill in all required fields")

               return
        }

        val VechId = dbRef.push().key!!

        val vechicle =
            VechicleModel(VechChasNo, VechBrand, VechRegNo, VechModel, VechId, ContNo, Vechtype)

        dbRef.child(VechId).setValue(vechicle)
            .addOnCompleteListener {


                VecNo.text.clear()
                VecModel.text.clear()
                VecBrand.text.clear()
                VecChasNo.text.clear()
                ConNo.text.clear()
                Vectype.text.clear()

                showNotification()

               showToast( "Service Booked Successfully")


            }


            .addOnFailureListener {
                error(Toast.makeText(this, "error", Toast.LENGTH_LONG).show())
            }


    }

    private fun showNotification() {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
        builder.setSmallIcon(R.drawable.baseline_directions_car_filled_24)
            .setContentTitle("Vechicle Service App")
            .setContentText("Service Booked Successfully")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                return
            }
            notify(2, builder.build())
        }
    }


    private fun setupAvatars() {
        val avatar1 = findViewById<CardView>(R.id.avatar1)
        val avatar2 = findViewById<CardView>(R.id.avatar2)

        avatar1.setOnClickListener {
            navigateToCarActivity()
        }

        avatar2.setOnClickListener {
            navigateToBikeActivity()
        }
    }

    private fun navigateToCarActivity() {
        Log.d("Navigation", "Navigating to CarDetailedActivity")
        val intent = Intent(this, CarDetailedActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToBikeActivity() {
        Log.d("Navigation", "Navigating to BikeDetailedActivity")
        val intent = Intent(this, BikeDetailedActivity::class.java)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        val inflater = layoutInflater
        val layout = inflater.inflate(R.layout.custom_toast_layout, findViewById(R.id.toast_layout_root))

        val text = layout.findViewById<TextView>(R.id.toast_text)
        text.text = message

        val toast = Toast(applicationContext)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.duration = Toast.LENGTH_LONG
        toast.view = layout
        toast.show()
    }




}