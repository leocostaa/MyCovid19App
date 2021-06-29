package com.example.mycovid19app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mycovid19app.databinding.FragmentInicioPageBinding

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentInicioPage.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentInicioPage : Fragment() {

    private var _binding: FragmentInicioPageBinding? = null

    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
    companion object {

    }
}