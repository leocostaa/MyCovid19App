package com.example.mycovid19app

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterLocais (val fragment: FragmentLocal): RecyclerView.Adapter<AdapterLocais.ViewHolderLocal>() {
    public var cursor: Cursor? = null
        get() = field
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    class ViewHolderLocal(itemView: View) : RecyclerView.ViewHolder(itemView) , View.OnClickListener{
        private val textViewCidade = itemView.findViewById<TextView>(R.id.textViewCidade)
        private val textViewLocalAdm_T = itemView.findViewById<TextView>(R.id.textViewLocalAdmi)
        private val textViewSala = itemView.findViewById<TextView>(R.id.textViewSala)

        init {
            itemView.setOnClickListener(this)
        }

        fun atualizaLocal(local: Local) {
            textViewCidade.text = local.cidade
            textViewLocalAdm_T.text = local.localadm
            textViewSala.text = local.sala
        }
        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        override fun onClick(v: View?) {
            selecionado?.desSeleciona()
            seleciona()
        }

        private fun seleciona() {
            selecionado = this
            itemView.setBackgroundResource(R.color.cor_selecao)
        }

        private fun desSeleciona() {
            selecionado = null
            itemView.setBackgroundResource(android.R.color.white)
        }

        companion object {
            var selecionado : ViewHolderLocal? = null
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


