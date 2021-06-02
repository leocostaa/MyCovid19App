package com.example.mycovid19app

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Vacinacao(var id: Long =-1, var data_vac: String, var local_vac: String, var idVacina: Long) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaVacinacao.CAMPO_DATAVAC,data_vac)
            put(TabelaVacinacao.CAMPO_LOCAL,local_vac)
            put(TabelaVacinacao.CAMPO_ID_VACINA,idVacina)
        }

        // valores.put(TabelaLivros.CAMPO_AUTOR,autor)
        //valores.put(TabelaLivros.CAMPO_TITULO,titulo)
        //se tiver mais campos fazer o mesmo para mais campos

        return valores
    }
    companion object{
        fun fromCursor(cursor: Cursor): Vacinacao {
            val colunaId = cursor.getColumnIndex(BaseColumns._ID)
            val colunaDataVac = cursor.getColumnIndex(TabelaVacinacao.CAMPO_DATAVAC)
            val colunaLocal = cursor.getColumnIndex(TabelaVacinacao.CAMPO_LOCAL)
            val colunaIdVacina = cursor.getColumnIndex(TabelaVacinacao.CAMPO_ID_VACINA)

            val id = cursor.getLong(colunaId)
            val dataVac = cursor.getString(colunaDataVac)
            val local = cursor.getString(colunaLocal)
            val idVacina = cursor.getLong(colunaIdVacina)

            return Vacinacao(id, dataVac, local, idVacina)
        }
    }


}