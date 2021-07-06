package com.example.mycovid19app

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mycovid19app.databinding.FragmentPacientesBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FragmentPacientes : Fragment(), LoaderManager.LoaderCallbacks<Cursor>{

    private var _binding: FragmentPacientesBinding? = null
    private var adapterPacientes: AdapterPacientes? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_pacientes


        _binding = FragmentPacientesBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_PACIENTES, null, this)

        val recyclerViewPacientes = view.findViewById<RecyclerView>(R.id.recyclerViewPacientes)

        adapterPacientes = AdapterPacientes(this)
        recyclerViewPacientes.adapter = adapterPacientes
        recyclerViewPacientes.layoutManager= LinearLayoutManager(requireContext())


    }
    fun navegaNovoPaciente() {
        findNavController().navigate(R.id.action_FragmentPaciente_to_FragmentNovoPaciente)
    }
    fun navegaAlterarPaciente() {
        //todo: navegar para o fragmento da edição de um livro
    }

    fun navegaEliminarPaciente() {
        findNavController().navigate(R.id.action_FragmentPaciente_to_fragmentEliminaPaciente)
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_novo_paciente -> navegaNovoPaciente()
            R.id.action_alterar_paciente-> navegaAlterarPaciente()
            R.id.action_eliminar_paciente -> navegaEliminarPaciente()
            else -> return false
        }

        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            ContentProviderApp.ENDERECO_PACIENTE,
            TabelaPaciente.TODAS_COLUNAS,
            null,null,
            TabelaPaciente.CAMPO_NOME
        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapterPacientes!!.cursor = data
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapterPacientes!!.cursor = null
    }
    companion object{
        const val ID_LOADER_MANAGER_PACIENTES = 0
    }
}