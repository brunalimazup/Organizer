package com.example.organizer

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    private val requestCode = 100
    private var disposable: Disposable? = null

    lateinit var fileAdapter: FileAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list()

    }

    private fun list() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), requestCode)
        } else {
            listExternalStorage()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == this.requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                listExternalStorage()
            } else {
                Toast.makeText(
                    this,
                    "É necessário aceitar a permissão para ter acesso.",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        this.disposable?.dispose()
    }

    private fun listExternalStorage() {
        val state = Environment.getExternalStorageState()

        if (Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state) {

            fileAdapter = FileAdapter()
            recyclerViewFile.adapter = fileAdapter
            recyclerViewFile.layoutManager = LinearLayoutManager(this)
            fileAdapter.setItens(ArrayList(Environment.getExternalStorageDirectory().listFiles().toList()))
            fileAdapter.setOnItemClick {
                fileAdapter.setItens(ArrayList(it.listFiles().toList()))
            }
//            FileLister.listFiles(Environment.getExternalStorageDirectory())
//            FileLister.listFiles(File("/storage/emulated/0/DCIM"))

        }
    }

    object FileLister {
        fun listFiles(directory: File) {
            val files = directory.listFiles()
            if (files != null) {
                for (file in files) {
                    if (file != null) {
                        if (file.isDirectory) {
                            println("DIR: > " + file.absolutePath)
                            listFiles(file)
                        } else {
                            println("          FILE: > " + file.absolutePath)
                        }
                    }
                }
            }
        }
    }
}








