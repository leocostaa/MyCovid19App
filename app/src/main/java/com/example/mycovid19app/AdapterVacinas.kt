package com.example.mycovid19app

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterVacinas(val fragment: FragmentVacina): RecyclerView.Adapter<AdapterVacinas.ViewHolderVacina>()  {

    public var cursor: Cursor? = null
        get() = field
        set(value) {
            field = value
            notifyDataSetChanged()
        }



    class ViewHolderVacina(itemView: View) : RecyclerView.ViewHolder(itemView) , View.OnClickListener{
        private val textViewOrigem = itemView.findViewById<TextView>(R.id.textViewOrigemTabela)
        private val textViewQuantidade = itemView.findViewById<TextView>(R.id.textViewQuantidade)
        private val textViewValidade = itemView.findViewById<TextView>(R.id.textViewValidade)

        private lateinit var vacina: Vacina
        init {
            itemView.setOnClickListener(this)
        }

        fun atualizaVacina(vacina: Vacina) {
            this.vacina = vacina
            textViewOrigem.text = vacina.origem
            textViewQuantidade.text = vacina.quantidade.toString()
            textViewValidade.text = vacina.validade.toString()
        }


        override fun onClick(v: View?) {
            selecionado?.desSeleciona()
            seleciona()
        }

        private fun seleciona() {
            selecionado = this
            itemView.setBackgroundResource(R.color.cor_selecao)
            DadosApp.vacinaSelecionado = vacina
            DadosApp.activity.atualizaMenuVacinas(true)

        }

        private fun desSeleciona() {
            selecionado = null
            itemView.setBackgroundResource(android.R.color.white)
        }

        companion object {
            var selecionado : ViewHolderVacina? = null
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