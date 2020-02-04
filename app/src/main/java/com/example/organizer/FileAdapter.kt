package com.example.organizer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_view.view.*
import java.io.File

typealias OnItemClickListener = (File) -> Unit

class FileAdapter : RecyclerView.Adapter<FileAdapter.ItemViewHolder>() {

    private var itens = ArrayList<File>()
    private var onItemClickListener: OnItemClickListener? = null
    private var selectedFile: File? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_view,
                parent,
                false
            )
        )

    override fun getItemCount() = itens.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        with(holder.itemView) {
            val file = itens[position]
            ivFile.setImageResource(
                when  {
                    (file.path.contains("/storage/emulated/0/Android")) -> R.drawable.ic_android_black
                    (file.path.contains("/storage/emulated/0/Music")) -> R.drawable.ic_library_music_black_24dp
                    (file.path.contains("/storage/emulated/0/Documents")) -> R.drawable.ic_folder_24px
                    (file.path.contains("/storage/emulated/0/Movies")) -> R.drawable.ic_video_library_black_24dp
                    (file.path.contains("/storage/emulated/0/DCIM")) -> R.drawable.ic_menu_gallery
                    (file.path.contains("/storage/emulated/0/Alarms")) -> R.drawable.ic_access_alarm_black_24dp
                    (file.path.contains("/storage/emulated/0/Download")) -> R.drawable.ic_file_download_black_24dp
                    (file.path.contains("/storage/emulated/0/Ringtones")) -> R.drawable.ic_music_note_24px
                    (file.path.contains("/storage/emulated/0/Pictures")) -> R.drawable.ic_picture_as_pdf_24px
                    else -> {
                        R.drawable.ic_error_black_24dp
                    }
                }
            )

            tvFileName.text = file.name

            setOnClickListener {
                selectedFile = file
                onItemClickListener?.invoke(file)
            }
        }

    }

    fun setItens(itens: ArrayList<File>) {
        this.itens.clear()
        this.itens.addAll(itens)
        notifyDataSetChanged()
    }

    fun goBack() {
        selectedFile = selectedFile?.let {
            setItens(ArrayList(it.parentFile.listFiles()?.toList() ?: mutableListOf()))
            it.parentFile
        }
    }

    fun setOnItemClick(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}