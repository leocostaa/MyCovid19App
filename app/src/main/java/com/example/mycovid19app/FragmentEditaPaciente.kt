package com.example.mycovid19app

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SimpleCursorAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import java.text.SimpleDateFormat


class FragmentEditaPaciente : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private lateinit var editTextNomeEdita: EditText
    private lateinit var editTextDataNascimentoEdita: EditText
    private lateinit var spinnerSexoEdita: Spinner



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_edita_paciente

        return inflater.inflate(R.layout.fragment_edita_paciente, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNomeEdita = view.findViewById(R.id.editTextNomeEdita)
        editTextDataNascimentoEdita = view.findViewById(R.id.editTextDataEdita)
        spinnerSexoEdita = view.findViewById(R.id.spinnerSexoEdita)

        editTextNomeEdita.setText(DadosApp.PacienteSelecionado!!.nome)
        editTextDataNascimentoEdita.setText(DadosApp.PacienteSelecionado!!.DataNascimento.toString())

        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_SEXO_EDITA, null, this)

    }
    fun navegaPaciente() {
        findNavController().navigate(R.id.action_fragmentEditaPaciente_to_fragmentInicioPage)
    }
    fun guardar(){


        val nome = editTextNomeEdita.text.toString()
        if (nome.isEmpty()) {
            editTextNomeEdita.setError("Preencha")
            editTextNomeEdita.requestFocus()
            return
        }

        val data = editTextDataNascimentoEdita .text.toString()
        val simpleDateFormat  = SimpleDateFormat("dd/MM/yyyy")
        val date = simpleDateFormat.parse(data)

        val sexo  =  spinnerSexoEdita.selectedItemId


        val paciente = DadosApp.PacienteSelecionado!!
        paciente.nome = nome
        paciente.DataNascimento = date
        paciente.sexo = sexo.toString()

        val uriPaciente = Uri.withAppendedPath(
            ContentProviderApp.ENDERECO_PACIENTE,
            paciente.id.toString()
        )

        val registos = activity?.contentResolver?.update(
            uriPaciente,
            paciente.toContentValues(),
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                "Erro ao alterar",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            "Alterado com sucesso",
            Toast.LENGTH_LONG
        ).show()
        navegaPaciente()
    }
    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_edita_paciente -> guardar()
            R.id.action_cancelar_edita_paciente -> navegaPaciente()
            else -> return false
        }

        return true
    }


    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            ContentProviderApp.ENDERECO_PACIENTE,
            TabelaPaciente.TODAS_COLUNAS,
            null, null,
            TabelaPaciente.CAMPO_SEXO
        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        atualizaSpinnerSexo(data)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        atualizaSpinnerSexo(null)
    }
    private fun atualizaSpinnerSexo(data: Cursor?) {
        spinnerSexoEdita.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaPaciente.CAMPO_SEXO),
            intArrayOf(android.R.id.text1),
            0
        )
    }
    companion object {
        const val ID_LOADER_MANAGER_SEXO_EDITA = 0
    }

}