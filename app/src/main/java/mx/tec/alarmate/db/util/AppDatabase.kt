package mx.tec.alarmate.db.util

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mx.tec.alarmate.db.dao.AlarmDao
import mx.tec.alarmate.db.dao.PuzzleDao
import mx.tec.alarmate.db.model.Alarm
import mx.tec.alarmate.db.model.Puzzle

@Database(entities = arrayOf(Alarm::class, Puzzle::class), version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase(){
    abstract fun alarmDao(): AlarmDao
    abstract fun puzzleDao(): PuzzleDao
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