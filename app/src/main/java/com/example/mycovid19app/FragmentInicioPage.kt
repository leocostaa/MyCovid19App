package com.example.mycovid19app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.mycovid19app.databinding.FragmentInicioPageBinding


class FragmentInicioPage : Fragment() {

    private var _binding: FragmentInicioPageBinding? = null

    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment= this
        (activity as MainActivity).menuAtual = R.menu.menu_inicio_page

        _binding = FragmentInicioPageBinding.inflate(inflater, container, false)
        return binding.root
           }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonPacientes.setOnClickListener {
            findNavController().navigate(R.id.action_FragmentInicio_to_FragmentPaciente)
        }
        binding.buttonLocais.setOnClickListener {
            findNavController().navigate(R.id.action_FragmentInicio_to_FragmentLocal)
        }
        binding.buttonVacinacoes.setOnClickListener {
            findNavController().navigate(R.id.action_FragmentInicio_to_FragmentVacinacao)
        }
        binding.buttonVacina.setOnClickListener {
            findNavController().navigate(R.id.action_FragmentInicio_to_FragmentVacina)
        }

    }
    fun navegaInicioCancela(){
        findNavController().navigate(R.id.action_fragmentInicioPage_to_fragmentLogin)
    }
    fun info(){
        Toast.makeText(requireContext(),"APP ",Toast.LENGTH_LONG)

    }
    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_login -> navegaInicioCancela()
            R.id.action_info -> info()
            else -> return false
        }
        return true
    }

    companion object {

    }
}