package com.example.organizer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_view.view.*
import java.io.File

typealias OnItemClickListener = (File) -> Unit

class FileAdapter : RecyclerView.Adapter<FileAdapter.ItemViewHolder>() {

    private var itens = ArrayList<File>()
    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_view,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = itens.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        with(holder.itemView) {
            val file = itens[position]
            ivFile.setImageResource(
                if (file.isDirectory) {
                    R.drawable.ic_folder_24px
                } else {
                    R.drawable.ic_picture_as_pdf_24px
                }
            )
            tvFileName.text = file.name

            setOnClickListener {
                onItemClickListener?.invoke(file)
            }
        }
    }

    fun getItens(itens: ArrayList<File>) {
        this.itens.clear()
        this.itens.addAll(itens)
        notifyDataSetChanged()
    }

    fun setOnItemClick(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}