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

    class ViewHolderPacientes(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewNome = itemView.findViewById<TextView>(R.id.textViewNome)
        private val textViewDataNascimento = itemView.findViewById<TextView>(R.id.textViewDataNascimento)
        private val textViewSexo = itemView.findViewById<TextView>(R.id.textViewSexo)

        fun atualizaPaciente(paciente: Paciente){
            textViewNome.text = paciente.nome
            textViewDataNascimento.text = paciente.DataNascimento.toString()
            textViewSexo.text = paciente.sexo
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
}