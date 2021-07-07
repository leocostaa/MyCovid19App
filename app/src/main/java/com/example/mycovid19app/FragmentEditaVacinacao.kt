package com.example.mycovid19app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Spinner


class FragmentEditaVacinacao : Fragment() {

    private lateinit var spinnerLOedita: Spinner
    private lateinit var spinnerNPedita: Spinner
    private lateinit var spinnerLAedita: Spinner
    private lateinit var CalendarViewedita: CalendarView

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return false
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_edita_vacinacoes

        return inflater.inflate(R.layout.fragment_edita_vacinacao, container, false)
    }


}