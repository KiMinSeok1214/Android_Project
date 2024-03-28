package com.example.project_last

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SearchDB(context: Context):

    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "SearchDB.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "Search_Table"
        const val COL1_KEYWORD = "keyword"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE $TABLE_NAME (" +
                "$COL1_KEYWORD TEXT " +
                ")"
        db?.execSQL(query)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion) {
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(db)
        }
    }

    // insert 할수 있는지는 activity에서 check
    fun insertKeyword(key: String) {
        val db = this.writableDatabase

        val cv = ContentValues().apply {
            put(COL1_KEYWORD, key)

        }
        db.insert(TABLE_NAME, null, cv)
    }

    fun getKeyword(cursor: Cursor) : String {
        val keyword = cursor.getString(0)
        return keyword
    }
    // 모든 list를 가져오는 함수
    fun getAllData(): ArrayList<String> {
        val keywordList: ArrayList<String> = ArrayList()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        cursor?.run {
            while (cursor.moveToNext()) {
                val keyword = getKeyword(cursor)
                keywordList.add(keyword)
            }
        }
        cursor?.close()
        return keywordList
    }
    fun deleteItem(keyword: String){
        val db = this.writableDatabase

        db.delete(TABLE_NAME, "$COL1_KEYWORD = ?", arrayOf(keyword))
    }
}