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

    class ViewHolderVacinacao(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val textViewDataVac = itemView.findViewById<TextView>(R.id.textViewDataVac)
        private val textViewNomePaciente = itemView.findViewById<TextView>(R.id.textViewNomePaciente)
        private val textViewLocal = itemView.findViewById<TextView>(R.id.textViewLocalAdm)
        private val textViewOrigem = itemView.findViewById<TextView>(R.id.textViewOrigem)

        private lateinit var vacinacao: Vacinacao
        init {
            itemView.setOnClickListener(this)
        }

        fun atualizaVacinacao(vacinacao: Vacinacao) {

            this.vacinacao = vacinacao
            textViewDataVac.text = vacinacao.data_vac.toString()
            textViewNomePaciente.text = vacinacao.nomePaciente
            textViewLocal.text = vacinacao.localadm
            textViewOrigem.text = vacinacao.origem
        }
        override fun onClick(v: View?) {
            selecionado?.desSeleciona()
            seleciona()
        }

        private fun seleciona() {
            selecionado = this
            itemView.setBackgroundResource(R.color.cor_selecao)
            DadosApp.vacinacaoSelecionado = vacinacao
            DadosApp.activity.atualizaMenuVacinacao(true)

        }

        private fun desSeleciona() {
            selecionado = null
            itemView.setBackgroundResource(android.R.color.white)
        }

        companion object {
            var selecionado : ViewHolderVacinacao? = null
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