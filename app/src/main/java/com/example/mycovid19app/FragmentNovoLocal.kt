package com.example.mycovid19app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.mycovid19app.databinding.FragmentNovoLocalBinding
import com.google.android.material.snackbar.Snackbar


class FragmentNovoLocal : Fragment() {

    private var _binding: FragmentNovoLocalBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var editTextCidade: EditText
    private lateinit var editTextLocalAdm: EditText
    private lateinit var editTextSala: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_novo_local

        _binding = FragmentNovoLocalBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editTextCidade = view.findViewById(R.id.editTextCidade)
        editTextLocalAdm = view.findViewById(R.id.editTextLocalAdm)
        editTextSala = view.findViewById(R.id.editTextSala)

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun navegaLocal() {
        findNavController().navigate(R.id.action_fragmentNovoLocal_to_fragmentInicioPage)
    }

    fun guardar() {
        val Cidade = editTextCidade.text.toString()
        if (Cidade.isEmpty()) {
            editTextCidade.setError("Preencha este campo")
            editTextCidade.requestFocus()
            return
        }

        val LocalAdm = editTextLocalAdm.text.toString()
        if (LocalAdm.isEmpty()) {
            editTextLocalAdm.setError("Preencha este campo")
            editTextLocalAdm.requestFocus()
            return
        }

        val Sala = editTextSala.text.toString()
        if (Sala.isEmpty()) {
            editTextSala.setError("Preencha este campo")
            editTextSala.requestFocus()
            return
        }

        val local = Local(cidade = Cidade, localadm = LocalAdm, sala = Sala)

        val uri = activity?.contentResolver?.insert(
            ContentProviderApp.ENDERECO_LOCAL,
            local.toContentValues()
        )

        if (uri == null) {
            Snackbar.make(
                editTextCidade,
                ("erro ao inserir "),
                Snackbar.LENGTH_LONG
            ).show()
            return
        }
        Toast.makeText(
            requireContext(),
            "Local gravado com sucesso",
            Toast.LENGTH_LONG
        ).show()

        navegaLocal()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_novo_local -> guardar()
            R.id.action_cancelar_novo_local -> navegaLocal()
            else -> return false
        }

        return true
    }
}