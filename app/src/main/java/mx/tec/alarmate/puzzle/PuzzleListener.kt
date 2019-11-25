package mx.tec.alarmate.puzzle

import mx.tec.alarmate.db.model.Puzzle

interface PuzzleListener {
    fun onPuzzleSuccess(puzzle: Puzzle?)
    fun onPuzzleFail(puzzle: Puzzle?)
}