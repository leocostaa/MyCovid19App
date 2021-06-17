package com.example.mycovid19app

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.*

data class Paciente(var id: Long= -1, var nome: String, var DataNascimento: Date, var sexo: String) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaPaciente.CAMPO_NOME,nome)
            put(TabelaPaciente.CAMPO_DATANASCIMENTO,DataNascimento.time)
            put(TabelaPaciente.CAMPO_SEXO,sexo)


        }

        return valores
    }
    companion object{
        fun fromCursor(cursor: Cursor): Paciente {
            val colunaId = cursor.getColumnIndex(BaseColumns._ID)
            val colunaNome = cursor.getColumnIndex(TabelaPaciente.CAMPO_NOME)
            val colunaDataNascimento = cursor.getColumnIndex(TabelaPaciente.CAMPO_DATANASCIMENTO)
            val colunaSexo = cursor.getColumnIndex(TabelaPaciente.CAMPO_SEXO)



            val id = cursor.getLong(colunaId)
            val nome = cursor.getString(colunaNome)
            val DataNascimento = Date (cursor.getLong(colunaDataNascimento))
            val sexo = cursor.getString(colunaSexo)


            return Paciente(id, nome, DataNascimento, sexo)
        }
    }





}