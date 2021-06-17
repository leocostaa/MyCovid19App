package com.example.mycovid19app

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AdapterPacientes(var cursor: Cursor? = null) : RecyclerView.Adapter<AdapterPacientes.ViewHolderPacientes>(){
    class ViewHolderPacientes(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPacientes {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolderPacientes, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}