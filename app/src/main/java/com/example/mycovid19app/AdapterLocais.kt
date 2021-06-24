package com.example.mycovid19app

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AdapterLocais (var cursor: Cursor? = null): RecyclerView.Adapter<AdapterLocais.ViewHolderLivro>() {
    class ViewHolderLivro(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderLivro {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolderLivro, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}