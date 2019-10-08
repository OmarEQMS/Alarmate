package mx.tec.alarmate

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_edit_puzzle.*
import mx.tec.alarmate.Puzzle.MathPuzzle
import mx.tec.alarmate.Puzzle.Puzzle
import mx.tec.alarmate.Puzzle.PuzzleEditorListener

class EditPuzzle : AppCompatActivity(), PuzzleEditorListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_puzzle)

        val fragmentManager = supportFragmentManager
        val ft = fragmentManager.beginTransaction()
        ft.add(R.id.lytFragment, MathPuzzle())
        ft.commit()
    }

    override fun onPuzzleEdited(puzzle: Puzzle) {
        Log.d("EditAlarm", "Puzzle edited")
    }
}
