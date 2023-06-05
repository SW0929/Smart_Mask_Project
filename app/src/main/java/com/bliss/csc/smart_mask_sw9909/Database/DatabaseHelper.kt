package com.bliss.csc.smart_mask_sw9909.Database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Insets.add
import androidx.core.graphics.Insets.add
import java.text.SimpleDateFormat
import java.util.*

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    //Database Table/Column 정의 PART
    companion object {
        private const val DATABASE_NAME = "SmartmaskDatabase.db"
        private const val DATABASE_VERSION = 1

        //TODO USER 테이블 선언부
        internal const val UserTABLE_NAME = "USERtable"
        private const val U_COLUMN_PK = "UserPK"
        internal const val U_COLUMN_Sleepstart = "Sleepstart"
        internal const val U_COLUMN_SleepFinish = "Sleepfinish"
        internal const val U_COLUMN_Sleeptime = "Sleeptime"
        internal const val U_COLUMN_Averagetemp = "Averagetemp"
        internal const val U_COLUMN_Nosecount = "Nosecount"
        internal const val U_COLUMN_Alarmtime = "Alarmtime"

        //TODO 소리센서 테이블 선언부
        internal const val SoundTABLE_NAME = "SOUNDTable"
        private const val S_COLUMN_PK = "SoundPK"
        private const val S_COLUMN_FK = "UserPK_FK"
        internal const val S_COLUMN_Recordtime = "Recordtime"
        internal const val S_COLUMN_decibel = "Decibel"

        //TODO 온도센서 테이블 선언부
        internal const val TempTABLE_NAME = "TEMPtable"
        private const val T_COLUMN_ID = "TempPK"
        private const val T_COLUMN_FK = "UserPK_FK"
        internal const val T_COLUMN_Recordtime = "Recordtime"
        internal const val T_COLUMN_Temp = "Temperature"
    }

    //Database 생성 PART
    override fun onCreate(db: SQLiteDatabase?) {
        val createUserTable = "CREATE TABLE $UserTABLE_NAME (" +
                "$U_COLUMN_PK TEXT PRIMARY KEY," +
                "$U_COLUMN_Sleepstart TEXT NOT NULL," +
                "$U_COLUMN_SleepFinish TEXT DEFAULT 0," +
                "$U_COLUMN_Sleeptime INTEGER DEFAULT 0," +
                "$U_COLUMN_Averagetemp INTEGER DEFAULT 0," +
                "$U_COLUMN_Nosecount INTEGER DEFAULT 0," +
                "$U_COLUMN_Alarmtime TEXT DEFAULT 0" +
                ")"

        val createSoundTable = "CREATE TABLE $SoundTABLE_NAME (" +
                "$S_COLUMN_PK INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$S_COLUMN_FK TEXT," +
                "$S_COLUMN_Recordtime TEXT," +
                "$S_COLUMN_decibel INTEGER," +
                "FOREIGN KEY($S_COLUMN_FK) REFERENCES $UserTABLE_NAME($U_COLUMN_PK)" +
                ")"

        val createTempTable = "CREATE TABLE $TempTABLE_NAME (" +
                "$T_COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$T_COLUMN_FK TEXT," +
                "$T_COLUMN_Recordtime TEXT," +
                "$T_COLUMN_Temp INTEGER," +
                "FOREIGN KEY($T_COLUMN_FK) REFERENCES $UserTABLE_NAME($U_COLUMN_PK)" +
                ")"

        db?.execSQL(createUserTable)
        db?.execSQL(createSoundTable)
        db?.execSQL(createTempTable)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // 기존 테이블을 삭제하고 새로운 버전의 테이블을 생성할 수 있음.
        db?.execSQL("DROP TABLE IF EXISTS $UserTABLE_NAME")
        db?.execSQL("DROP TABLE IF EXISTS $SoundTABLE_NAME")
        db?.execSQL("DROP TABLE IF EXISTS $TempTABLE_NAME")
        onCreate(db)
    }

    //USER DATA Insert하는 함수 (수면시작)
    fun insertData_user(
        UserPK: String?,
        수면시작시간: String?
    ){
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(U_COLUMN_PK, UserPK)
        contentValues.put(U_COLUMN_Sleepstart, 수면시작시간)
        db.insert(DatabaseHelper.UserTABLE_NAME, null, contentValues)
        db.close()
    }

    //수면시작시간 조회(Slect startsleep)
    @SuppressLint("Range")
    fun getSleepstart(userPK: String): String? {
        val query = "SELECT $U_COLUMN_Sleepstart FROM $UserTABLE_NAME WHERE $U_COLUMN_PK = ?"

        val db = this.readableDatabase
        val cursor: Cursor? = db.rawQuery(query, arrayOf(userPK.toString()))

        var Sleepstart: String? = null
        if (cursor?.moveToFirst() == true) {
            Sleepstart = cursor.getString(cursor.getColumnIndex(U_COLUMN_Sleepstart))
        }

        cursor?.close()
        db.close()

        return Sleepstart
    }

    //수면종료 버튼 클릭 시, COL(수면종료시간, 총수면시간, 평균온도값, 코골이횟수) UPDATE
    fun updateSleepfinish(userPK: String, starttime: String, finishtime: String, str_temp: Int, str_nosecount: Int): Boolean {
        val recordedTimeToSeconds = convertToSeconds(starttime.toString()) //starttime 초환산
        val currenttimetoSeconds = convertToSeconds(finishtime) //finishtime 초환산
        val converttime = convertToTime(currenttimetoSeconds-recordedTimeToSeconds) //update수면시간

        val db = this.writableDatabase
        val values = ContentValues()
        values.put(U_COLUMN_SleepFinish, finishtime)
        values.put(U_COLUMN_Sleeptime, converttime)
        values.put(U_COLUMN_Averagetemp, str_temp)
        values.put(U_COLUMN_Nosecount, str_nosecount)

        val rowsAffected = db.update(UserTABLE_NAME, values, "$U_COLUMN_PK = ?", arrayOf(userPK.toString()))
        db.close()

        return rowsAffected > 0
    }

    //알람설정 버튼 클릭 시, COL(Alarmtime) UPDATE
    fun updateSleepfinish(userPK: String, alarmtime: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(U_COLUMN_Alarmtime, alarmtime)

        val rowsAffected = db.update(UserTABLE_NAME, values, "$U_COLUMN_PK = ?", arrayOf(userPK.toString()))
        db.close()

        return rowsAffected > 0
    }

    //Calendar 조회하기 버튼 Function
    fun getCalendarRecord(primaryKeyValue: String): Cursor? {
        val db = this.readableDatabase
        val selection = "$U_COLUMN_PK = ?"
        val selectionArgs = arrayOf(primaryKeyValue.toString())
        return db.query(UserTABLE_NAME, null, selection, selectionArgs, null, null, null)
    }

    //RESET 버튼 Function
    fun deleteData(userID: String) {
        val db = this.writableDatabase
        val selection1 = "$S_COLUMN_FK = ?"
        val selection2 = "$T_COLUMN_FK = ?"
        val selection3 = "$U_COLUMN_PK = ?"
        db.delete(SoundTABLE_NAME, selection1, arrayOf(userID.toString()))
        db.delete(TempTABLE_NAME, selection2, arrayOf(userID.toString()))
        db.delete(UserTABLE_NAME, selection3, arrayOf(userID.toString()))
        db.close()
    }

    //시간 관련 함수
    fun convertToSeconds(recordedTime: String): Int {
        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val time = timeFormat.parse(recordedTime)
        val seconds = time.seconds
        val minutes = time.minutes
        val hours = time.hours

        return seconds + (minutes * 60) + (hours * 3600)
    }
    //초->시간단위로 변환 함수
    fun convertToTime(seconds: Int): String {
        val hour = seconds / 3600
        val minute = (seconds % 3600) / 60
        val second = seconds % 60

        return String.format("%02d:%02d:%02d", hour, minute, second)
    }
}