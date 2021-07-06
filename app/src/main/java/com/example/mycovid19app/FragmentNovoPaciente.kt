package com.example.mycovid19app

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import com.example.mycovid19app.databinding.FragmentNovoPacienteBinding
import com.google.android.material.snackbar.Snackbar
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [FragmentNovoPaciente.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentNovoPaciente : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private var _binding: FragmentNovoPacienteBinding? = null

    private lateinit var editTextNome: EditText
    private lateinit var editTextDate: EditText
    private lateinit var spinnerSexo: Spinner


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment= this
        (activity as MainActivity).menuAtual = R.menu.menu_novo_paciente

        _binding = FragmentNovoPacienteBinding.inflate(inflater, container, false)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNome = view.findViewById(R.id.editTextNome)
        editTextDate = view.findViewById(R.id.editTextDate)
        spinnerSexo = view.findViewById(R.id.spinnerSexo)

        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_SEXO, null, this)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun navegaPaciente() {
        findNavController().navigate(R.id.action_fragmentNovoPaciente_to_fragmentInicioPage)
    }

    fun guardar() {

        val Sexo = spinnerSexo.selectedItemId

        val nome = editTextNome.text.toString()
        if (nome.isEmpty()) {
            editTextNome.setError("Preencha")
            editTextNome.requestFocus()
            return
        }

        val data  =  editTextDate.text.toString()
        val simpleDateFormat  = SimpleDateFormat("dd/MM/yyyy")
        val date = simpleDateFormat.parse(data)
        if (data.isEmpty()) {
            editTextDate.setError("Preencha")
            editTextDate.requestFocus()
            return
        }




        val paciente = Paciente(nome = nome, DataNascimento = date, sexo = Sexo.toString())

        val uri = activity?.contentResolver?.insert(
            ContentProviderApp.ENDERECO_PACIENTE,
            paciente.toContentValues()
        )

        if (uri == null) {
            Snackbar.make(
                editTextNome,
                ("Erro ao inserir "),
                Snackbar.LENGTH_LONG
            ).show()
            return
        }
        Toast.makeText(
            requireContext(),
            "Paciente criado com sucesso",
            Toast.LENGTH_LONG
        ).show()
        navegaPaciente()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_novo_paciente -> guardar()
            R.id.action_cancelar_novo_paciente -> navegaPaciente()
            else -> return false
        }

        return true
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            ContentProviderApp.ENDERECO_PACIENTE,
            TabelaPaciente.TODAS_COLUNAS,
            null, null,
            TabelaPaciente.CAMPO_SEXO
        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        atualizaSpinnerSexo(data)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        atualizaSpinnerSexo(null)
    }

    private fun atualizaSpinnerSexo(data: Cursor?) {
        spinnerSexo.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaPaciente.CAMPO_SEXO),
            intArrayOf(android.R.id.text1),
            0
        )
    }
    companion object{
        const val ID_LOADER_MANAGER_SEXO = 0
    }
}