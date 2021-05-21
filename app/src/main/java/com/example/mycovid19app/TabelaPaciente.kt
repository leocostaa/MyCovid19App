package com.example.mycovid19app

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaPaciente (db: SQLiteDatabase) {
    private val db : SQLiteDatabase = db

    fun cria() {
        db.execSQL("CREATE TABLE  $NOME_TABELA (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_NOME TEXT NOT NULL, $CAMPO_DATANASCIMENTO INTEGER NOT NULL, $CAMPO_SEXO TEXT NOT NULL, $CAMPO_INFECAO TEXT NOT NULL)")

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
        selection: String,
        selectionArgs: Array<String>,
        groupBy: String,
        having: String,
        orderBy: String
    ): Cursor? {
        return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

    companion object{
        const val NOME_TABELA = "Paciente"
        const val CAMPO_NOME = "nome"
        const val CAMPO_DATANASCIMENTO = "DataNascimento"
        const val CAMPO_SEXO = "sexo"
        const val CAMPO_INFECAO = "infecao"
    }
}