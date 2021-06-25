package com.example.mycovid19app

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AdapterVacinas(var cursor: Cursor? = null): RecyclerView.Adapter<AdapterVacinas.ViewHolderVacina>() {

    class ViewHolderVacina(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderVacina {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolderVacina, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


}