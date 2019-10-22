package mx.tec.alarmate.puzzle

import mx.tec.alarmate.db.model.Puzzle

interface PuzzleEditorListener {
    fun onPuzzleEdited(puzzle: Puzzle)
}