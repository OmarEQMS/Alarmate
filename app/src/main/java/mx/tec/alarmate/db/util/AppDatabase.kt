package mx.tec.alarmate.db.util

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mx.tec.alarmate.db.dao.AlarmDao
import mx.tec.alarmate.db.model.Alarm

@Database(entities = arrayOf(Alarm::class), version = 1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun alarmDao(): AlarmDao
    companion object{
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "Alarmate"
                ).build()
            }
            return INSTANCE as AppDatabase
        }
    }
}