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
import android.widget.Spinner
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController


class FragmentEditaLocal : Fragment()  {
    private lateinit var editTextCidadeEdita: EditText
    private lateinit var editTextLocalAdmEdita: EditText
    private lateinit var editTextSalaEdita: EditText

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_edita_local

        return inflater.inflate(R.layout.fragment_edita_local, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextCidadeEdita = view.findViewById(R.id.editTextCidadeEdita)
        editTextLocalAdmEdita = view.findViewById(R.id.editTextLocalAdmEdita)
        editTextSalaEdita = view.findViewById(R.id.editTextSalaEdita)

        editTextCidadeEdita.setText(DadosApp.localSelecionado!!.cidade)
        editTextLocalAdmEdita.setText(DadosApp.localSelecionado!!.localadm)
        editTextSalaEdita.setText(DadosApp.localSelecionado!!.sala)
    }
    fun navegaLocal() {
        findNavController().navigate(R.id.action_fragmentEditaLocal_to_FragmentLocal)
    }

    fun guardar() {
        val Cidade = editTextCidadeEdita.text.toString()
        if (Cidade.isEmpty()) {
            editTextCidadeEdita.setError("Preenche este campo")
            editTextCidadeEdita.requestFocus()
            return
        }

        val LocalAdm = editTextLocalAdmEdita.text.toString()
        if (LocalAdm.isEmpty()) {
            editTextLocalAdmEdita.setError("Preencha este campo")
            editTextLocalAdmEdita.requestFocus()
            return
        }

        val Sala = editTextSalaEdita.text.toString()
        if (Sala.isEmpty()) {
            editTextSalaEdita.setError("Preencha este campo")
            editTextSalaEdita.requestFocus()
            return
        }


        val local = DadosApp.localSelecionado!!
        local.cidade = Cidade
        local.localadm = LocalAdm
        local.sala = Sala

        val uriLocal = Uri.withAppendedPath(
            ContentProviderApp.ENDERECO_LOCAL,
            local.id.toString()
        )

        val registos = activity?.contentResolver?.update(
            uriLocal,
            local.toContentValues(),
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
        navegaLocal()
    }
    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_edita_local -> guardar()
            R.id.action_cancelar_edita_local -> navegaLocal()
            else -> return false
        }

        return true
    }





}