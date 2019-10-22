package mx.tec.alarmate.puzzle

import androidx.room.TypeConverter

enum class PuzzleDifficulty constructor(val code: Int) {
    UNKNOWN(0),
    EASY(1),
    MEDIUM(2),
    HARD(3);

    @TypeConverter
    fun getType(numeral: Int?): PuzzleDifficulty? {
        for (ds in values()) {
            if (ds.code === numeral) {
                return ds
            }
        }
        return null
    }

    @TypeConverter
    fun getTypeInt(status: PuzzleDifficulty?): Int? {
        return if (status != null) status!!.code else null

    }
}

class PuzzleDifficultyConverter {

    @TypeConverter
    fun getType(numeral: Int?): PuzzleDifficulty? {
        for (ds in PuzzleDifficulty.values()) {
            if (ds.code === numeral) {
                return ds
            }
        }
        return null
    }

    @TypeConverter
    fun getTypeInt(status: PuzzleDifficulty?): Int? {
        return if (status != null) status!!.code else null

    }
}

