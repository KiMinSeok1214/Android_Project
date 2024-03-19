package com.example.project_last

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import androidx.core.content.contentValuesOf

class ItemDB(context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "ItemDB.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "Item_Table"
        const val COL1_ID = "id"
        const val COL2_REST_NAME = "rest_name"
        const val COL3_ISVISIT = "isvisit"
        const val COL4_ISDELIVERY = "isdelivery"
        const val COL5_DATE = "date"
        const val COL6_REST_STAR = "rest_star"
        const val COL7_REST_COMMENT = "rest_comment"
        const val COL8_MAIN_IMAGE_URI = "main_image_uri"
        const val COL9_ADDRESS = "address"
        const val COL10_URL = "url"
        const val COL11_ISFAVOR = "isfavor"
        const val COL12_MENU_NAME = "menu_name"
        const val COL13_MENU_STAR = "menu_star"
        const val COL14_MENU_IMAGE_URI = "menu_image_uri"
        const val COL15_MENU_COMMENT = "menu_comment"
        const val COL16_PRICE = "price"
        const val COL17_HASHTAG = "hashtag"
        const val COL18_CATEGORY1 = "category1"
        const val COL19_CATEGORY2 = "category2"
        const val COL20_CATEGORY3 = "category3"
        const val COL21_CATEGORY4 = "category4"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE $TABLE_NAME (" +
                "$COL1_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL2_REST_NAME TEXT, " +
                "$COL3_ISVISIT INTEGER, " +
                "$COL4_ISDELIVERY INTEGER, " +
                "$COL5_DATE TEXT, " +
                "$COL6_REST_STAR REAL, " +
                "$COL7_REST_COMMENT TEXT, " +
                "$COL8_MAIN_IMAGE_URI TEXT, " +
                "$COL9_ADDRESS TEXT, " +
                "$COL10_URL TEXT, " +
                "$COL11_ISFAVOR INTEGER, " +
                "$COL12_MENU_NAME TEXT, " +
                "$COL13_MENU_STAR REAL, " +
                "$COL14_MENU_IMAGE_URI TEXT, " +
                "$COL15_MENU_COMMENT TEXT, " +
                "$COL16_PRICE INTEGER, " +
                "$COL17_HASHTAG TEXT, " +
                "$COL18_CATEGORY1 TEXT, " +
                "$COL19_CATEGORY2 TEXT, " +
                "$COL20_CATEGORY3 TEXT, " +
                "$COL21_CATEGORY4 TEXT" +
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
    fun insertItem(item: Item) {
        val db = this.writableDatabase

        val cv = ContentValues().apply {
            put(COL2_REST_NAME, item.rest_name)
            put(COL3_ISVISIT, item.isvisit)
            put(COL4_ISDELIVERY, item.isdelivery)
            put(COL5_DATE, item.date)
            put(COL6_REST_STAR, item.rest_star)
            put(COL7_REST_COMMENT, item.rest_comment)
            put(COL8_MAIN_IMAGE_URI, item.main_image_uri)
            put(COL9_ADDRESS, item.address)
            put(COL10_URL, item.url)
            put(COL11_ISFAVOR, item.isfavor)
            put(COL12_MENU_NAME, item.menu_name)
            put(COL13_MENU_STAR, item.menu_star)
            put(COL14_MENU_IMAGE_URI, item.url)
            put(COL15_MENU_COMMENT, item.menu_comment)
            put(COL16_PRICE, item.price)
            put(COL17_HASHTAG, item.hashtag)
            put(COL18_CATEGORY1, item.category1)
            put(COL19_CATEGORY2, item.category2)
            put(COL20_CATEGORY3, item.category3)
            put(COL21_CATEGORY4, item.category4)
        }
        db.insert(TABLE_NAME, null, cv)
    }

    fun getItem(cursor: Cursor): Item {
        val item = Item(
            cursor.getInt(0),
            cursor.getString(1),
            cursor.getInt(2),
            cursor.getInt(3),
            cursor.getString(4),
            cursor.getDouble(5),
            cursor.getString(6),
            cursor.getString(7),
            cursor.getString(8),
            cursor.getString(9),
            cursor.getInt(10),
            cursor.getString(11),
            cursor.getDouble(12),
            cursor.getString(13),
            cursor.getString(14),
            cursor.getInt(15),
            cursor.getString(16),
            cursor.getString(17),
            cursor.getString(18),
            cursor.getString(19),
            cursor.getString(20)
        )
        return item
    }
    // 모든 list를 가져오는 함수
    fun getAllData(): ArrayList<Item> {
        var itemList: ArrayList<Item> = ArrayList()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COL2_REST_NAME IS NOT NULL", null)

        cursor?.run {
            while (cursor.moveToNext()) {
                val item = getItem(cursor)
                itemList.add(item)
            }
        }
        cursor.close()
        return itemList
    }

    fun getHashtag(): ArrayList<String> {
        var hashtag = ArrayList<String>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME " +
                "WHERE $COL2_REST_NAME IS NULL and $COL18_CATEGORY1 IS NULL", null)
        var str:String

        cursor?.let {
            while (cursor.moveToNext())
                hashtag.add(cursor.getString(16))
        }
        return hashtag
    }

    fun deleteItem(restList: ArrayList<String>) {
        val db = writableDatabase

        for (rest in restList)
            db.delete(TABLE_NAME, "rest_name = ?", arrayOf(rest))
    }

    fun checkHashtagExist(nowHashtag: String): Boolean {
        val hashtags = getHashtag()
        return hashtags.contains(nowHashtag)
    }

    /*
        hashtag를 하나 받아서 있는지 없는지 확인하고,
        없다면 db에 추가한다.
     */
    fun insertHashtag(hashtag: String) {
        val db = writableDatabase

        if (!checkHashtagExist(hashtag)) {
            val cv = ContentValues().apply {
                put(COL17_HASHTAG, hashtag)
            }
            db.insert(TABLE_NAME, null, cv)
        }
    }

    /*
        item인 경우
            해시태그 존재 : 업데이트
        해시태그인 경우 : 삭제
     */
    fun deleteHashtag(hashtag: String) {
        val wdb = writableDatabase
        val rdb = readableDatabase

        // 해시태그인 경우
        wdb.delete(TABLE_NAME, "hashtag = ?", arrayOf(hashtag))

        // item인 경우
        val cursor = rdb.rawQuery("SELECT * FROM $TABLE_NAME " +
                "WHERE $COL17_HASHTAG LIKE '%?%'", arrayOf(hashtag))

        cursor?.let {
            while (cursor.moveToNext()) {
                var oldstr = cursor.getString(16)
                val newstr = oldstr.replace(hashtag, "")
                val cv = ContentValues().apply {
                    put(COL17_HASHTAG, newstr)
                }
                wdb.update(TABLE_NAME, cv, "$COL17_HASHTAG = ?",
                    arrayOf(oldstr))
            }
        }
        cursor.close()
    }

    fun renameHashtag(hashtag:String, newhashtag:String) {
        val wdb = writableDatabase
        val rdb = readableDatabase
        val cv = ContentValues().apply {
            put(COL17_HASHTAG, newhashtag)
        }

        if (!checkHashtagExist(newhashtag)) {
            // 해시태그인 경우
            wdb.update(TABLE_NAME, cv, "hashtag = ?", arrayOf(hashtag))

            // item인 경우
            val cursor = rdb.rawQuery(
                "SELECT * FROM $TABLE_NAME " +
                        "WHERE $COL17_HASHTAG LIKE '%?%'", arrayOf(hashtag)
            )

            cursor?.let {
                while (cursor.moveToNext()) {
                    var oldstr = cursor.getString(16)
                    val newstr = oldstr.replace(hashtag, newhashtag)
                    val cv = ContentValues().apply {
                        put(COL17_HASHTAG, newstr)
                    }
                    wdb.update(
                        TABLE_NAME, cv, "$COL17_HASHTAG = ?",
                        arrayOf(oldstr)
                    )
                }
            }
            cursor.close()
        }
    }
}