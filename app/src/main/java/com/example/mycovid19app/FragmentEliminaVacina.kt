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


class FragmentEliminaVacina : Fragment() {

    private lateinit var textViewEliminaO: TextView
    private lateinit var textViewEliminaQ: TextView
    private lateinit var textViewEliminaV: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_elimina_vacina

        return inflater.inflate(R.layout.fragment_elimina_vacina, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewEliminaO = view.findViewById(R.id.textViewEliminaO)
        textViewEliminaQ = view.findViewById(R.id.textViewEliminaQ)
        textViewEliminaV = view.findViewById(R.id.textViewEliminaV)

        val vacina = DadosApp.vacinaSelecionado!!
        textViewEliminaO.setText(vacina.origem)
        textViewEliminaQ.setText(vacina.quantidade.toString())
        textViewEliminaV.setText(vacina.validade.toString())
    }
    fun navegaInicio() {
        findNavController().navigate(R.id.action_fragmentEliminaVacina_to_fragmentInicioPage)
    }
    fun elimina() {
        val uriVacina = Uri.withAppendedPath(
            ContentProviderApp.ENDERECO_VACINA,
            DadosApp.vacinaSelecionado!!.id.toString()
        )

        val registos = activity?.contentResolver?.delete(
            uriVacina,
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                "Erro Eliminar Vacina",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            "Vacina Eliminada com sucesso",
            Toast.LENGTH_LONG
        ).show()
        navegaInicio()
    }
    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_confirma_eliminar_vacina -> elimina()
            R.id.action_cancelar_eliminar_vacina -> navegaInicio()
            else -> return false
        }

        return true
    }


}