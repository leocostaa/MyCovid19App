package com.example.mycovid19app

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Local (var id: Long= -1,var cidade: String, var localadm: String, var sala: String) {

    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaLocal.CAMPO_CIDADE,cidade)
            put(TabelaLocal.CAMPO_LOCALADM,localadm)
            put(TabelaLocal.CAMPO_SALA,sala)


        }

        return valores
    }
    companion object{
        fun fromCursor(cursor: Cursor): Local {
            val colunaId = cursor.getColumnIndex(BaseColumns._ID)
            val colunaCidade = cursor.getColumnIndex(TabelaLocal.CAMPO_CIDADE)
            val colunaLocaladm = cursor.getColumnIndex(TabelaLocal.CAMPO_LOCALADM)
            val colunaSala = cursor.getColumnIndex(TabelaLocal.CAMPO_SALA)



            val id = cursor.getLong(colunaId)
            val Cidade = cursor.getString(colunaCidade)
            val LocalAdm = cursor.getString(colunaLocaladm)
            val Sala = cursor.getString(colunaSala)


            return Local(id, Cidade, LocalAdm, Sala)
        }
    }
}