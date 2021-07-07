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
import com.example.mycovid19app.databinding.FragmentVacinaBinding


class FragmentVacina : Fragment(),LoaderManager.LoaderCallbacks<Cursor>{

    private var _binding: FragmentVacinaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var adapterVacinas : AdapterVacinas? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_vacinas

        _binding = FragmentVacinaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerViewVacinas = view.findViewById<RecyclerView>(R.id.recyclerViewVacinas)
        adapterVacinas = AdapterVacinas(this)
        recyclerViewVacinas.adapter = adapterVacinas
        recyclerViewVacinas.layoutManager = LinearLayoutManager(requireContext())



        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_VACINAS, null, this)
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
    }
    fun navegaNovoVacina() {
        findNavController().navigate(R.id.action_FragmentVacina_to_fragmentNovoVacina)
    }
    fun navegaAlterarVacina() {
        findNavController().navigate(R.id.action_FragmentVacina_to_fragmentEditaVacina)

    }

    fun navegaEliminarVacina() {
        findNavController().navigate(R.id.action_FragmentVacina_to_fragmentEliminaVacina)
    }
    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_novo_vacina -> navegaNovoVacina()
            R.id.action_alterar_vacina -> navegaAlterarVacina()
            R.id.action_eliminar_vacina -> navegaEliminarVacina()
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
            ContentProviderApp.ENDERECO_VACINA,
            TabelaVacina.TODAS_COLUNAS,
            null, null,
            TabelaVacina.CAMPO_ORIGEM
        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapterVacinas!!.cursor = data
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapterVacinas!!.cursor = null
    }

    companion object{
        const val ID_LOADER_MANAGER_VACINAS = 0
    }
}