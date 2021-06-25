package com.example.mycovid19app

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterVacinas(val fragment: FragmentVacina): RecyclerView.Adapter<AdapterVacinas.ViewHolderVacina>() {

    public var cursor: Cursor? = null
        get() = field
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolderVacina(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewOrigem = itemView.findViewById<TextView>(R.id.textViewOrigemTabela)
        private val textViewQuantidade = itemView.findViewById<TextView>(R.id.textViewQuantidade)
        private val textViewValidade = itemView.findViewById<TextView>(R.id.textViewValidade)


        fun atualizaVacina(vacina: Vacina) {
            textViewOrigem.text = vacina.origem
            textViewQuantidade.text = vacina.quantidade.toString()
            textViewValidade.text = vacina.validade.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderVacina {
        val itemVacina = fragment.layoutInflater.inflate(R.layout.item_vacina, parent, false)

        return ViewHolderVacina(itemVacina)
    }

    override fun onBindViewHolder(holder: ViewHolderVacina, position: Int) {
        cursor!!.moveToPosition(position)
        holder.atualizaVacina(Vacina.fromCursor(cursor!!))

    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }


}