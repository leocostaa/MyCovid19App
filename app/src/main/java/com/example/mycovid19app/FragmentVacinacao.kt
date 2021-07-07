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
import com.example.mycovid19app.databinding.FragmentVacinacaoBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FragmentVacinacao : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private var _binding: FragmentVacinacaoBinding? = null
    private var adapterVacinacao : AdapterVacinacao? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_vacinacao




        _binding = FragmentVacinacaoBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerViewVacinacao = view.findViewById<RecyclerView>(R.id.recyclerViewVacinacao)
        adapterVacinacao = AdapterVacinacao(this)
        recyclerViewVacinacao.adapter = adapterVacinacao
        recyclerViewVacinacao.layoutManager = LinearLayoutManager(requireContext())

        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_VACINACAO, null, this)


    }
    fun navegaNovoVacincao() {
        findNavController().navigate(R.id.action_FragmentVacinacao_to_fragmentNovoVacinacao)
    }
    fun navegaAlterarVacinacao() {
        findNavController().navigate(R.id.action_FragmentVacinacao_to_fragmentEditaVacinacao)
    }

    fun navegaEliminarVacinacao() {
        findNavController().navigate(R.id.action_FragmentVacinacao_to_fragmentEliminaVacinacao)
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_novo_vacinacao -> navegaNovoVacincao()
            R.id.action_alterar_vacinacao -> navegaAlterarVacinacao()
            R.id.action_eliminar_vacinacao -> navegaEliminarVacinacao()
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
            ContentProviderApp.ENDERECO_VACINACAO,
            TabelaVacinacao.TODAS_COLUNAS,
            null, null,
            TabelaVacinacao.CAMPO_DATAVAC
        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapterVacinacao!!.cursor = data
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapterVacinacao!!.cursor = null
    }

    companion object{
        const val ID_LOADER_MANAGER_VACINACAO = 0
    }
}