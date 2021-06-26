package com.example.mycovid19app

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.*

data class Vacinacao(var id: Long =-1, var data_vac: Date, var idVacina: Long, var idPaciente: Long, var idLocal: Long, var nomePaciente: String? = null) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaVacinacao.CAMPO_DATAVAC,data_vac.time)
            put(TabelaVacinacao.CAMPO_ID_VACINA,idVacina )
            put(TabelaVacinacao.CAMPO_ID_PACIENTE,idPaciente)
            put(TabelaVacinacao.CAMPO_ID_LOCAL,idLocal)
            put(TabelaVacinacao.CAMPO_EXTERNO_NOME_PACIENTE,nomePaciente)

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
            val colunaIdLocal = cursor.getColumnIndex(TabelaVacinacao.CAMPO_ID_LOCAL)
            val colNomePaciente = cursor.getColumnIndex(TabelaVacinacao.CAMPO_EXTERNO_NOME_PACIENTE)

            val id = cursor.getLong(colunaId)
            val dataVac = Date(cursor.getLong(colunaDataVac))
            val idVacina = cursor.getLong(colunaIdVacina)
            val idPaciente = cursor.getLong(colunaIdPaciente)
            val idLocal = cursor.getLong(colunaIdLocal)
            val nomePaciente = if (colNomePaciente != -1) cursor.getString(colNomePaciente) else null

            return Vacinacao(id, dataVac, idVacina, idPaciente, idLocal, nomePaciente )
        }
    }


}