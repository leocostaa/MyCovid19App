package com.example.mycovid19app

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController



class FragmentEliminaPaciente : Fragment() {

    private lateinit var textViewNomeEliminar: TextView
    private lateinit var textViewDataNascimentoEliminar: TextView
    private lateinit var textViewSexoEliminar: TextView




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_elimina_paciente

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_elimina_paciente, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewNomeEliminar = view.findViewById(R.id.textViewEliminaN)
        textViewDataNascimentoEliminar = view.findViewById(R.id.textViewEliminaDN)
        textViewSexoEliminar = view.findViewById(R.id.textViewEliminaS)

        val paciente = DadosApp.PacienteSelecionado!!
        textViewNomeEliminar.setText(paciente.nome)
        textViewDataNascimentoEliminar.setText(paciente.DataNascimento.toString())
        textViewSexoEliminar.setText(paciente.sexo)
    }
    fun navegaPacientes() {
        findNavController().navigate(R.id.action_fragmentEliminaPaciente_to_fragmentInicioPage)
    }
    fun elimina() {
        val uriPacientes = Uri.withAppendedPath(
            ContentProviderApp.ENDERECO_PACIENTE,
            DadosApp.PacienteSelecionado!!.id.toString()
        )

        val registos = activity?.contentResolver?.delete(
            uriPacientes,
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                "Erro ao eliminar paciente",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            "Paciente eliminado com sucesso",
            Toast.LENGTH_LONG
        ).show()

        navegaPacientes()
    }
    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_confirma_eliminar_paciente -> elimina()
            R.id.action_cancelar_eliminar_paciente -> navegaPacientes()
            else -> return false
        }

        return true
    }

}