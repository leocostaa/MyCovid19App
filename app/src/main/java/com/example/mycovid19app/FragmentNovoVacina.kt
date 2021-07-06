package com.example.mycovid19app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.mycovid19app.databinding.FragmentNovoVacinaBinding

class FragmentNovoVacina : Fragment() {

    private var _binding: FragmentNovoVacinaBinding? = null

    private val binding get() = _binding!!

    private lateinit var editTextOrigem : EditText
    private lateinit var editTextValidade : EditText
    private lateinit var editTextQuantidade : EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_novo_vacina


        _binding = FragmentNovoVacinaBinding.inflate(inflater, container, false)
        return binding.root


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editTextOrigem = view.findViewById(R.id.editTextLabOrigem)
        editTextValidade = view.findViewById(R.id.editTextValidade)
        editTextQuantidade = view.findViewById(R.id.editTextQuantidade)



    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun navegaInicioPage() {
        findNavController().navigate(R.id.action_fragmentNovoVacina_to_fragmentInicioPage)

    }

    fun guardar() {
        // todo: guardar livro
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_novo_vacina -> guardar()
            R.id.action_cancelar_novo_vacina -> navegaInicioPage()
            else -> return false
        }

        return true
    }
}