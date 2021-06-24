package com.example.mycovid19app

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterLocais (val fragment: FragmentLocal,var cursor: Cursor? = null): RecyclerView.Adapter<AdapterLocais.ViewHolderLocal>() {
    


    class ViewHolderLocal(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewCidade = itemView.findViewById<TextView>(R.id.textViewCidade)
        private val textViewLocalAdm_T = itemView.findViewById<TextView>(R.id.textViewLocalAdmi)
        private val textViewSala = itemView.findViewById<TextView>(R.id.textViewSala)

        fun atualizaLocal(local: Local) {
            textViewCidade.text = local.cidade
            textViewLocalAdm_T.text = local.localadm
            textViewSala.text = local.sala
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderLocal {
        val itemLocal = fragment.layoutInflater.inflate(R.layout.item_local, parent, false)

        return ViewHolderLocal(itemLocal)
    }

    override fun onBindViewHolder(holder: ViewHolderLocal, position: Int) {
        cursor!!.moveToPosition(position)
        holder.atualizaLocal(Local.fromCursor(cursor!!))
    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }
}