package com.example.mycovid19app

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaVacinacao (db : SQLiteDatabase){
    private val db : SQLiteDatabase = db

    fun cria() {
        db.execSQL("CREATE TABLE  $NOME_TABELA (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_DATAVAC INTEGER NOT NULL,$CAMPO_ID_VACINA INTEGER NOT NULL,$CAMPO_ID_PACIENTE INTEGER NOT NULL ,$CAMPO_ID_LOCAL INTEGER NOT NULL, FOREIGN KEY ($CAMPO_ID_VACINA) REFERENCES ${TabelaVacina.NOME_TABELA}, FOREIGN KEY ($CAMPO_ID_PACIENTE) REFERENCES ${TabelaPaciente.NOME_TABELA},FOREIGN KEY ($CAMPO_ID_LOCAL) REFERENCES ${TabelaLocal.NOME_TABELA})")

    }

    fun insert(values: ContentValues): Long {
        return db.insert(NOME_TABELA, null, values)
    }

    fun update(values: ContentValues, whereClause: String, whereArgs: Array<String>): Int {
        return db.update(NOME_TABELA, values, whereClause, whereArgs )
    }

    fun delete(whereClause: String, whereArgs: Array<String>): Int {
        return db.delete(NOME_TABELA, whereClause, whereArgs)
    }

    fun query(
        columns: Array<String>,
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor? {
        val ultimaColuna = columns.size - 1

        var posColNomePaciente = -1
        var posColLocal = -1
        var posColOrigem = -1
        // -1 indica que a coluna não foi pedida
        for (i in 0..ultimaColuna) {
            if (columns[i] == CAMPO_EXTERNO_NOME_PACIENTE) {
                posColNomePaciente = i
            }else if(columns[i] == CAMPO_EXTERNO_NOME_LOCAL){
                posColLocal = i
            }else if(columns[i] == CAMPO_EXTERNO_ORIGEM){
                posColOrigem = i
            }
        }

        if (posColNomePaciente == -1 && posColLocal == -1 && posColOrigem == -1) {
            return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy)
        }

        var colunas = ""
        for (i in 0..ultimaColuna) {
            if (i > 0) colunas += ","

            colunas += if (i == posColNomePaciente) {
                "${TabelaPaciente.NOME_TABELA}.${TabelaPaciente.CAMPO_NOME} AS $CAMPO_EXTERNO_NOME_PACIENTE"
            }else if (i == posColLocal) {
                "${TabelaLocal.NOME_TABELA}.${TabelaLocal.CAMPO_LOCALADM} AS $CAMPO_EXTERNO_NOME_LOCAL"
            }else if (i == posColOrigem) {
                "${TabelaVacina.NOME_TABELA}.${TabelaVacina.CAMPO_ORIGEM} AS $CAMPO_EXTERNO_ORIGEM"
            }else{
                "${NOME_TABELA}.${columns[i]}"
            }
        }

        val tabelas = "$NOME_TABELA INNER JOIN ${TabelaPaciente.NOME_TABELA} ON ${TabelaPaciente.NOME_TABELA}.${BaseColumns._ID}=$CAMPO_ID_PACIENTE " +
                                   "INNER JOIN ${TabelaLocal.NOME_TABELA} ON ${TabelaLocal.NOME_TABELA}.${BaseColumns._ID}=$CAMPO_ID_LOCAL " +
                                   "INNER JOIN ${TabelaVacina.NOME_TABELA} ON ${TabelaVacina.NOME_TABELA}.${BaseColumns._ID}=$CAMPO_ID_VACINA"

        var sql = "SELECT $colunas FROM $tabelas"

        if (selection != null) sql += " WHERE $selection"

        if (groupBy != null) {
            sql += " GROUP BY $groupBy"
            if (having != null) " HAVING $having"
        }

        if (orderBy != null) sql += " ORDER BY $orderBy"

        return db.rawQuery(sql, selectionArgs)







    }

    companion object{
        const val NOME_TABELA = "Vacinacao"
        const val CAMPO_DATAVAC = "DataVacinacao"
        const val CAMPO_ID_VACINA = "id_vacina"
        const val CAMPO_ID_PACIENTE = "id_paciente"
        const val CAMPO_ID_LOCAL = "id_local"
        const val CAMPO_EXTERNO_NOME_PACIENTE = "nome_paciente"
        const val CAMPO_EXTERNO_NOME_LOCAL = "Localadm"
        const val CAMPO_EXTERNO_ORIGEM = "LabdeOrigem"



        val TODAS_COLUNAS = arrayOf(BaseColumns._ID, CAMPO_DATAVAC, CAMPO_ID_VACINA, CAMPO_ID_PACIENTE, CAMPO_ID_LOCAL,
            CAMPO_EXTERNO_NOME_PACIENTE, CAMPO_EXTERNO_NOME_LOCAL, CAMPO_EXTERNO_ORIGEM)
    }
}