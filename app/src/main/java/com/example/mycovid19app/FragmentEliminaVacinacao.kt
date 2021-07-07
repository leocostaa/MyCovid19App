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


class FragmentEliminaVacinacao : Fragment() {

    private lateinit var textViewEliminaDV: TextView
    private lateinit var textViewEliminaLO: TextView
    private lateinit var textViewEliminaNP: TextView
    private lateinit var textViewEliminaLA: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_elimina_vacinacoes

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_elimina_vacinacao, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewEliminaDV = view.findViewById(R.id.textViewEliminaDVac)
        textViewEliminaLO = view.findViewById(R.id.textViewEliminaLO)
        textViewEliminaNP = view.findViewById(R.id.textViewEliminaNP)
        textViewEliminaLA = view.findViewById(R.id.textViewEliminaLA)

        val vacinacao = DadosApp.vacinacaoSelecionado !!
        textViewEliminaDV.setText(vacinacao.data_vac.toString())
        textViewEliminaLO.setText(vacinacao.origem)
        textViewEliminaNP.setText(vacinacao.nomePaciente)
        textViewEliminaLA.setText(vacinacao.localadm)
    }

    fun navegaInicioCancela() {
        findNavController().navigate(R.id.action_fragmentEliminaVacinacao_to_fragmentInicioPage)
        Toast.makeText(
            requireContext(),
            "Cancelado com sucesso",
            Toast.LENGTH_LONG
        ).show()
    }
    fun navegaInicio() {
        findNavController().navigate(R.id.action_fragmentEliminaVacinacao_to_fragmentInicioPage)

    }

    fun elimina() {
       
    }
    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_confirma_eliminar_vacinacoes -> elimina()
            R.id.action_cancelar_eliminar_vacinacoes -> navegaInicioCancela()
            else -> return false
        }

        return true
    }

}