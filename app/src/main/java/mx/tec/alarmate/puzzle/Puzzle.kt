package mx.tec.alarmate.Puzzle

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.*
import androidx.room.PrimaryKey
import mx.tec.alarmate.Alarm.Alarm
import org.json.JSONObject

@Entity(
    foreignKeys = arrayOf(
        ForeignKey(
        entity = Alarm::class,
        parentColumns = arrayOf("idAlarm"),
        childColumns = arrayOf("fkAlarm"))
    )
)
class Puzzle: Parcelable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "idPuzzle")
    val idPuzzle: Int
    @ColumnInfo(name = "type")
    val type: PuzzleType
    @ColumnInfo(name = "difficulty")
    val difficulty: PuzzleDifficulty
    @ColumnInfo(name = "config")
    val config: String
    @ColumnInfo(name = "fkAlarm")
    val fkAlarm: Int

    @Ignore
    constructor() {
        this.idPuzzle = 0
        this.type = PuzzleType.UNKNOWN
        this.difficulty = PuzzleDifficulty.UNKNOWN
        this.config = JSONObject().toString()
        this.fkAlarm = 0
    }

    @Ignore
    constructor(idPuzzle: Int, type: PuzzleType, difficulty: PuzzleDifficulty, config: String, fkAlarm: Int) {
        this.idPuzzle = idPuzzle
        this.type = type
        this.difficulty = difficulty
        this.config = config
        this.fkAlarm = fkAlarm
    }

    constructor(source: Parcel):this(
        source.readInt(),
        PuzzleType.valueOf(source.readInt().toString()),
        PuzzleDifficulty.valueOf(source.readInt().toString()),
        source.readString()!!,
        source.readInt()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            dest.writeInt(idPuzzle)
            dest.writeInt(type.code)
            dest.writeInt(difficulty.code)
            dest.writeInt(fkAlarm)
        }
    }

    override fun describeContents(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object CREATOR : Parcelable.Creator<Puzzle> {
        override fun createFromParcel(parcel: Parcel): Puzzle {
            return Puzzle(parcel)
        }

        override fun newArray(size: Int): Array<Puzzle?> {
            return arrayOfNulls(size)
        }
    }
}
