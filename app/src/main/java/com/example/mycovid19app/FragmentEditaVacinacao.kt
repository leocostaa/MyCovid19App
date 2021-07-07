package com.example.mycovid19app

import android.database.Cursor
import android.net.Uri
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
import java.util.*


class FragmentEditaVacinacao : Fragment(),LoaderManager.LoaderCallbacks<Cursor> {

    private lateinit var spinnerLOedita: Spinner
    private lateinit var spinnerNPedita: Spinner
    private lateinit var spinnerLAedita: Spinner
    private lateinit var CalendarViewedita: CalendarView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_edita_vacinacoes

        return inflater.inflate(R.layout.fragment_edita_vacinacao, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        spinnerLOedita = view.findViewById(R.id.spinnerLOedita)
        spinnerNPedita = view.findViewById(R.id.spinnerNPedita)
        spinnerLAedita = view.findViewById(R.id.spinnerLAedita)
        CalendarViewedita = view.findViewById(R.id.calendarViewEdita)

        LoaderManager.getInstance(this).initLoader(ID_LOADER_MANAGER_LABed, null, this)
        LoaderManager.getInstance(this).initLoader(ID_LOADER_MANAGER_NOMEPACed, null, this)
        LoaderManager.getInstance(this).initLoader(ID_LOADER_MANAGER_LOCALVACed, null, this)
    }
    fun navegaInicioCancela() {
        findNavController().navigate(R.id.action_fragmentEditaVacinacao_to_fragmentInicioPage)
        Toast.makeText(
            requireContext(),
            "Cancelado com sucesso",
            Toast.LENGTH_LONG
        ).show()
    }
    fun navegaInicio() {
        findNavController().navigate(R.id.action_fragmentEditaVacinacao_to_fragmentInicioPage)

    }
    fun guardar() {
        val origemnovo = spinnerLOedita.selectedItemId

        val nomenovo = spinnerNPedita.selectedItemId

        val localnovo = spinnerLAedita.selectedItemId

        val dataval = CalendarViewedita.date

        val vacinacao = DadosApp.vacinacaoSelecionado!!
        vacinacao.idVacina = origemnovo
        vacinacao.idPaciente = nomenovo
        vacinacao.idLocal = localnovo
        vacinacao.data_vac = Date(dataval)


        val uriVacinacao = Uri.withAppendedPath(
            ContentProviderApp.ENDERECO_VACINACAO,
            vacinacao.id.toString()
        )

        val registos = activity?.contentResolver?.update(
            uriVacinacao,
            vacinacao.toContentValues(),
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                getString(R.string.vacc_edit_err),
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            getString(R.string.vacc_edit_succ),
            Toast.LENGTH_LONG
        ).show()
        navegaInicio()
    }
    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_edita_vacinacao -> guardar()
            R.id.action_cancelar_edita_vacinacao -> navegaInicioCancela()
            else -> return false
        }

        return true
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {

        when (id) {
            ID_LOADER_MANAGER_LABed-> return CursorLoader(
                requireContext(),
                ContentProviderApp.ENDERECO_VACINA,
                TabelaVacina.TODAS_COLUNAS,
                null, null,
                TabelaVacina.CAMPO_ORIGEM
            )
            ID_LOADER_MANAGER_NOMEPACed-> return CursorLoader(
                requireContext(),
                ContentProviderApp.ENDERECO_PACIENTE,
                TabelaPaciente.TODAS_COLUNAS,
                null, null,
                TabelaPaciente.CAMPO_NOME
            )
            ID_LOADER_MANAGER_LOCALVACed-> return CursorLoader(
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
            ID_LOADER_MANAGER_LABed ->atualizaSpinnerLab(data)
            ID_LOADER_MANAGER_NOMEPACed ->atualizaSpinnerNomePaciente(data)
            ID_LOADER_MANAGER_LOCALVACed ->atualizaSpinnerlocal(data)

        }
        atualizaOrigemSelecionado()
        atualizaNomePacSelecionado()
        atualizaLocalAdmSelecionado()
    }

    private fun atualizaOrigemSelecionado() {
        val origem = DadosApp.vacinacaoSelecionado!!.idVacina

        val ultimoOrigem = spinnerLOedita.count - 1
        for (i in 0..ultimoOrigem) {
            if (origem == spinnerLOedita.getItemIdAtPosition(i)) {
                spinnerLOedita.setSelection(i)
                return
            }
        }
    }
    private fun atualizaNomePacSelecionado() {
        val nome = DadosApp.vacinacaoSelecionado!!.idPaciente

        val ultimoNome = spinnerNPedita.count - 1
        for (i in 0..ultimoNome) {
            if (nome == spinnerNPedita.getItemIdAtPosition(i)) {
                spinnerNPedita.setSelection(i)
                return
            }
        }
    }
    private fun atualizaLocalAdmSelecionado() {
        val local = DadosApp.vacinacaoSelecionado!!.idLocal

        val ultimoLocal = spinnerLAedita.count - 1
        for (i in 0..ultimoLocal) {
            if (local == spinnerLAedita.getItemIdAtPosition(i)) {
                spinnerLAedita.setSelection(i)
                return
            }
        }
    }
    override fun onLoaderReset(loader: Loader<Cursor>) {
        atualizaSpinnerLab(null)
        atualizaSpinnerNomePaciente(null)
        atualizaSpinnerlocal(null)

    }
    private fun atualizaSpinnerLab(data: Cursor?) {
        spinnerLOedita.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaVacina.CAMPO_ORIGEM),
            intArrayOf(android.R.id.text1),
            0
        )
    }
    private fun atualizaSpinnerNomePaciente(data: Cursor?) {
        spinnerNPedita.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaPaciente.CAMPO_NOME),
            intArrayOf(android.R.id.text1),
            0
        )
    }
    private fun atualizaSpinnerlocal(data: Cursor?) {
        spinnerLAedita.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaLocal.CAMPO_LOCALADM),
            intArrayOf(android.R.id.text1),
            0
        )
    }

    companion object {
        const val ID_LOADER_MANAGER_LABed = 0
        const val ID_LOADER_MANAGER_NOMEPACed = 1
        const val ID_LOADER_MANAGER_LOCALVACed = 2

    }


}