package mx.tec.alarmate.puzzle

import mx.tec.alarmate.db.model.Puzzle

interface PuzzleListener {
    fun onPuzzleSuccess()
    fun onPuzzleFail()
}