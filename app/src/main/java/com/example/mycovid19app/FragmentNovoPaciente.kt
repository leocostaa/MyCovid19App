package com.example.mycovid19app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import androidx.navigation.fragment.findNavController
import com.example.mycovid19app.databinding.FragmentNovoPacienteBinding



/**
 * A simple [Fragment] subclass.
 * Use the [FragmentNovoPaciente.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentNovoPaciente : Fragment() {

    private var _binding: FragmentNovoPacienteBinding? = null

    private lateinit var editTextNome: EditText
    private lateinit var editTextData: EditText
    private lateinit var spinnerSexo: Spinner

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

 

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.FragmentNovoPaciente = this
        (activity as MainActivity).menuAtual = R.menu.menu_novo_paciente

        _binding = FragmentNovoPacienteBinding.inflate(inflater, container, false)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNome = view.findViewById(R.id.editTextNome)
        editTextData = view.findViewById(R.id.editTextDataNascimento)
        spinnerSexo = view.findViewById(R.id.spinnerSexo)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun navegaListaLivros() {
        findNavController().navigate(R.id.action_FragmentNovoPaciente_to_FragmentPaciente)
    }

    fun guardar() {
        // todo: guardar livro
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_novo_paciente -> guardar()
            R.id.action_cancelar_novo_paciente -> navegaListaLivros()
            else -> return false
        }

        return true
    }
}