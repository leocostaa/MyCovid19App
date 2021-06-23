package com.example.mycovid19app

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AdapterVacinacao(var cursor: Cursor? = null) : RecyclerView.Adapter<AdapterVacinacao.ViewHolderVacinacao>(){

    class ViewHolderVacinacao(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderVacinacao {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolderVacinacao, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}