package mx.tec.alarmate

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_edit_puzzle.*
import mx.tec.alarmate.db.model.Puzzle
import mx.tec.alarmate.db.util.AppDatabase
import mx.tec.alarmate.puzzle.*

class EditPuzzle : AppCompatActivity(), PuzzleEditorListener, AdapterView.OnItemSelectedListener {

    enum class Operation { CREATE, UPDATE, DELETE }

    var idAlarm: Int = 0
    var isNew = true
    var puzzle: Puzzle? = null
    var puzzleType: PuzzleType = PuzzleType.MATH
    var puzzleTypes = arrayOf(PuzzleType.MATH, PuzzleType.MAZE, PuzzleType.REWRITE, PuzzleType.SEQUENCE)
    var puzzleTypeStrs = arrayOf("Ecuación matemática", "Laberinto", "Memoria", "Secuencia")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_puzzle)
        showPuzzleEditor()
        spnPuzzleType.adapter = ArrayAdapter<String>(this, R.layout.custom_spinner_item, puzzleTypeStrs)
        spnPuzzleType.onItemSelectedListener = this
        idAlarm = intent.getIntExtra(ARG_PUZZLE_ALARM_ID, 0)
        puzzle = intent.getParcelableExtra(ARG_PUZZLE)
        isNew = puzzle == null
        if(idAlarm == 0){
            finish()
        }
        if(isNew){
            spnPuzzleType.setSelection(0)
        }else{
            spnPuzzleType.setSelection(puzzleTypes.indexOf(puzzle?.type))
        }
    }

    override fun onPuzzleEdited(puzzle: Puzzle?) {
        if(puzzle != null){
            if(isNew){
                editPuzzle(puzzle, Operation.CREATE)
            }else{
                editPuzzle(puzzle, Operation.UPDATE)
            }
        }
    }

    override fun onPuzzleDeleted(puzzle: Puzzle?) {
        if(puzzle != null) {
            editPuzzle(puzzle, Operation.DELETE)
        }
    }

    fun showPuzzleEditor(){
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.lytFragment, PuzzleEditFragment(puzzle, idAlarm, puzzleType, isNew))
        ft.commit()
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        puzzleType = puzzleTypes[p2]
        showPuzzleEditor()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) { }

    fun editPuzzle(puzzle: Puzzle, operation: Operation){
        val db = AppDatabase.getInstance(this)
        Log.d("EditPuzzle", puzzle.toString())
        Thread{
            when(operation){
                Operation.CREATE -> {
                    Log.d("EditPuzzle", "Creating puzzle")
                    db.puzzleDao().createPuzzle(puzzle)
                }
                Operation.UPDATE -> {
                    Log.d("EditPuzzle", "Updating puzzle")
                    db.puzzleDao().updatePuzzle(puzzle)
                }
                Operation.DELETE -> {
                    Log.d("EditPuzzle", "Deleting puzzle")
                    db.puzzleDao().deletePuzzle(puzzle)
                }
            }
            finish()
        }.start()
    }

}
