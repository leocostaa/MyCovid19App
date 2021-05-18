package com.example.mycovid19app

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BdAppOpenHelper (context : Context?)  :
    SQLiteOpenHelper(context, NOME_BASE_DADOS, null, VERSAO_BASE_DADOS) {

    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            TabelaPaciente(db).cria()

        }
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
    companion object{
        const val NOME_BASE_DADOS = "CovidApp.db"
        const val VERSAO_BASE_DADOS = 1
    }
}