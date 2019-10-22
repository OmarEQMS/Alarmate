package mx.tec.alarmate.db.dao

import androidx.room.*
import mx.tec.alarmate.db.model.Puzzle

@Dao
interface PuzzleDao {
    @Query("SELECT * FROM Puzzle")
    fun listPuzzles(): List<Puzzle>

    @Query("SELECT * FROM Puzzle WHERE idPuzzle=:idPuzzle")
    fun getPuzzle(idPuzzle: Int): Puzzle

    @Insert
    fun insertPuzzle(puzzle: Puzzle)

    @Update
    fun updatePuzzle(puzzle: Puzzle)

    @Delete
    fun deletePuzzle(puzzle: Puzzle)
}