package com.example.mycovid19app

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterPacientes(val fragment:FragmentPacientes) : RecyclerView.Adapter<AdapterPacientes.ViewHolderPacientes>(){
    public var cursor: Cursor? = null
        get() = field
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolderPacientes(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val textViewNome = itemView.findViewById<TextView>(R.id.textViewNome)
        private val textViewDataNascimento = itemView.findViewById<TextView>(R.id.textViewDataNascimento)
        private val textViewSexo = itemView.findViewById<TextView>(R.id.textViewSexo)

        private lateinit var paciente : Paciente

        init {
            itemView.setOnClickListener(this)
        }

        fun atualizaPaciente(paciente: Paciente){
            this.paciente = paciente

            textViewNome.text = paciente.nome
            textViewDataNascimento.text = paciente.DataNascimento.toString()
            textViewSexo.text = paciente.sexo
        }

        override fun onClick(v: View?) {
            selecionado?.desSeleciona()
            seleciona()
        }
        private fun seleciona() {
            selecionado = this
            itemView.setBackgroundResource(R.color.cor_selecao)
            DadosApp.PacienteSelecionado = paciente
            DadosApp.activity.atualizaMenuPacientes(true)

        }

        private fun desSeleciona() {
            selecionado = null
            itemView.setBackgroundResource(android.R.color.white)
        }

        companion object {
            var selecionado : ViewHolderPacientes? = null
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPacientes {
        val itemPaciente = fragment.layoutInflater.inflate(R.layout.item_paciente, parent, false)

        return ViewHolderPacientes(itemPaciente)
    }

    override fun onBindViewHolder(holder: ViewHolderPacientes, position: Int) {
        cursor!!.moveToPosition(position)
        holder.atualizaPaciente(Paciente.fromCursor(cursor!!))
    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0

    }
    companion object{
        var selecionado : ViewHolderPacientes? = null
    }
}