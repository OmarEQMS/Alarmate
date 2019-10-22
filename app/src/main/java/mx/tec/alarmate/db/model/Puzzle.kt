package mx.tec.alarmate.db.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.*
import androidx.room.PrimaryKey
import mx.tec.alarmate.puzzle.PuzzleDifficulty
import mx.tec.alarmate.puzzle.PuzzleDifficultyConverter
import mx.tec.alarmate.puzzle.PuzzleType
import mx.tec.alarmate.puzzle.PuzzleTypeConverter
import org.json.JSONObject

@Entity(foreignKeys = [ForeignKey(
    entity = Alarm::class,
    parentColumns = ["idAlarm"],
    childColumns = ["fkAlarm"],
    onDelete = ForeignKey.NO_ACTION)]
)
@TypeConverters(PuzzleDifficultyConverter::class, PuzzleTypeConverter::class)
class Puzzle: Parcelable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    val idPuzzle: Long
    var type: PuzzleType
    var difficulty: PuzzleDifficulty
    var config: String
    @ColumnInfo(name = "fkAlarm", index = true)
    var fkAlarm: Int

    @Ignore
    constructor() {
        this.idPuzzle = 0
        this.type = PuzzleType.UNKNOWN
        this.difficulty = PuzzleDifficulty.UNKNOWN
        this.config = JSONObject().toString()
        this.fkAlarm = 0
    }

    constructor(idPuzzle: Long, type: PuzzleType, difficulty: PuzzleDifficulty, config: String, fkAlarm: Int) {
        this.idPuzzle = idPuzzle
        this.type = type
        this.difficulty = difficulty
        this.config = config
        this.fkAlarm = fkAlarm
    }

    @Ignore
    constructor(source: Parcel):this(
        source.readLong(),
        PuzzleTypeConverter().getType(source.readInt())!!,
        PuzzleDifficultyConverter().getDifficulty(source.readInt())!!,
        source.readString()!!,
        source.readInt()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            dest.writeLong(idPuzzle)
            dest.writeInt(type.code)
            dest.writeInt(difficulty.code)
            dest.writeString(config)
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

    override fun toString(): String {
        return "idPuzzle: ${idPuzzle}, type: ${type.name}, difficulty: ${difficulty.name}, config: ${config}, fkAlarm: ${fkAlarm}"
    }
}
