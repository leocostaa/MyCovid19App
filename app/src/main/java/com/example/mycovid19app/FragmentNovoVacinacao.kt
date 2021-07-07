package com.example.mycovid19app

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.SimpleCursorAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import com.example.mycovid19app.databinding.FragmentNovoVacinacaoBinding
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*


class FragmentNovoVacinacao : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

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

        LoaderManager.getInstance(this).initLoader(ID_LOADER_MANAGER_LAB, null, this)
        LoaderManager.getInstance(this).initLoader(ID_LOADER_MANAGER_NOMEPAC, null, this)
        LoaderManager.getInstance(this).initLoader(ID_LOADER_MANAGER_LOCALVAC, null, this)
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
        
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_novo_vacinacao -> guardar()
            R.id.action_cancelar_novo_vacinacao -> navegaInicioCancela()
            else -> return false
        }

        return true
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {

        when (id) {
            ID_LOADER_MANAGER_LAB-> return CursorLoader(
                requireContext(),
                 ContentProviderApp.ENDERECO_VACINA,
                 TabelaVacina.TODAS_COLUNAS,
                null, null,
                   TabelaVacina.CAMPO_ORIGEM
            )
            ID_LOADER_MANAGER_NOMEPAC-> return CursorLoader(
                requireContext(),
                ContentProviderApp.ENDERECO_PACIENTE,
                TabelaPaciente.TODAS_COLUNAS,
                null, null,
                TabelaPaciente.CAMPO_NOME
            )
            ID_LOADER_MANAGER_LOCALVAC-> return CursorLoader(
                requireContext(),
                ContentProviderApp.ENDERECO_LOCAL,
                TabelaLocal.TODAS_COLUNAS,
                null, null,
                TabelaLocal.CAMPO_LOCALADM
            )
        }
        return CursorLoader(requireContext())
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {

        when(loader.id){
            ID_LOADER_MANAGER_LAB ->atualizaSpinnerLab(data)
            ID_LOADER_MANAGER_NOMEPAC ->atualizaSpinnerNomePaciente(data)
            ID_LOADER_MANAGER_LOCALVAC ->atualizaSpinnerlocal(data)

        }

    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        atualizaSpinnerLab(null)
        atualizaSpinnerNomePaciente(null)
        atualizaSpinnerlocal(null)

    }
    private fun atualizaSpinnerLab(data: Cursor?) {
        spinnerLOnovo.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaVacina.CAMPO_ORIGEM),
            intArrayOf(android.R.id.text1),
            0
        )
    }
    private fun atualizaSpinnerNomePaciente(data: Cursor?) {
        spinnerNPnovo.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaPaciente.CAMPO_NOME),
            intArrayOf(android.R.id.text1),
            0
        )
    }
    private fun atualizaSpinnerlocal(data: Cursor?) {
        spinnerLAnovo.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaLocal.CAMPO_LOCALADM),
            intArrayOf(android.R.id.text1),
            0
        )
    }

    companion object {
        const val ID_LOADER_MANAGER_LAB = 0
        const val ID_LOADER_MANAGER_NOMEPAC = 1
        const val ID_LOADER_MANAGER_LOCALVAC = 2

    }

}