package com.example.mycovid19app

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterVacinacao(val fragment: FragmentVacinacao) : RecyclerView.Adapter<AdapterVacinacao.ViewHolderVacinacao>(){

    public var cursor: Cursor? = null
        get() = field
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolderVacinacao(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewDataVac = itemView.findViewById<TextView>(R.id.textViewDataVac)

        fun atualizaVacinacao(vacinacao: Vacinacao) {
            textViewDataVac.text = vacinacao.data_vac.toString()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderVacinacao {
        val itemVacinacao = fragment.layoutInflater.inflate(R.layout.item_vacinacao, parent, false)

        return ViewHolderVacinacao(itemVacinacao)
    }

    override fun onBindViewHolder(holder: ViewHolderVacinacao, position: Int) {
        cursor!!.moveToPosition(position)
        holder.atualizaVacinacao(Vacinacao.fromCursor(cursor!!))
    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }
}