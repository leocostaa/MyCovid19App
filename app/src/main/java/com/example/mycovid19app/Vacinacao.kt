package com.example.mycovid19app

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Vacinacao(var id: Long =-1, var data_vac: String, var idVacina: Long,var idPaciente: Long) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaVacinacao.CAMPO_DATAVAC,data_vac)
            put(TabelaVacinacao.CAMPO_ID_VACINA,idVacina )
            put(TabelaVacinacao.CAMPO_ID_PACIENTE,idPaciente)
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
            val colunaIdVacina = cursor.getColumnIndex(TabelaVacinacao.CAMPO_ID_VACINA)
            val colunaIdPaciente = cursor.getColumnIndex(TabelaVacinacao.CAMPO_ID_PACIENTE)

            val id = cursor.getLong(colunaId)
            val dataVac = cursor.getString(colunaDataVac)
            val idVacina = cursor.getLong(colunaIdVacina)
            val idPaciente = cursor.getLong((colunaIdPaciente))

            return Vacinacao(id, dataVac, idVacina, idPaciente)
        }
    }


}