package mx.tec.alarmate.Puzzle

import androidx.room.*

@Dao
interface PuzzleDao {
    @Query("SELECT * FROM Puzzle")
    fun getPuzzles(): List<Puzzle>

    @Insert
    fun insertPuzzle(puzzle: Puzzle)

    @Update
    fun updatePuzzle(puzzle: Puzzle)

    @Delete
    fun deletePuzzle(puzzle: Puzzle)
}