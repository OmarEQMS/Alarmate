package mx.tec.alarmate.db.dao

import androidx.room.*
import mx.tec.alarmate.db.model.Alarm

@Dao
interface AlarmDao {
    @Query("SELECT * FROM Alarm")
    fun listAlarms(): List<Alarm>
    @Query("SELECT * FROM Alarm WHERE idAlarm=:idAlarm")
    fun getAlarm(idAlarm: Int): Alarm
    @Insert
    fun createAlarm(alarm: Alarm)
    @Update
    fun updateAlarm(alarm: Alarm)
    @Delete
    fun deleteAlarm(alarm: Alarm)
}