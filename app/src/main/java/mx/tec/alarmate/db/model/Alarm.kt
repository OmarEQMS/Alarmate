package mx.tec.alarmate.db.model

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import mx.tec.alarmate.EditAlarm
import mx.tec.alarmate.alarm.AlarmReceiver
import java.sql.Time
import java.util.*

@Entity
data class Alarm(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var idAlarm: Long,
    var name: String,
    var active : Boolean,
    var hour: String,
    var monday: Boolean,
    var tuesday: Boolean,
    var wednesday: Boolean,
    var thursday: Boolean,
    var friday: Boolean,
    var saturday: Boolean,
    var sunday: Boolean,
    var vibration: Boolean,
    var flash: Boolean
): Parcelable{
    constructor(source: Parcel):this(
        source.readLong(),
        source.readString()!!,
        source.readInt()>0,
        source.readString()!!,
        source.readInt()>0,
        source.readInt()>0,
        source.readInt()>0,
        source.readInt()>0,
        source.readInt()>0,
        source.readInt()>0,
        source.readInt()>0,
        source.readInt()>0,
        source.readInt()>0

    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(idAlarm)
        parcel.writeString(name)
        parcel.writeInt(if (active) 1 else 0)
        parcel.writeString(hour)
        parcel.writeInt(if (monday) 1 else 0)
        parcel.writeInt(if (tuesday) 1 else 0)
        parcel.writeInt(if (wednesday) 1 else 0)
        parcel.writeInt(if (thursday) 1 else 0)
        parcel.writeInt(if (friday) 1 else 0)
        parcel.writeInt(if (saturday) 1 else 0)
        parcel.writeInt(if (sunday) 1 else 0)
        parcel.writeInt(if (vibration) 1 else 0)
        parcel.writeInt(if (flash) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Alarm> {
        override fun createFromParcel(parcel: Parcel): Alarm {
            return Alarm(parcel)
        }

        override fun newArray(size: Int): Array<Alarm?> {
            return arrayOfNulls(size)
        }
    }

    fun getDaysArray(): ArrayList<Int>{
        val days = arrayListOf<Int>()
        if(sunday){
            days.add(Calendar.SUNDAY)
        }
        if(monday){
            days.add(Calendar.MONDAY)
        }
        if(tuesday){
            days.add(Calendar.TUESDAY)
        }
        if(wednesday){
            days.add(Calendar.WEDNESDAY)
        }
        if(thursday){
            days.add(Calendar.THURSDAY)
        }
        if(friday){
            days.add(Calendar.FRIDAY)
        }
        if(saturday){
            days.add(Calendar.SATURDAY)
        }
        return days
    }

    fun getNextDay(): Int{
        val days = getDaysArray()
        val calendar = Calendar.getInstance()
        val hour = getAlarmHour()
        val minute = getAlarmMinute()
        val currDay = calendar.get(Calendar.DAY_OF_WEEK)
        val currHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currMinute = calendar.get(Calendar.MINUTE)

        for (day in days){
            if(day > currDay){
                return day
            }else if(day == currDay){
                if(hour > currHour){
                    return day
                } else if(hour == currHour && minute > currMinute){
                    return day
                }
            }
        }
        return -1
    }

    fun getAlarmHour(): Int{
        return hour.split(":")[0].toInt()
    }

    fun getAlarmMinute(): Int{
        return hour.split(":")[1].toInt()
    }

    fun registerNextAlarm(ctx: Context){
        if(!active){
            Log.d(Alarm::class.java.simpleName, "Alarm with id: ${idAlarm}, name: ${name} not active, skipping it")
            return
        }

        // Intent part
        val i = Intent(ctx, AlarmReceiver::class.java)
        i.action = "${AlarmReceiver.ACTION_TRIGGER_ALARM}${idAlarm}"

        val pendingIntentRequestCode = 0
        val flags = PendingIntent.FLAG_UPDATE_CURRENT
        val pendingIntent = PendingIntent.getBroadcast(ctx, pendingIntentRequestCode, i, flags)

        // Alarm time
        var valid = false
        val calendar: Calendar = Calendar.getInstance().apply {
            val nextDay = getNextDay()
            if(nextDay>-1){
                set(Calendar.HOUR_OF_DAY, getAlarmHour())
                set(Calendar.MINUTE, getAlarmMinute())
                set(Calendar.DAY_OF_WEEK, nextDay)
                valid = true
            }else{
                Log.d(Alarm::class.java.simpleName, "Alarm with id: ${idAlarm}, name: ${name} invalid next day, skipping it")
            }
        }

        if(!valid){
            return
        }

        // Get AlarmManager instance
        val alarmManager = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Set with system Alarm Service
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        Log.d(EditAlarm::class.java.simpleName, "Configured alarm ")
    }
}
