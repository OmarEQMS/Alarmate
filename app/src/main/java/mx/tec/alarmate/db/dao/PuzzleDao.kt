package mx.tec.alarmate.db.dao

import androidx.room.*
import mx.tec.alarmate.db.model.Puzzle

@Dao
interface PuzzleDao {
    @Query("SELECT * FROM Puzzle")
    fun listPuzzles(): List<Puzzle>

    @Query("SELECT * FROM Puzzle WHERE fkAlarm=:idAlarm")
    fun listAlarmPuzzles(idAlarm: Int): List<Puzzle>

    @Query("SELECT * FROM Puzzle WHERE idPuzzle=:idPuzzle")
    fun getPuzzle(idPuzzle: Int): Puzzle

    @Insert
    fun createPuzzle(puzzle: Puzzle)

    @Update
    fun updatePuzzle(puzzle: Puzzle)

    @Delete
    fun deletePuzzle(puzzle: Puzzle)
}