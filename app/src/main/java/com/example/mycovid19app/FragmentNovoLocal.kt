package com.example.mycovid19app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mycovid19app.databinding.FragmentNovoLocalBinding


class FragmentNovoLocal : Fragment() {

    private var _binding: FragmentNovoLocalBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


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

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun navegaLocal() {
        findNavController().navigate(R.id.action_fragmentNovoLocal_to_FragmentLocal)
    }

    fun guardar() {
        // todo: guardar local
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