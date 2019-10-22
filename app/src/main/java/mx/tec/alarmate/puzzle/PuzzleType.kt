package mx.tec.alarmate.puzzle

import androidx.room.TypeConverter

enum class PuzzleType constructor(val code: Int) {
    UNKNOWN(0),
    MATH(1),
    MAZE(2),
    SEQUENCE(3),
    REWRITE(4),
    SCAN(5);
}

class PuzzleTypeConverter {
    @TypeConverter
    fun getType(numeral: Int?): PuzzleType? {
        for (ds in PuzzleType.values()) {
            if (ds.code === numeral) {
                return ds
            }
        }
        return null
    }

    @TypeConverter
    fun getTypeInt(status: PuzzleType?): Int? {
        return if (status != null) status!!.code else null

    }
}

