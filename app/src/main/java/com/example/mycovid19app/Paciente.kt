package com.example.mycovid19app

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Paciente(var id: Long= -1,var nome: String, var DataNascimento: String, var sexo: String, var infecao: Int) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaPaciente.CAMPO_NOME,nome)
            put(TabelaPaciente.CAMPO_DATANASCIMENTO,DataNascimento)
            put(TabelaPaciente.CAMPO_SEXO,sexo)
            put(TabelaPaciente.CAMPO_INFECAO,infecao)

        }

        return valores
    }
    companion object{
        fun fromCursor(cursor: Cursor): Paciente {
            val colunaId = cursor.getColumnIndex(BaseColumns._ID)
            val colunaNome = cursor.getColumnIndex(TabelaPaciente.CAMPO_NOME)
            val colunaDataNascimento = cursor.getColumnIndex(TabelaPaciente.CAMPO_DATANASCIMENTO)
            val colunaSexo = cursor.getColumnIndex(TabelaPaciente.CAMPO_SEXO)
            val colunaInfecao = cursor.getColumnIndex(TabelaPaciente.CAMPO_INFECAO)


            val id = cursor.getLong(colunaId)
            val nome = cursor.getString(colunaNome)
            val DataNascimento = cursor.getString(colunaDataNascimento)
            val sexo = cursor.getString(colunaSexo)
            val infecao = cursor.getInt(colunaInfecao)

            return Paciente(id, nome, DataNascimento, sexo, infecao)
        }
    }





}