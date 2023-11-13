package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random



class MainActivity : AppCompatActivity() {
    private lateinit var Progress: ProgressBar
    private lateinit var Textprog: TextView
    private lateinit var audio: TextView
    private var progr = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        updateProgressBar()


        val button: Button = findViewById(R.id.button)
        button.setOnClickListener { v ->
            progr = Random.nextInt(101)
            updateProgressBar()
        }
        val up: Button = findViewById(R.id.button4)
        up.setOnClickListener {
            // Simulate picking an audio file
            val audioPickIntent = Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(audioPickIntent, AUDIO_PICK_REQUEST)
        }
    }
    private fun updateProgressBar() {

         Progress = findViewById(R.id.progressBar2)
         Progress.progress = progr
         Textprog = findViewById(R.id.textView3)
         Textprog.text = "$progr%"
    }

    // Handle the result of picking an audio file
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AUDIO_PICK_REQUEST && resultCode == RESULT_OK) {
            data?.data?.let { audioUri ->
                audio = findViewById(R.id.editTextText)
                val audioName = getAudioFileName(audioUri)
                audio.text = "     $audioName"
            }
        }
    }

    // Get the file name from the content URI
    private fun getAudioFileName(uri: android.net.Uri): String {
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)
                return it.getString(nameIndex)
            }
        }
        return "Unknown"
    }
    companion object {
        private const val AUDIO_PICK_REQUEST = 123
    }
}