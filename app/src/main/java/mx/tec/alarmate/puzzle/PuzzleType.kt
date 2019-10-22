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
    fun getType(code: Int?): PuzzleType? {
        for (ds in PuzzleType.values()) {
            if (ds.code === code) {
                return ds
            }
        }
        return null
    }

    @TypeConverter
    fun getTypeInt(type: PuzzleType?): Int? {
        return if (type != null) type!!.code else null

    }
}

