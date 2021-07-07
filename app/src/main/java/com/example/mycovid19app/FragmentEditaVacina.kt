package com.example.mycovid19app

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
import androidx.navigation.fragment.findNavController
import java.text.SimpleDateFormat


class FragmentEditaVacina : Fragment() {
    private lateinit var editTextOrigemEdita: EditText
    private lateinit var editTextQuantidadeEdita: EditText
    private lateinit var editTextValidadeEdita : EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_edita_vacina

        return inflater.inflate(R.layout.fragment_edita_vacina, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextOrigemEdita = view.findViewById(R.id.editTextOrigemEdita)
        editTextQuantidadeEdita = view.findViewById(R.id.editTextQuantidadeEdita)
        editTextValidadeEdita = view.findViewById(R.id.editTextValidadeEdita)

        editTextOrigemEdita.setText(DadosApp.vacinaSelecionado!!.origem)
        editTextQuantidadeEdita.setText(DadosApp.vacinaSelecionado!!.quantidade.toString())
        editTextValidadeEdita.setText(DadosApp.vacinaSelecionado!!.validade.toString())


    }
    fun navegaInicio() {
        findNavController().navigate(R.id.action_fragmentEditaVacina_to_fragmentInicioPage)
    }
    fun guardar() {

        val origem = editTextOrigemEdita.text.toString()
        if (origem.isEmpty()) {
            editTextOrigemEdita.setError("Preencha")
            editTextOrigemEdita.requestFocus()
            return
        }

        val quantidade = editTextQuantidadeEdita.text.toString()
        val qnt = Integer.parseInt(quantidade)
        if (quantidade.isEmpty()) {
            editTextQuantidadeEdita.setError(getString(R.string.Preencha))
            editTextQuantidadeEdita.requestFocus()
            return
        }
        val validade = editTextValidadeEdita.text.toString()
        val simpleDateFormat  = SimpleDateFormat("dd/MM/yyyy")
        val date = simpleDateFormat.parse(validade)
        if (validade.isEmpty()) {
            editTextValidadeEdita.setError(getString(R.string.Preencha))
            editTextValidadeEdita.requestFocus()
            return
        }



        val vacina = DadosApp.vacinaSelecionado!!
        vacina.origem = origem
        vacina.quantidade = qnt
        vacina.validade = date

        val uriVacina = Uri.withAppendedPath(
            ContentProviderApp.ENDERECO_VACINA,
            vacina.id.toString()
        )

        val registos = activity?.contentResolver?.update(
            uriVacina,
            vacina.toContentValues(),
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                getString(R.string.vac_edit_err),
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            getString(R.string.vac_edit_succ),
            Toast.LENGTH_LONG
        ).show()
        navegaInicio()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_edita_vacina -> guardar()
            R.id.action_cancelar_edita_vacina -> navegaInicio()
            else -> return false
        }

        return true
    }










}