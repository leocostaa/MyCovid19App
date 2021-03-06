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
import com.example.mycovid19app.databinding.FragmentLocalBinding

class FragmentLocal : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private var _binding: FragmentLocalBinding? = null
    private var adapterLocais : AdapterLocais? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_locais

        _binding = FragmentLocalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerViewLocal = view.findViewById<RecyclerView>(R.id.recyclerViewLocal)
        adapterLocais = AdapterLocais(this)
        recyclerViewLocal.adapter = adapterLocais
        recyclerViewLocal.layoutManager = LinearLayoutManager(requireContext())


        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_LOCAIS, null, this)


    }
    fun navegaNovoLocal() {
        findNavController().navigate(R.id.action_FragmentLocal_to_fragmentNovoLocal)
    }
    fun navegaAlterarLocal() {
        findNavController().navigate(R.id.action_FragmentLocal_to_fragmentEditaLocal)

    }

    fun navegaEliminarLocal() {
        findNavController().navigate(R.id.action_FragmentLocal_to_fragmentEliminaLocal)
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_novo_local -> navegaNovoLocal()
            R.id.action_alterar_local -> navegaAlterarLocal()
            R.id.action_eliminar_local -> navegaEliminarLocal()
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
            ContentProviderApp.ENDERECO_LOCAL,
            TabelaLocal.TODAS_COLUNAS,
            null, null,
            TabelaLocal.CAMPO_CIDADE
        )
    }


    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapterLocais!!.cursor = data
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapterLocais!!.cursor = null
    }

    companion object {
        const val ID_LOADER_MANAGER_LOCAIS = 0
    }
}