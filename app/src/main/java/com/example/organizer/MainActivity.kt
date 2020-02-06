package com.example.organizer

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_toolbar.*


class MainActivity : AppCompatActivity() {

    private val requestCode = 100
    private var disposable: Disposable? = null

    lateinit var fileAdapter: FileAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list()
        allComponents()
        onBackPressed()
    }

    private fun allComponents() {
        setSupportActionBar(toolbar_main as Toolbar?)
        supportActionBar?.title = "SD CARD"
        (toolbar_main as Toolbar?)?.setNavigationOnClickListener {
            fileAdapter.goBack()
        }

        floatingActionButton.setOnClickListener {
            val internalReference = "/sdcard/DCIM/Pictures/logo.png"
            val uri = Uri.parse(internalReference)
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.type = "image/png"
            intent.flags = Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
            intent.putExtra(Intent.EXTRA_STREAM, uri)

            if (intent.resolveActivity(packageManager) != null) {
                val intentChooser = Intent.createChooser(intent, "Compartilhar imagem com:")
                startActivity(intentChooser)
            }
        }
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
            recyclerViewFile.layoutManager = GridLayoutManager(this, 3)
            fileAdapter.setItens(ArrayList(Environment.getExternalStorageDirectory().listFiles().toList()))
            fileAdapter.setOnItemClick {
                fileAdapter.setItens(ArrayList(it.listFiles().toList()))
            }
//            FileLister.listFiles(Environment.getExternalStorageDirectory())
//            FileLister.listFiles(File("/storage/emulated/0/DCIM"))

        }
    }

    override fun onBackPressed() {
        fileAdapter.goBack()
    }


}








