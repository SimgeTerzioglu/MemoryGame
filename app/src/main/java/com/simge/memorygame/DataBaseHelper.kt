package com.simge.memorygame

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val databaseName = "database"
val tableName = "users"
val columnName = "username"
val columnScore = "score"
val columnTime = "time"
val columnId = "Id"

class DataBaseHelper(var context: Context):SQLiteOpenHelper(context, databaseName,null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        var createTable = " CREATE TABLE " + tableName +"(" +
                columnId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                columnName + " VARCHAR(256)," +
                columnTime + " VARCHAR(256),"+
                columnScore + " VARCHAR(256))"
        db?.execSQL(createTable)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun insertData(users : UserModel){
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(columnName,users.username)
        cv.put(columnScore,users.score)
        cv.put(columnTime,users.time)

        var sonuc = db.insert(tableName,null,cv)

        if (sonuc == (-1).toLong()){
            Toast.makeText(context,"error",Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(context,"success",Toast.LENGTH_LONG).show()

        }

    }

    @SuppressLint("Range")
    fun getUsers(myContext:Context):ArrayList<UserModel>{
        val query = "Select * From $tableName"
        val db : SQLiteDatabase = this.readableDatabase
        val cursor = db.rawQuery(query,null)
        val users = ArrayList<UserModel>()

        if(cursor.count == 0){
            Toast.makeText(myContext,"No records found",Toast.LENGTH_LONG).show()
        }else{
            while (cursor.moveToNext()){
                var user = UserModel()
                user.id = cursor.getString(cursor.getColumnIndex(columnId)).toInt()
                user.username = cursor.getString(cursor.getColumnIndex(columnName))
                user.score = cursor.getString(cursor.getColumnIndex(columnScore))
                user.time = cursor.getString(cursor.getColumnIndex(columnTime))
                users.add(user)
            }
        }

        cursor.close()
        db.close()
        return users
    }

}