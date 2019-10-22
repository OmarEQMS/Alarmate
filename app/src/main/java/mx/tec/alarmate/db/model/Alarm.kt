package mx.tec.alarmate.db.model

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time

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
)