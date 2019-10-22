package mx.tec.alarmate.db.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.sql.Time

@Entity(foreignKeys = [ForeignKey(entity = Alarm::class,
                        parentColumns = ["idAlarm"],
                        childColumns = ["fkAlarm"],
                        onDelete = ForeignKey.NO_ACTION)]
)
data class Puzzle(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var idPuzzle: Long,
    var fkAlarm: Long
)
