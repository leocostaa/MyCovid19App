package com.example.mycovid19app

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
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

        _binding = FragmentVacinacaoBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerViewVacinacao = view.findViewById<RecyclerView>(R.id.recyclerViewVacinacao)
        adapterVacinacao = AdapterVacinacao()
        recyclerViewVacinacao.adapter = adapterVacinacao
        recyclerViewVacinacao.layoutManager = LinearLayoutManager(requireContext())

        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_VACINACAO, null, this)
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
        TODO("Not yet implemented")
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {

    }

    companion object{
        const val ID_LOADER_MANAGER_VACINACAO = 0
    }
}