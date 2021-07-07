package com.example.mycovid19app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Spinner
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.mycovid19app.databinding.FragmentNovoVacinacaoBinding



class FragmentNovoVacinacao : Fragment() {

    private var _binding: FragmentNovoVacinacaoBinding? = null

    private lateinit var spinnerLOnovo: Spinner
    private lateinit var spinnerNPnovo: Spinner
    private lateinit var spinnerLAnovo: Spinner
    private lateinit var CalendarViewDataVac: CalendarView

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_novo_vacinacao

        _binding = FragmentNovoVacinacaoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        spinnerLOnovo = view.findViewById(R.id.spinnerLOnovo)
        spinnerNPnovo = view.findViewById(R.id.spinnerNPnovo)
        spinnerLAnovo = view.findViewById(R.id.spinnerLAnovo)
        CalendarViewDataVac = view.findViewById(R.id.calendarViewDataVac)

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun navegaInicioCancela() {
        findNavController().navigate(R.id.action_fragmentNovoVacinacao_to_fragmentInicioPage)
        Toast.makeText(
            requireContext(),
            "Cancelado com sucesso",
            Toast.LENGTH_LONG
        ).show()
    }
    fun navegaInicio() {
        findNavController().navigate(R.id.action_fragmentNovoVacinacao_to_fragmentInicioPage)

    }
    fun guardar() {
        // todo: guardar livro
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_novo_vacinacao -> guardar()
            R.id.action_cancelar_novo_vacinacao -> navegaInicioCancela()
            else -> return false
        }

        return true
    }

}