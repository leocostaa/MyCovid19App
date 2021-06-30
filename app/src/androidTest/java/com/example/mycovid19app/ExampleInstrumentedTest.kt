package com.example.mycovid19app

import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private fun getAppContext() = InstrumentationRegistry.getInstrumentation().targetContext
    private fun GetbdAppOpenHelper() = BdAppOpenHelper(getAppContext())
    private fun Data(ano :Int, mes:Int, dia:Int) = Date (ano-1900, mes-1,dia)

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
    private fun insereVacinacao(tabela: TabelaVacinacao, vacinacao: Vacinacao): Long {
        val id = tabela.insert(vacinacao.toContentValues())
        assertNotEquals(-1, id)
        return id

    }
    private fun insereLocal(tabela: TabelaLocal, local: Local): Long {
        val id = tabela.insert(local.toContentValues())
        assertNotEquals(-1, id)
        return id

    }
    private fun getPacienteBaseDados(tabela: TabelaPaciente, id: Long): Paciente {
        val cursor = tabela.query(
            TabelaPaciente.TODAS_COLUNAS,
            "${TabelaPaciente.NOME_TABELA}.${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null, null, null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Paciente.fromCursor(cursor)
    }
    private fun getVacinaBaseDados(tabela: TabelaVacina, id: Long): Vacina {
        val cursor = tabela.query(
            TabelaVacina.TODAS_COLUNAS,
            "${TabelaVacina.NOME_TABELA}.${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null, null, null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Vacina.fromCursor(cursor)
    }
    private fun getVacinacaoBaseDados(tabela: TabelaVacinacao, id: Long): Vacinacao {
        val cursor = tabela.query(
            TabelaVacinacao.TODAS_COLUNAS,
            "${TabelaVacinacao.NOME_TABELA}.${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null, null, null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Vacinacao.fromCursor(cursor)
    }
    private fun getLocalBaseDados(tabela: TabelaLocal, id: Long): Local {
        val cursor = tabela.query(
            TabelaLocal.TODAS_COLUNAS,
            "${TabelaLocal.NOME_TABELA}.${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null, null, null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Local.fromCursor(cursor)
    }

    @Before
    fun apagarBaseDados(){
      //getAppContext().deleteDatabase((BdAppOpenHelper.NOME_BASE_DADOS))

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


        val paciente = Paciente(nome = "Joana Sousa", DataNascimento = Data(1990,8,12), sexo = "Feminino")
        paciente.id = inserePaciente(tabelaPaciente, paciente)
        db.close()
    }

    @Test
    fun consegueAlterarPacientes() {
        val db = GetbdAppOpenHelper().writableDatabase
        val tabelaPaciente = TabelaPaciente(db)

        val paciente = Paciente(nome = "Mariana Nunes", DataNascimento = Data(1990,8,12), sexo = "Feminino")
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

        val paciente = Paciente(nome = "Pedro Marques", DataNascimento = Data(1990,8,12), sexo = "Masculino")
        paciente.id = inserePaciente(tabelaPaciente, paciente)

        val registosEliminados = tabelaPaciente.delete(
            "${BaseColumns._ID}=?",
            arrayOf(paciente.id.toString())
        )
        assertEquals(1,registosEliminados)
        db.close()
    }

    @Test
    fun consegueLerPacientes() {
        val db = GetbdAppOpenHelper().writableDatabase
        val tabelaPaciente = TabelaPaciente(db)

        val paciente = Paciente(nome = "Fernando Mendes", DataNascimento = Data(1990,8,12), sexo = "Masculino")
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

        val vacina = Vacina(origem = "Pfizer", quantidade = 100, validade = Data(2023,8,13))
        vacina.id = insereVacina(tabelaVacina, vacina)
        db.close()
    }

    @Test
    fun consegueAlterarVacinas() {
        val db = GetbdAppOpenHelper().writableDatabase
        val tabelaVacina = TabelaVacina(db)

        val vacina = Vacina(origem = "?", quantidade = 100, validade = Data(2030,3,3))
        vacina.id = insereVacina(tabelaVacina, vacina)
        vacina.origem = "Johnson"
        vacina.quantidade = 99
        vacina.validade = Data(2022,3,19)

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

        val vacina = Vacina(origem = "AstraZenca", quantidade = 200, validade = Data(2024,1,6))
        vacina.id = insereVacina(tabelaVacina, vacina)

        val registosEliminados = tabelaVacina.delete(
            "${BaseColumns._ID}=?",
            arrayOf(vacina.id.toString())
        )
        assertEquals(1,registosEliminados)
        db.close()
    }

    @Test
    fun consegueLerVacinas() {
        val db = GetbdAppOpenHelper().writableDatabase
        val tabelaVacina = TabelaVacina(db)

        val vacina = Vacina(origem = "Moderna", quantidade = 200, validade = Data(2023,6,23))
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


        db.close()
    }

    @Test
    fun consegueInserirVacinacao() {

        val db = GetbdAppOpenHelper().writableDatabase

        val tabelaVacina = TabelaVacina(db)
        val vacina = Vacina(origem = "Moderna", quantidade = 200, validade = Data(2023,3,23))
        vacina.id= insereVacina(tabelaVacina,vacina)

        val tabelaPaciente = TabelaPaciente(db)
        val paciente = Paciente(nome = "Ester Maria", DataNascimento = Data(1979,4,18), sexo = "Masculino")
        paciente.id = inserePaciente(tabelaPaciente,paciente)

        val tabelaLocal = TabelaLocal(db)
        val local = Local(cidade = "Guimarães",localadm = "Hospital da Senhora da Oliveira", sala = "ala covid 5")
        local.id = insereLocal(tabelaLocal,local)

        val tabelaVacinacao = TabelaVacinacao(db)
        val vacinacao = Vacinacao(data_vac = Data(2021,9,23),idVacina = vacina.id ,idPaciente = paciente.id,idLocal = local.id, nomePaciente = paciente.nome, localadm = local.localadm, origem = vacina.origem)
        vacinacao.id = insereVacinacao(tabelaVacinacao, vacinacao)

        assertEquals(vacinacao, getVacinacaoBaseDados(tabelaVacinacao, vacinacao.id))

        db.close()
    }
    fun consegueAlterarVacinacao() {
        val db = GetbdAppOpenHelper().writableDatabase

        val tabelaVacina = TabelaVacina(db)
        val vacina = Vacina(origem = "Pfizer", quantidade = 200, validade = Data(2023,2,8))
        vacina.id= insereVacina(tabelaVacina,vacina)

        val tabelaPaciente = TabelaPaciente(db)
        val paciente = Paciente(nome = "Fernando Mendes", DataNascimento = Data(1960,8,12), sexo = "Masculino")
        paciente.id = inserePaciente(tabelaPaciente,paciente)

        val tabelaLocal = TabelaLocal(db)
        val local = Local(cidade = "Famalicão",localadm = "Unidade Hospitalar de Famalicão ", sala = "ala covid 2")
        local.id = insereLocal(tabelaLocal,local)

        val tabelaVacinacao = TabelaVacinacao(db)
        val vacinacao = Vacinacao(data_vac = Data(2021,10,23),idVacina = vacina.id, idPaciente = paciente.id, idLocal = local.id, nomePaciente = paciente.nome, localadm = local.localadm, origem = vacina.origem)
        vacinacao.id = insereVacinacao(tabelaVacinacao, vacinacao)
        vacinacao.data_vac= Data(2021,10,24)
        vacinacao.nomePaciente = "MIMIMI"


        val registosAlterados = tabelaVacinacao.update(
            vacinacao.toContentValues(),
            "_id=?",
            arrayOf(vacinacao.id.toString())
        )

        assertEquals(1, registosAlterados)

        assertEquals(vacinacao, getVacinacaoBaseDados(tabelaVacinacao, vacinacao.id))
        db.close()
    }

    @Test
    fun consegueEliminarVacinacao(){
        val db = GetbdAppOpenHelper().writableDatabase

        val tabelaVacina = TabelaVacina(db)
        val vacina = Vacina(origem = "AstraZenca", quantidade = 200, validade = Data(2023,4,13))
        vacina.id= insereVacina(tabelaVacina,vacina)

        val tabelaPaciente = TabelaPaciente(db)
        val paciente = Paciente(nome = "Afonso Paiva", DataNascimento = Data(1965,4,12), sexo = "Masculino")
        paciente.id = inserePaciente(tabelaPaciente,paciente)

        val tabelaLocal = TabelaLocal(db)
        val local = Local(cidade = "Guimarães",localadm = "Hospital da Senhora da Oliveira", sala = "ala covid 5")
        local.id = insereLocal(tabelaLocal,local)


        val tabelaVacinacao = TabelaVacinacao(db)
        val vacinacao = Vacinacao(data_vac = Data(2021,9,25),idVacina = vacina.id,idPaciente = paciente.id, idLocal = local.id, nomePaciente = paciente.nome, localadm = local.localadm, origem = vacina.origem)
        vacinacao.id = insereVacinacao(tabelaVacinacao, vacinacao)

        val registosEliminados = tabelaVacinacao.delete(
            "${BaseColumns._ID}=?",
            arrayOf(vacinacao.id.toString())
        )
        assertEquals(1,registosEliminados)

        db.close()

    }

    @Test
    fun consegueLerVacinacao(){
        val db = GetbdAppOpenHelper().writableDatabase

        val tabelaVacina = TabelaVacina(db)
        val vacina = Vacina(origem = "Johnson", quantidade = 100, validade = Data(2023,5,13))
        vacina.id= insereVacina(tabelaVacina,vacina)

        val tabelaPaciente = TabelaPaciente(db)
        val paciente = Paciente(nome = "Benedita Castro", DataNascimento = Data(1969,8,16), sexo = "Masculino")
        paciente.id = inserePaciente(tabelaPaciente,paciente)

        val tabelaLocal = TabelaLocal(db)
        val local = Local(cidade = "Felgueiras",localadm = "Hospital da Senhora da Oliveira", sala = "ala covid 4")
        local.id = insereLocal(tabelaLocal,local)


        val tabelaVacinacao = TabelaVacinacao(db)
        val vacinacao = Vacinacao(data_vac = Data(2021,9,26),idVacina = vacina.id,idPaciente = paciente.id, idLocal = local.id, nomePaciente = paciente.nome, localadm = local.localadm, origem = vacina.origem)
        vacinacao.id = insereVacinacao(tabelaVacinacao, vacinacao)

        val cursor = tabelaVacinacao.query(
            TabelaVacinacao.TODAS_COLUNAS,
            "${BaseColumns._ID}=?",
            arrayOf(vacinacao.id.toString()),
            null,
            null,
            null

        )
        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        val vacinacaoBd = Vacinacao.fromCursor(cursor)
        assertEquals(vacinacao, vacinacaoBd)

        assertEquals(vacinacao, getVacinacaoBaseDados(tabelaVacinacao, vacinacao.id))

        db.close()
    }

    @Test
    fun consegueInserirLocal() {
        //linhas para ler a base de dados
        val db = GetbdAppOpenHelper().writableDatabase
        val tabelaLocal = TabelaLocal(db)

        val local = Local(cidade = "Porto",localadm = "Hospital da Luz 4",sala = "3")
        local.id = insereLocal(tabelaLocal, local)
        db.close()
    }

    @Test
    fun consegueAlterarLocal() {
        val db = GetbdAppOpenHelper().writableDatabase
        val tabelaLocal = TabelaLocal(db)

        val local = Local(cidade = "Porto",localadm = "Hospital da Luz 3",sala = "3")
        local.id = insereLocal(tabelaLocal, local)
        local.sala = "4"

        val registosAlterados = tabelaLocal.update(
            local.toContentValues(),
            "_id=?",
            arrayOf(local.id.toString())
        )

        assertEquals(1, registosAlterados)
        db.close()
    }

    @Test
    fun consegueEliminarLocal(){
        val db = GetbdAppOpenHelper().writableDatabase
        val tabelaLocal = TabelaLocal(db)

        val local = Local(cidade = "Porto",localadm = "Hospital da Luz",sala = "3")
        local.id = insereLocal(tabelaLocal, local)

        val registosEliminados = tabelaLocal.delete(
            "${BaseColumns._ID}=?",
            arrayOf(local.id.toString())
        )
        assertEquals(1,registosEliminados)
        db.close()
    }

    @Test
    fun consegueLerLocal() {
        val db = GetbdAppOpenHelper().writableDatabase
        val tabelaLocal = TabelaLocal(db)

        val local = Local(cidade = "Porto",localadm = "Hospital da Luz 2",sala = "3")
        local.id = insereLocal(tabelaLocal, local)

        val cursor = tabelaLocal.query(
            TabelaLocal.TODAS_COLUNAS,
            "${BaseColumns._ID}=?",
            arrayOf(local.id.toString()),
            null,
            null,
            null

        )
        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        val localBd = Local.fromCursor(cursor)
        assertEquals(local, localBd)

        //cursor permite navegar pelos registos
        //exemplo
        //->
        // 1 Drama
        // 2 Ficção
        // 3 Aventura

        db.close()
    }
}