package com.example.mycovid19app

import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private fun getAppContext() = InstrumentationRegistry.getInstrumentation().targetContext
    private fun GetbdAppOpenHelper() = BdAppOpenHelper(getAppContext())

    private fun inserePaciente(tabela: TabelaPaciente, paciente: Paciente): Long {
        val id = tabela.insert(paciente.toContentValues())
        assertNotEquals(-1, id)
        return id

    }
    private fun insereVacina(tabela: TabelaVacina, vacina: Vacina): Long {
        val id = tabela.insert(vacina.toContentValues())
        assertNotEquals(-1, id)
        return id

    }



    @Before
    fun apagarBaseDados(){
        getAppContext().deleteDatabase((BdAppOpenHelper.NOME_BASE_DADOS))

    }

    @Test
    fun consegueAbrirBaseDados(){
        val dbOpenHelper = BdAppOpenHelper(getAppContext())
        val db = dbOpenHelper.readableDatabase
        assert(db.isOpen)
        db.close()
    }

    @Test
    fun consegueInserirPacientes() {
        //linhas para ler a base de dados
        val db = GetbdAppOpenHelper().writableDatabase
        val tabelaPaciente = TabelaPaciente(db)

        val paciente = Paciente(nome = "Joana Sousa", DataNascimento = "22/12/1999", sexo = "F", infecao = 0)
        paciente.id = inserePaciente(tabelaPaciente, paciente)
        db.close()
    }

    @Test
    fun consegueAlterarPacientes() {
        val db = GetbdAppOpenHelper().writableDatabase
        val tabelaPaciente = TabelaPaciente(db)

        val paciente = Paciente(nome = "Mariana Nunes", DataNascimento = "12/08/1990", sexo = "F", infecao = 0)
        paciente.id = inserePaciente(tabelaPaciente, paciente)
        paciente.nome = "Maria Nunes"

        val registosAlterados = tabelaPaciente.update(
            paciente.toContentValues(),
            "_id=?",
            arrayOf(paciente.id.toString())
        )

        assertEquals(1, registosAlterados)
        db.close()
    }

    @Test
    fun consegueEliminarPaciente(){
        val db = GetbdAppOpenHelper().writableDatabase
        val tabelaPaciente = TabelaPaciente(db)

        val paciente = Paciente(nome = "Pedro Marques", DataNascimento = "10/08/1989", sexo = "M", infecao = 1)
        paciente.id = inserePaciente(tabelaPaciente, paciente)

        val registosEliminados = tabelaPaciente.delete(
            "${BaseColumns._ID}=?",
            arrayOf(paciente.id.toString())
        )
        db.close()
    }

    @Test
    fun consegueLerPacientes() {
        val db = GetbdAppOpenHelper().writableDatabase
        val tabelaPaciente = TabelaPaciente(db)

        val paciente = Paciente(nome = "Fernando Mendes", DataNascimento = "18/04/1979", sexo = "M", infecao = 0)
        paciente.id = inserePaciente(tabelaPaciente, paciente)

        val cursor = tabelaPaciente.query(
            TabelaPaciente.TODAS_COLUNAS,
            "${BaseColumns._ID}=?",
            arrayOf(paciente.id.toString()),
            null,
            null,
            null

        )
        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        val pacienteBd = Paciente.fromCursor(cursor)
        assertEquals(paciente, pacienteBd)

        //cursor permite navegar pelos registos
        //exemplo
        //->
        // 1 Drama
        // 2 Ficção
        // 3 Aventura

        db.close()
    }

    @Test
    fun consegueInserirVacinas() {
        //linhas para ler a base de dados
        val db = GetbdAppOpenHelper().writableDatabase
        val tabelaVacina = TabelaVacina(db)

        val vacina = Vacina(origem = "Pfizer", quantidade = 100, validade = "22/01/2022")
        vacina.id = insereVacina(tabelaVacina, vacina)
        db.close()
    }

    @Test
    fun consegueAlterarVacinas() {
        val db = GetbdAppOpenHelper().writableDatabase
        val tabelaVacina = TabelaVacina(db)

        val vacina = Vacina(origem = "Pfizer", quantidade = 100, validade = "22/01/2022")
        vacina.id = insereVacina(tabelaVacina, vacina)
        vacina.quantidade = 99

        val registosAlterados = tabelaVacina.update(
            vacina.toContentValues(),
            "_id=?",
            arrayOf(vacina.id.toString())
        )

        assertEquals(1, registosAlterados)
        db.close()
    }

    @Test
    fun consegueEliminarVacina(){
        val db = GetbdAppOpenHelper().writableDatabase
        val tabelaVacina = TabelaVacina(db)

        val vacina = Vacina(origem = "Moderna", quantidade = 200, validade = "15/01/2023")
        vacina.id = insereVacina(tabelaVacina, vacina)

        val registosEliminados = tabelaVacina.delete(
            "${BaseColumns._ID}=?",
            arrayOf(vacina.id.toString())
        )
        db.close()
    }

    @Test
    fun consegueLerVacinas() {
        val db = GetbdAppOpenHelper().writableDatabase
        val tabelaVacina = TabelaVacina(db)

        val vacina = Vacina(origem = "Moderna", quantidade = 200, validade = "15/01/2023")
        vacina.id = insereVacina(tabelaVacina, vacina)

        val cursor = tabelaVacina.query(
            TabelaVacina.TODAS_COLUNAS,
            "${BaseColumns._ID}=?",
            arrayOf(vacina.id.toString()),
            null,
            null,
            null

        )
        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        val vacinaBd = Vacina.fromCursor(cursor)
        assertEquals(vacina, vacinaBd)

        //cursor permite navegar pelos registos
        //exemplo
        //->
        // 1 Drama
        // 2 Ficção
        // 3 Aventura

        db.close()
    }
}