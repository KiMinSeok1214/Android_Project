package com.example.project_last

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import androidx.core.content.contentValuesOf
import java.util.ArrayList

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
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COL2_REST_NAME IS NOT ''", null)

        cursor?.run {
            while (cursor.moveToNext()) {
                val item = getItem(cursor)
                itemList.add(item)
            }
        }
        cursor.close()
        return itemList
    }

    fun getAllHashtag(): ArrayList<Item> {
        var hashtagList: ArrayList<Item> = ArrayList()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COL2_REST_NAME = '' AND $COL18_CATEGORY1 = ''", null)

        cursor?.run {
            while (cursor.moveToNext()) {
                val hashtag = getItem(cursor)
                hashtagList.add(hashtag)
            }
        }
        cursor.close()
        return hashtagList
    }

    // 모든 음식점 list를 가져오는 함수
    fun getAllRestaurent(): ArrayList<Restaurent> {
        var restList = ArrayList<Restaurent>()
        val db = this.readableDatabase
        val restNameList = ArrayList<String>() // 음식점 이름만 담을 list

        val cursor = db.rawQuery("SELECT DISTINCT $COL2_REST_NAME FROM $TABLE_NAME WHERE $COL2_REST_NAME IS NOT ''", null)
        cursor?.let {
            while (cursor.moveToNext())
                restNameList.add(cursor.getString(0))
        }
        for (rest_name in restNameList) {
            val restaurent = getRestData(rest_name)
            val isdilivery = checkDelivery(restaurent)
            val isvisit = checkVisit(restaurent)
            val isfavor = checkFavor(restaurent)

            restList.add(Restaurent(
                rest_name,
                restaurent[0].rest_star.toFloat(),
                restaurent[0].rest_comment,
                restaurent[0].main_image_uri,
                isdilivery,
                isvisit,
                isfavor
            ))
        }
        cursor.close()
        return restList
    }

    fun getDiaryList(rest_name: String): ArrayList<Diary> {
        val db = this.readableDatabase
        val diaryList = ArrayList<Diary>()
        val dateList = ArrayList<String>()

        var cursor = db.rawQuery("SELECT DISTINCT $COL5_DATE FROM $TABLE_NAME " +
                "WHERE $COL2_REST_NAME = ?", arrayOf(rest_name))
        cursor?.let {
            while (cursor.moveToNext())
                dateList.add(cursor.getString(0))
        }
        cursor.close()
        for (date in dateList) {
            var rest_star = 0.0f
            var comment = ""
            var isdelivery = 0
            var isvisit = 0
            var menuList = ArrayList<String>()
            var hashtagList = ArrayList<String>()

            cursor = db.rawQuery(
                "SELECT * FROM $TABLE_NAME " +
                        "WHERE $COL2_REST_NAME = ? AND $COL5_DATE = ?", arrayOf(rest_name, date)
            )
            while (cursor.moveToNext()) {
                rest_star = cursor.getFloat(5)
                comment = cursor.getString(6)
                isdelivery = cursor.getInt(3)
                isvisit = cursor.getInt(2)
                menuList.add(cursor.getString(12))
            }
            hashtagList = getRestHash(rest_name)
            diaryList.add(Diary(
                date,
                rest_star,
                comment,
                hashtagList,
                isdelivery,
                isvisit,
                menuList
            ))
        }
        return diaryList
    }

    private fun getRestHash(rest_name: String): ArrayList<String> {
        var hashList = ArrayList<String>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT DISTINCT $COL17_HASHTAG FROM $TABLE_NAME " +
                "WHERE $COL2_REST_NAME = ?", arrayOf(rest_name))

        if (cursor.moveToFirst())
        {
            val hashtag = cursor.getString(0)
            hashList = stringToArrayList(hashtag)
        }
        return hashList
    }
    fun stringToArrayList(input: String): ArrayList<String> {
        return ArrayList(input.split("\n").filter { it.isNotEmpty() })
    }
    private fun checkFavor(restaurent: ArrayList<Item>): Int {
        for (item in restaurent) {
            if (item.isvisit == 1)
                return 1
        }
        return 0
    }

    private fun checkVisit(restaurent: ArrayList<Item>): Int {
        for (item in restaurent) {
            if (item.isvisit == 1)
                return 1
        }
        return 0
    }

    private fun checkDelivery(restaurent: ArrayList<Item>): Int {
        for (item in restaurent) {
            if (item.isdelivery == 1)
                return 1
        }
        return 0
    }



    fun getHashtag(): ArrayList<String> {
        var hashtag = ArrayList<String>()
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_NAME " +
                    "WHERE $COL2_REST_NAME = '' and $COL18_CATEGORY1 = ''", null
        )

        cursor?.let {
            while (cursor.moveToNext())
                hashtag.add(cursor.getString(16))
        }
        return hashtag
    }

    fun deleteRest(restList: ArrayList<String>) {
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
        if (!checkHashtagExist(hashtag)) {
            val item = Item(hashtag = hashtag)
            insertItem(item)
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
        val cursor = rdb.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COL2_REST_NAME != '' AND $COL17_HASHTAG LIKE ?", arrayOf("%$hashtag%"))


        cursor?.let {
            while (cursor.moveToNext()) {
                var oldstr = cursor.getString(16)
                var list = stringToArrayList(cursor.getString(16))
                list.remove("$hashtag")
                val newstr = list.joinToString("\n")
                val cv = ContentValues().apply {
                    put(COL17_HASHTAG, newstr)
                }
                wdb.update(TABLE_NAME, cv, "$COL17_HASHTAG = ?", arrayOf(oldstr))
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
                "SELECT * FROM $TABLE_NAME WHERE $COL2_REST_NAME != '' AND $COL18_CATEGORY1 != '' AND $COL17_HASHTAG LIKE ?", arrayOf("%$hashtag%")
            )

            cursor?.let {
                while (cursor.moveToNext()) {
                    var oldstr = cursor.getString(16)
                    var list = stringToArrayList(cursor.getString(16))
                    list.remove(hashtag)
                    list.add(newhashtag)
                    val newstr = list.joinToString("\n")
                    val cv = ContentValues().apply { put(COL17_HASHTAG, newstr) }
                    wdb.update(TABLE_NAME, cv, "$COL17_HASHTAG = ?", arrayOf(oldstr))
                }
            }
            cursor.close()
        }
    }

    // h1을 -> h2로 바꾸고 h1을 삭제
    fun combineH1ToH2(hashtag1:String, hashtag2:String) {
        val wdb = writableDatabase
        val rdb = readableDatabase

        // 해시태그인 경우 삭제
        wdb.delete(TABLE_NAME, "hashtag = ?", arrayOf(hashtag1))

        // item인 경우 h1 -> h2로 rename
        val cursor = rdb.rawQuery(
            "SELECT * FROM $TABLE_NAME WHERE $COL2_REST_NAME != '' AND $COL18_CATEGORY1 != '' AND $COL17_HASHTAG LIKE ?", arrayOf("%${hashtag1}%")
        )
        cursor?.let {
            while (cursor.moveToNext()) {
                var oldstr = cursor.getString(16)
                var list = stringToArrayList(cursor.getString(16))

                list.remove(hashtag1)
                list.add(hashtag2)
                val newstr = list.joinToString("\n")
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
    // 테스트용입니다 00
    fun insertRH(restaurant : String, hashtag: String) {
        val db = writableDatabase
        val item = Item(rest_name = restaurant, hashtag = hashtag)
        insertItem(item)
    }

    fun getHASHREST(hashtag : String): ArrayList<Restaurent> {
        val restNameList = ArrayList<String>()
        var restList: ArrayList<Restaurent> = ArrayList()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COL2_REST_NAME != ''", null)

        cursor?.let {
            while (cursor.moveToNext()) {
                var list = stringToArrayList(cursor.getString(16))
                if (list.contains(hashtag))
                    restNameList.add(cursor.getString(1))
            }
        }

        for (rest_name in restNameList) {
            val restaurent = getRestData(rest_name)
            val isdilivery = checkDelivery(restaurent)
            val isvisit = checkVisit(restaurent)
            val isfavor = checkFavor(restaurent)

            restList.add(Restaurent(
                rest_name,
                restaurent[0].rest_star.toFloat(),
                restaurent[0].rest_comment,
                restaurent[0].main_image_uri,
                isdilivery,
                isvisit,
                isfavor
            ))
        }
        return restList
    }



    // 테스트용입니다 00
    fun getRestHASH(): ArrayList<String> {
        val rest = ArrayList<String>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COL2_REST_NAME IS NOT NULL", null)
        var str:String

        cursor?.let {
            while (cursor.moveToNext())
                rest.add(cursor.getString(1) + "'s hashtags\n ->" + cursor.getString(16) + '\n')
        }
        return rest
    }

    // 테스트용입니다 00
    fun checkRestaurantExist(rest: String): Boolean {
        val hashtags = getRestHASH()
        return hashtags.contains(rest)
    }

    // 테스트용입니다 00
    fun addH2R(restaurant : String, hashtag: String) {
        val db = writableDatabase
        if (checkRestaurantExist(restaurant) && checkHashtagExist(restaurant)) {
            val wdb = writableDatabase
            val rdb = readableDatabase

            // item인 경우
            val cursor = rdb.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COL2_REST_NAME = ? and $COL17_HASHTAG NOT LIKE '%?%'", arrayOf(restaurant, hashtag))

            cursor?.let {
                while (cursor.moveToNext()) {
                    val newstr = cursor.getString(16) + '\n' + hashtag
                    val cv = ContentValues().apply {
                        put(COL17_HASHTAG, newstr)
                    }
                    wdb.update(TABLE_NAME, cv, "$COL2_REST_NAME = ?",
                        arrayOf(restaurant))
                }
            }
        }
    }

    // 테스트용입니다 00
    fun deleteH2R(restaurant : String, hashtag: String) {
        if (checkRestaurantExist(restaurant) && checkHashtagExist(restaurant)) {
            val wdb = writableDatabase
            val rdb = readableDatabase

            // item인 경우
            val cursor = rdb.rawQuery("SELECT * FROM $TABLE_NAME " +
                    "WHERE $COL2_REST_NAME = ? and $COL17_HASHTAG LIKE '%?%'", arrayOf(restaurant, hashtag))
            cursor?.let {
                while (cursor.moveToNext()) {
                    var oldstr = cursor.getString(16)
                    val newstr = oldstr.replace(hashtag, "")
                    val cv = ContentValues().apply {
                        put(COL17_HASHTAG, newstr)
                    }
                    wdb.update(TABLE_NAME, cv, "$COL2_REST_NAME = ?",
                        arrayOf(restaurant))
                }
            }
        }
    }

    // 카테고리를 추가하는 함수
    fun insertCategory(clist : ArrayList<String>) {
        val db = writableDatabase
        val item = Item(category1 = clist[0], category2 = clist[1], category3 = clist[2], category4 = clist[3])
        insertItem(item)
    }
    // 카테고리를 삭제하는 함수
    fun deleteCategory(clist : ArrayList<String>) {
        val db = writableDatabase
        val list = arrayOf(clist[0], clist[1], clist[2], clist[3])
        db.delete(TABLE_NAME, "category1 = ? and category2 = ? and category3 = ? and category4 = ?", list)
    }

    //
    fun getCategory() : ArrayList<String> {
        val db = this.readableDatabase

        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COL2_REST_NAME = '' and $COL17_HASHTAG = ''", null)
        var str:String

        val list = ArrayList<String>()
        cursor?.let {
            while (cursor.moveToNext())
                list.add(cursor.getString(17) + " " + cursor.getString(18) + " " + cursor.getString(19) + " " + cursor.getString(20))
        }
        return list
    }
    fun renameCategory(oldlist : ArrayList<String>, newlist : ArrayList<String>) {
        val wdb = writableDatabase
        val rdb = readableDatabase
        val cv = ContentValues().apply {
            put(COL18_CATEGORY1, newlist[0])
            put(COL19_CATEGORY2, newlist[1])
            put(COL20_CATEGORY3, newlist[2])
            put(COL21_CATEGORY4, newlist[3])
        }
        wdb.update(TABLE_NAME, cv, "$COL18_CATEGORY1 = ? AND  $COL19_CATEGORY2 = ? AND  $COL20_CATEGORY3 = ? AND  $COL21_CATEGORY4 = ?", arrayOf(oldlist[0], oldlist[1], oldlist[2], oldlist[3]))
    }
    fun setrestLove(restnames: ArrayList<String>) {
        val wdb = writableDatabase
        val cv = ContentValues().apply {
            put(COL11_ISFAVOR, 1)
        }
        for (rest_name in restnames)
            wdb.update(TABLE_NAME, cv, "rest_name = ?", arrayOf(rest_name))
    }
    fun getRestData(rest_name: String): ArrayList<Item> {
        var itemList: ArrayList<Item> = ArrayList()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COL2_REST_NAME = ? " +
                "ORDER BY $COL5_DATE DESC", arrayOf(rest_name))

        cursor?.run {
            while (cursor.moveToNext()) {
                val item = getItem(cursor)
                itemList.add(item)
            }
        }
        cursor.close()
        return itemList
    }
    fun getKeywordData(keyword: String): ArrayList<Restaurent> {
        val db = this.readableDatabase
        var restList: ArrayList<Restaurent> = ArrayList()
        val restNameList = ArrayList<String>()

        val cursor = db.rawQuery(
            "SELECT DISTINCT $COL2_REST_NAME FROM $TABLE_NAME " +
                    "WHERE $COL2_REST_NAME LIKE '%' || ? || '%' " +
                    "OR $COL7_REST_COMMENT LIKE '%' || ? || '%' " +
                    "OR $COL9_ADDRESS LIKE '%' || ? || '%' " +
                    "OR $COL12_MENU_NAME LIKE '%' || ? || '%' " +
                    "OR $COL15_MENU_COMMENT LIKE '%' || ? || '%' " +
                    "OR $COL17_HASHTAG LIKE '%' || ? || '%' " +
                    "OR $COL18_CATEGORY1 LIKE '%' || ? || '%' " +
                    "OR $COL19_CATEGORY2 LIKE '%' || ? || '%' " +
                    "OR $COL20_CATEGORY3 LIKE '%' || ? || '%' " +
                    "OR $COL21_CATEGORY4 LIKE '%' || ? || '%'",
            arrayOf(keyword, keyword, keyword, keyword, keyword, keyword, keyword, keyword, keyword, keyword)
        )

        cursor?.run {
            while (cursor.moveToNext()) {
                val restName = cursor.getString(0)
                // 특정 키워드가 포함된 경우에만 restNameList에 추가
                restNameList.add(restName)
            }
        }
        for (rest_name in restNameList) {
            val restaurent = getRestData(rest_name)
            val isdilivery = checkDelivery(restaurent)
            val isvisit = checkVisit(restaurent)
            val isfavor = checkFavor(restaurent)

            restList.add(Restaurent(
                rest_name,
                restaurent[0].rest_star.toFloat(),
                restaurent[0].rest_comment,
                restaurent[0].main_image_uri,
                isdilivery,
                isvisit,
                isfavor
            ))
        }
        cursor.close()
        return restList
    }

    fun loveRest(rest: String, setting: Int) {
        val db = writableDatabase
        val cv = ContentValues().apply {
            put(COL11_ISFAVOR, setting)
        }
        db.update(TABLE_NAME, cv, "$COL2_REST_NAME = ?", arrayOf(rest))
    }

    fun deleteDiary(rest_name: String, date: String) {
        val db = writableDatabase

        db.delete(TABLE_NAME, "rest_name = ? and date = ?", arrayOf(rest_name, date))
    }
}