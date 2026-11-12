package com.github.gleberphant.appagendamentos.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.github.gleberphant.appagendamentos.models.Agendamento
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AgendamentoDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "agendamentos.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_NAME = "agendamentos"
        private const val COLUMN_ID = "id"
        private const val COLUMN_DATA = "data"
        private const val COLUMN_DESCRICAO = "descricao"
        private const val COLUMN_STATUS = "status"
    }

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreate(db: SQLiteDatabase) {
        val sql = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_DATA TEXT NOT NULL,
                $COLUMN_DESCRICAO TEXT NOT NULL,
                $COLUMN_STATUS INTEGER NOT NULL DEFAULT 0
            )
        """.trimIndent()
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun inserir(agendamento: Agendamento): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_DATA, dateFormat.format(agendamento.data))
            put(COLUMN_DESCRICAO, agendamento.descricao)
            put(COLUMN_STATUS, if (agendamento.status) 1 else 0)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    fun listar(): List<Agendamento> {
        val lista = mutableListOf<Agendamento>()
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, "$COLUMN_ID ASC")

        cursor.use {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndexOrThrow(COLUMN_ID))
                val dataStr = it.getString(it.getColumnIndexOrThrow(COLUMN_DATA))
                val descricao = it.getString(it.getColumnIndexOrThrow(COLUMN_DESCRICAO))
                val statusInt = it.getInt(it.getColumnIndexOrThrow(COLUMN_STATUS))

                val data = try { dateFormat.parse(dataStr) } catch (e: Exception) { Date() }

                lista.add(
                    Agendamento(
                        id = id,
                        data = data ?: Date(),
                        descricao = descricao,
                        status = statusInt == 1
                    )
                )
            }
        }
        return lista
    }

    fun buscarPorId(id: Int): Agendamento? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            null,
            "$COLUMN_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        cursor.use {
            if (it.moveToFirst()) {
                val idVal = it.getInt(it.getColumnIndexOrThrow(COLUMN_ID))
                val dataStr = it.getString(it.getColumnIndexOrThrow(COLUMN_DATA))
                val descricao = it.getString(it.getColumnIndexOrThrow(COLUMN_DESCRICAO))
                val statusInt = it.getInt(it.getColumnIndexOrThrow(COLUMN_STATUS))

                val data = try { dateFormat.parse(dataStr) } catch (e: Exception) { Date() }

                return Agendamento(
                    id = idVal,
                    data = data ?: Date(),
                    descricao = descricao,
                    status = statusInt == 1
                )
            }
        }
        return null
    }

    fun atualizar(agendamento: Agendamento) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_DATA, dateFormat.format(agendamento.data))
            put(COLUMN_DESCRICAO, agendamento.descricao)
            put(COLUMN_STATUS, if (agendamento.status) 1 else 0)
        }
        db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(agendamento.id.toString()))
    }

    fun deletar(id: Int) {
        val db = writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }
}
