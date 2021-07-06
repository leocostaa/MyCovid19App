package com.example.mycovid19app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.mycovid19app.databinding.FragmentNovoVacinaBinding
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat

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
        val origem = editTextOrigem.text.toString()
        if (origem.isEmpty()) {
            editTextOrigem.setError("Preencha este campo")
            return
        }

        val validade = editTextValidade.text.toString()
        val simpleDateFormat  = SimpleDateFormat("dd/MM/yyyy")
        val date = simpleDateFormat.parse(validade)
        if (validade.isEmpty()) {
            editTextValidade.setError("Preencha este campo")
            return
        }

        val quantidade = editTextQuantidade.text.toString()
        val qnt = Integer.parseInt(quantidade)

        if (quantidade.isEmpty()) {
            editTextQuantidade.setError("Preencha este campo")
            return
        }

        val vacina = Vacina(origem = origem, validade = date, quantidade = qnt)

        val uri = activity?.contentResolver?.insert(
            ContentProviderApp.ENDERECO_VACINA,
            vacina.toContentValues()
        )

        if (uri == null) {
            Snackbar.make(
                editTextOrigem,
                ("erro ao inserir "),
                Snackbar.LENGTH_LONG
            ).show()
            return
        }
        Toast.makeText(
            requireContext(),
            "Guardado com sucesso",
            Toast.LENGTH_LONG
        ).show()
        navegaInicioPage()
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