package mx.tec.alarmate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_edit_alarm.*
import mx.tec.alarmate.Puzzle.MathPuzzle
import mx.tec.alarmate.Puzzle.Puzzle
import mx.tec.alarmate.Puzzle.PuzzleEditorListener

class EditAlarm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_alarm)

        btnAddPuzzle.setOnClickListener {
            var intent = Intent(this, EditPuzzle::class.java)
            startActivity(intent)
        }
    }
}
