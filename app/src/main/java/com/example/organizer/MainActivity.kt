package com.example.organizer

import android.Manifest
import android.app.ActionBar
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import java.io.File

class MainActivity : AppCompatActivity() {

    val requestCode = 100
    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun list(view: View) {
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

    //   **
//    private val READ_REQUEST_CODE: Int = 42
//    fun performFileSearch() {
//        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
//            addCategory(Intent.CATEGORY_OPENABLE)
//            type = "image/*"
//        }
//        startActivityForResult(intent, READ_REQUEST_CODE)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
//        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//            resultData?.data?.also { uri ->
//                Log.i(TAG, "Uri: $uri")
//                showImage(uri)
//            }
//        }
//    }

    private fun listExternalStorage() {

        val state = Environment.getExternalStorageState()

        if (Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state) {

            this.disposable =
                Observable.fromPublisher(FileLister(Environment.getExternalStorageDirectory()))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        txtFiles.append(it + "\n")
                    }, {
                        Log.e("MainActivity", "Erro ao listar os arquivos do SD card", it)
                    }, {
                        Toast.makeText(
                            this,
                            "Sucesso, listagem de arquivos completa",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        this.disposable?.dispose()
                        this.disposable = null
                    })
        }
    }

    private class FileLister(val directory: File) : Publisher<String> {

        private lateinit var subscriber: Subscriber<in String>

        override fun subscribe(s: Subscriber<in String>?) {
            if (s == null) {
                return
            }
            this.subscriber = s
            this.listFiles(this.directory)
            this.subscriber.onComplete()
        }

        private fun listFiles(directory: File) {
            val files = directory.listFiles()
            if (files != null) {
                for (file in files) {
                    if (file != null) {
                        if (file.isDirectory) {
                            listFiles(file)
                        } else {
                            subscriber.onNext(file.absolutePath)
                        }
                    }
                }
            }
        }
    }


}







