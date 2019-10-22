package mx.tec.alarmate

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_edit_puzzle.*
import mx.tec.alarmate.puzzle.PuzzleEditFragment
import mx.tec.alarmate.puzzle.PuzzleEditorListener
import mx.tec.alarmate.puzzle.PuzzleType
import mx.tec.alarmate.db.model.Puzzle

class EditPuzzle : AppCompatActivity(), PuzzleEditorListener, AdapterView.OnItemSelectedListener {

    var puzzleType: PuzzleType = PuzzleType.MATH
    var puzzleTypes = arrayOf(PuzzleType.MATH, PuzzleType.MAZE, PuzzleType.REWRITE, PuzzleType.SEQUENCE)
    var puzzleTypeStrs = arrayOf("Ecuación matemática", "Laberinto", "Memoria", "Secuencia")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_puzzle)
        showPuzzleEditor()
        spnPuzzleType.adapter = ArrayAdapter<String>(this, R.layout.custom_spinner_item, puzzleTypeStrs)
        spnPuzzleType.onItemSelectedListener = this
        spnPuzzleType.setSelection(0)
    }

    override fun onPuzzleEdited(puzzle: Puzzle) {
        Log.d("EditAlarm", "Puzzle edited")
    }

    fun showPuzzleEditor(){
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.lytFragment, PuzzleEditFragment(puzzleType))
        ft.commit()
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        puzzleType = puzzleTypes[p2]
        showPuzzleEditor()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) { }


}
