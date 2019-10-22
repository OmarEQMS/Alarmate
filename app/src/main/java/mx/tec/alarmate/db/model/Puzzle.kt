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
    val idPuzzle: Int
    val type: PuzzleType
    val difficulty: PuzzleDifficulty
    val config: String
    @ColumnInfo(name = "fkAlarm", index = true)
    val fkAlarm: Int

    @Ignore
    constructor() {
        this.idPuzzle = 0
        this.type = PuzzleType.UNKNOWN
        this.difficulty = PuzzleDifficulty.UNKNOWN
        this.config = JSONObject().toString()
        this.fkAlarm = 0
    }

    constructor(idPuzzle: Int, type: PuzzleType, difficulty: PuzzleDifficulty, config: String, fkAlarm: Int) {
        this.idPuzzle = idPuzzle
        this.type = type
        this.difficulty = difficulty
        this.config = config
        this.fkAlarm = fkAlarm
    }

    @Ignore
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
