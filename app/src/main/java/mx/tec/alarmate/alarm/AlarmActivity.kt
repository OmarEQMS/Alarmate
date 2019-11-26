package mx.tec.alarmate.alarm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import mx.tec.alarmate.R
import mx.tec.alarmate.db.model.Alarm
import mx.tec.alarmate.db.model.Puzzle
import mx.tec.alarmate.db.util.AppDatabase
import mx.tec.alarmate.puzzle.PuzzleFragment
import mx.tec.alarmate.puzzle.PuzzleListener
import mx.tec.alarmate.puzzle.PuzzleType
import mx.tec.alarmate.puzzle.basic.BasicPuzzleFragment
import mx.tec.alarmate.puzzle.math.MathPuzzleFragment
import mx.tec.alarmate.puzzle.rewrite.RewritePuzzleFragment
import mx.tec.alarmate.puzzle.sequence.SequencePuzzleFragment

class AlarmActivity : AppCompatActivity(), PuzzleListener {

    lateinit var alarm: Alarm
    lateinit var puzzles: List<Puzzle?>
    var puzzleIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        val alarm = intent.getParcelableExtra<Alarm>(AlarmReceiver.ARG_ALARM)
        if(alarm != null){
            this.alarm = alarm
            Log.d(AlarmActivity::class.java.simpleName, "Alarm was triggered with id: ${alarm.idAlarm}, name: ${alarm.name}")

            Thread{
                val db = AppDatabase.getInstance(this)
                puzzles = db.puzzleDao().listAlarmPuzzles(this.alarm.idAlarm.toInt())
                if(puzzles.size == 0){
                    puzzles = listOf(null)
                }
                triggerPuzzles()
            }.start()

        }else{
            Log.e(AlarmActivity::class.java.simpleName, "Alarm is not stored in database")
            finish()
        }
    }

    override fun onPuzzleSuccess() {
        Toast.makeText(this, "La alarma se ha detenido", Toast.LENGTH_LONG).show()
        puzzleIndex++
        triggerPuzzles()
    }

    override fun onPuzzleFail() {
        Toast.makeText(this, "La alarma ha fallado", Toast.LENGTH_LONG).show()
        triggerPuzzles()
    }

    fun triggerPuzzles(){
        // Alarm is done register next alarm to be triggered
        if(puzzleIndex >= puzzles.size){
            finish()
            // TODO(abrahamtorres): Register next alarm
//            alarm.registerNextAlarm(this)
            return
        }
        this.runOnUiThread{
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.lytFragment, getPuzzleFragment(puzzles[puzzleIndex]))
            ft.commit()
        }
    }

    fun getPuzzleFragment(puzzle:Puzzle?): PuzzleFragment{
        when(puzzle?.type){
            PuzzleType.MATH -> {
                return MathPuzzleFragment(alarm, puzzle)
            }
            PuzzleType.MAZE -> {
//                return BasicPuzzleFragment(alarm, puzzle)
            }
            PuzzleType.SCAN -> {
//                return BasicPuzzleFragment(alarm, puzzle)
            }
            PuzzleType.SEQUENCE -> {
                return SequencePuzzleFragment(alarm, puzzle)
            }
            PuzzleType.REWRITE-> {
                return RewritePuzzleFragment(alarm, puzzle)
            }
        }
        return BasicPuzzleFragment(alarm, puzzle)
    }
}
