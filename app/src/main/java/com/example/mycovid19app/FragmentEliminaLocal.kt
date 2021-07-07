package com.example.mycovid19app

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController


class FragmentEliminaLocal : Fragment() {

    private lateinit var textViewCidadeEliminar: TextView
    private lateinit var textViewLocalAdmEliminar: TextView
    private lateinit var textViewSalaEliminar: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_elimina_local

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_elimina_local, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewCidadeEliminar = view.findViewById(R.id.textViewEliminaC)
        textViewLocalAdmEliminar = view.findViewById(R.id.textViewEliminaL)
        textViewSalaEliminar = view.findViewById(R.id.textViewEliminaS)

        val local = DadosApp.localSelecionado!!
        textViewCidadeEliminar.setText(local.cidade)
        textViewLocalAdmEliminar.setText(local.localadm)
        textViewSalaEliminar.setText(local.sala)
    }

    fun navegaLocal() {
        findNavController().navigate(R.id.action_fragmentEliminaLocal_to_fragmentInicioPage)
    }

    fun elimina() {
        val uriLocal = Uri.withAppendedPath(
            ContentProviderApp.ENDERECO_LOCAL,
            DadosApp.localSelecionado!!.id.toString()
        )

        val registos = activity?.contentResolver?.delete(
            uriLocal,
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                getString(R.string.loc_elm_erro),
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            getString(R.string.loc_elm_succ),
            Toast.LENGTH_LONG
        ).show()

        navegaLocal()
    }


    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_confirma_eliminar_local -> elimina()
            R.id.action_cancelar_eliminar_local -> navegaLocal()
            else -> return false
        }

        return true
    }
}