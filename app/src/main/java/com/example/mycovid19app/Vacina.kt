package com.example.mycovid19app

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.*

data class Vacina(var id: Long= -1,var origem: String, var quantidade: Int, var validade: Date) {

    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaVacina.CAMPO_ORIGEM,origem)
            put(TabelaVacina.CAMPO_QUANTIDADE,quantidade)
            put(TabelaVacina.CAMPO_VALIDADE,validade.time)

        }
        return valores
    }

    companion object{
        fun fromCursor(cursor: Cursor): Vacina {
            val colunaId = cursor.getColumnIndex(BaseColumns._ID)
            val colunaOrigem = cursor.getColumnIndex(TabelaVacina.CAMPO_ORIGEM)
            val colunaQuantidade = cursor.getColumnIndex(TabelaVacina.CAMPO_QUANTIDADE)
            val colunaValidade = cursor.getColumnIndex(TabelaVacina.CAMPO_VALIDADE)

            val id = cursor.getLong(colunaId)
            val origem = cursor.getString(colunaOrigem)
            val quantidade = cursor.getInt(colunaQuantidade)
            val validade = Date(cursor.getLong(colunaValidade))


            return Vacina(id, origem, quantidade, validade)
        }
    }
}