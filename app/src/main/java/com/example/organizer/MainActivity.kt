package com.example.organizer

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_activity_toolbar.*
import permissions.dispatcher.*
import java.io.*
import java.lang.System.load


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val state = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED == state) {

        } else if (Environment.MEDIA_MOUNTED_READ_ONLY == state) {

        } else {

        }

        val directoryOne =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
        val directoryTwo =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val directoryThree =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)

        fun getExternalDir(privateDir: Boolean) =
            if (privateDir) getExternalFilesDir(null)
            else Environment.getExternalStorageDirectory()

        fun saveToExternal(privateDir : Boolean) {
            val hasPermission = checkStoragePermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                RC_STORAGE_PERMISSION)
            if(!hasPermission) {
                return
            }else {
                Log.e("NGVL", "Não é possivel escrever no SD card")
            }
        }
        fun loadFromExternal(privateDir : Boolean) {
            val hasPermission =  checkStoragePermission(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                RC_STORAGE_PERMISSION)
            if(!hasPermission) {
                return
            } else {
                Log.e("NGVL", "SD Card indisponivel")
            }
        }
    }

    fun checkStoragePermission(permission : String, requestedCode: Int): Boolean{
        if(ActivityCompat.checkSelfPermission(this,permission)
        != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)){
                Toast.makeText(this, R.string.message_permission_requested,
                    Toast.LENGTH_SHORT).show()
            }
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestedCode)
            return false
        }
            return true
    }

    override fun onRequestPermissionsResult(requestedCode: Int, permission: Array<String>, grantResult: IntArray){
//        super.onRequestPermissionsResult(requestCode, permissions, grantResult)
        when(requestedCode){
            RC_STORAGE_PERMISSION -> {
                if(grantResult[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, R.string.permission_granted,
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, R.string.permission_denied,
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    companion object{
        val RC_STORAGE_PERMISSION = 0
    }
}


