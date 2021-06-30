package com.example.mycovid19app

import android.app.DatePickerDialog
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.mycovid19app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var menu: Menu

    var menuAtual = R.menu.menu_pacientes
        set(value) {
            field = value
            invalidateOptionsMenu()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)


        DadosApp.activity = this
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.

        menuInflater.inflate(menuAtual, menu)
        this.menu = menu

        if (menuAtual == R.menu.menu_pacientes ) {
            atualizaMenuPacientes(false)
        }
        if (menuAtual == R.menu.menu_locais ) {
            atualizaMenuLocais(false)
        }


        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val opcaoProcessada= when (item.itemId) {

            R.id.action_settings -> {
                Toast.makeText(this, R.string.versao, Toast.LENGTH_LONG).show()
                true
            }
            else -> when(menuAtual) {
                R.menu.menu_pacientes -> (DadosApp.fragment as FragmentPacientes).processaOpcaoMenu(item)
                R.menu.menu_novo_paciente -> (DadosApp.fragment as FragmentNovoPaciente).processaOpcaoMenu(item)
                R.menu.menu_inicio_page ->(DadosApp.fragment as FragmentInicioPage).processaOpcaoMenu(item)
                R.menu.menu_locais ->(DadosApp.fragment as FragmentLocal).processaOpcaoMenu(item)
                R.menu.menu_novo_local->(DadosApp.fragment as FragmentNovoLocal).processaOpcaoMenu(item)
                R.menu.menu_edita_local -> (DadosApp.fragment as FragmentEditaLocal).processaOpcaoMenu(item)

                else -> false
            }
        }
        return if(opcaoProcessada) true else super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    fun atualizaMenuPacientes(mostraBotoesAlterarEliminar : Boolean) {
        menu.findItem(R.id.action_alterar_paciente).setVisible(mostraBotoesAlterarEliminar)
        menu.findItem(R.id.action_eliminar_paciente).setVisible(mostraBotoesAlterarEliminar)
    }
    fun atualizaMenuLocais(mostraBotoesAlterarEliminar : Boolean) {
        menu.findItem(R.id.action_alterar_local).setVisible(mostraBotoesAlterarEliminar)
        menu.findItem(R.id.action_eliminar_local).setVisible(mostraBotoesAlterarEliminar)
    }
}