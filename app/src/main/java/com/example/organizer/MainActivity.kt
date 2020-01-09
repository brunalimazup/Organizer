package com.example.organizer

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_activity_toolbar.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        var btnImage = findViewById(R.id.image) as ImageButton

        btnImage.setOnClickListener {
            Toast.makeText(this, "YOU CLICKED ME.", Toast.LENGTH_SHORT).show()

            var btnMusic = findViewById(R.id.music) as ImageButton

            btnMusic.setOnClickListener {
                Toast.makeText(this, "YOU CLICKED ME.", Toast.LENGTH_SHORT).show()

                var btnPdf = findViewById(R.id.pdf) as ImageButton

                btnPdf.setOnClickListener {
                    Toast.makeText(this, "YOU CLICKED ME.", Toast.LENGTH_SHORT).show()

                    var btnVideo = findViewById(R.id.video) as ImageButton

                    btnVideo.setOnClickListener {
                        Toast.makeText(this, "YOU CLICKED ME.", Toast.LENGTH_SHORT).show()

                        var btnFiles = findViewById(R.id.files) as ImageButton

                        btnFiles.setOnClickListener {
                            Toast.makeText(this, "YOU CLICKED ME.", Toast.LENGTH_SHORT).show()

                            var btnAndroid = findViewById(R.id.android) as ImageButton

                            btnAndroid.setOnClickListener {
                                Toast.makeText(this, "YOU CLICKED ME.", Toast.LENGTH_SHORT).show()
                            }


                        }
                    }

                }
            }
        }
    }
}


