package com.example.mycovid19app

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

class ContentProviderApp: ContentProvider() {
    private var bdAppOpenHelper : BdAppOpenHelper? = null

    override fun onCreate(): Boolean {
        bdAppOpenHelper = BdAppOpenHelper(context)

        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val bd = bdAppOpenHelper!!.readableDatabase

        return when (getUriMatcher().match(uri)) {
            URI_PACIENTE -> TabelaPaciente(bd).query(
                projection as Array<String>,
                selection,
                selectionArgs as Array<String>?,
                null,
                null,
                sortOrder
            )

            URI_PACIENTE_ESPECIFICO -> TabelaPaciente(bd).query(
                projection as Array<String>,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!),
                null,
                null,
                null
            )

            URI_VACINA -> TabelaVacina(bd).query(
                projection as Array<String>,
                selection,
                selectionArgs as Array<String>?,
                null,
                null,
                sortOrder
            )

            URI_VACINA_ESPECIFICA -> TabelaVacina(bd).query(
                projection as Array<String>,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!),
                null,
                null,
                null
            )

            URI_VACINACAO -> TabelaVacinacao(bd).query(
                projection as Array<String>,
                selection,
                selectionArgs as Array<String>?,
                null,
                null,
                sortOrder
            )

            URI_VACINACAO_ESPECIFICA -> TabelaVacinacao(bd).query(
                projection as Array<String>,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!),
                null,
                null,
                null
            )
            URI_LOCAL -> TabelaLocal(bd).query(
                projection as Array<String>,
                selection,
                selectionArgs as Array<String>?,
                null,
                null,
                sortOrder
            )

            URI_LOCAL_ESPECIFICA -> TabelaLocal(bd).query(
                projection as Array<String>,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!),
                null,
                null,
                null
            )
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return when (getUriMatcher().match(uri)) {
            URI_PACIENTE -> "$MULTIPLOS_ITEMS/$PACIENTE"
            URI_PACIENTE_ESPECIFICO -> "$UNICO_ITEM/$PACIENTE"
            URI_VACINA -> "$MULTIPLOS_ITEMS/$VACINA"
            URI_VACINA_ESPECIFICA -> "$UNICO_ITEM/$VACINA"
            URI_VACINACAO -> "$MULTIPLOS_ITEMS/$VACINACAO"
            URI_VACINACAO_ESPECIFICA -> "$UNICO_ITEM/$VACINACAO"
            URI_LOCAL -> "$MULTIPLOS_ITEMS/$LOCAL"
            URI_LOCAL_ESPECIFICA -> "$UNICO_ITEM/$LOCAL"
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val bd = bdAppOpenHelper!!.writableDatabase

        val id = when (getUriMatcher().match(uri)) {
            URI_PACIENTE -> TabelaPaciente(bd).insert(values!!)
            URI_VACINA -> TabelaVacina(bd).insert(values!!)
            URI_VACINACAO -> TabelaVacinacao(bd).insert(values!!)
            URI_LOCAL -> TabelaLocal(bd).insert(values!!)
            else -> -1L
        }

        if (id == -1L) return null

        return Uri.withAppendedPath(uri, id.toString())
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val bd = bdAppOpenHelper!!.writableDatabase

        return when (getUriMatcher().match(uri)) {
            URI_PACIENTE_ESPECIFICO -> TabelaPaciente(bd).delete(
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )

            URI_VACINA_ESPECIFICA -> TabelaVacina(bd).delete(
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )

            URI_VACINACAO_ESPECIFICA -> TabelaVacinacao(bd).delete(
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )
            URI_LOCAL_ESPECIFICA -> TabelaLocal(bd).delete(
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )

            else -> 0
        }
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        val bd = bdAppOpenHelper!!.writableDatabase

        return when (getUriMatcher().match(uri)) {
            URI_PACIENTE_ESPECIFICO -> TabelaPaciente(bd).update(
                values!!,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )

            URI_VACINA_ESPECIFICA -> TabelaVacina(bd).update(
                values!!,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )

            URI_VACINACAO_ESPECIFICA -> TabelaVacinacao(bd).update(
                values!!,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )
            URI_LOCAL_ESPECIFICA -> TabelaLocal(bd).update(
                values!!,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )

            else -> 0
        }
    }

    companion object{
        private const val AUTHORITY = "com.example.mycovid19app"

        private const val PACIENTE = "paciente"
        private const val VACINA = "vacina"
        private const val VACINACAO = "vacinacao"
        private const val LOCAL = "local"


        private const val URI_PACIENTE = 100
        private const val URI_PACIENTE_ESPECIFICO = 101
        private const val URI_VACINA = 200
        private const val URI_VACINA_ESPECIFICA = 201
        private const val URI_VACINACAO = 300
        private const val URI_VACINACAO_ESPECIFICA = 301
        private const val URI_LOCAL = 400
        private const val URI_LOCAL_ESPECIFICA = 401

        private val ENDERECO_BASE = Uri.parse("content://$AUTHORITY")
        public val ENDERECO_PACIENTE = Uri.withAppendedPath(ENDERECO_BASE, PACIENTE)
        public val ENDERECO_VACINA = Uri.withAppendedPath(ENDERECO_BASE, VACINA)
        public val ENDERECO_VACINACAO = Uri.withAppendedPath(ENDERECO_BASE, VACINACAO)
        public val ENDERECO_LOCAL = Uri.withAppendedPath(ENDERECO_BASE, LOCAL)




        private const val MULTIPLOS_ITEMS = "vnd.android.cursor.dir"
        private const val UNICO_ITEM = "vnd.android.cursor.item"

        private fun getUriMatcher() : UriMatcher{
            val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

            uriMatcher.addURI(AUTHORITY, PACIENTE, URI_PACIENTE)
            uriMatcher.addURI(AUTHORITY, "$PACIENTE/#", URI_PACIENTE_ESPECIFICO)
            uriMatcher.addURI(AUTHORITY, VACINA, URI_VACINA)
            uriMatcher.addURI(AUTHORITY, "$VACINA/#", URI_VACINA_ESPECIFICA)
            uriMatcher.addURI(AUTHORITY, VACINACAO, URI_VACINACAO)
            uriMatcher.addURI(AUTHORITY, "$VACINACAO/#", URI_VACINACAO_ESPECIFICA)
            uriMatcher.addURI(AUTHORITY, LOCAL, URI_LOCAL)
            uriMatcher.addURI(AUTHORITY, "$LOCAL/#", URI_LOCAL_ESPECIFICA)


            return uriMatcher
        }


    }
}