package mx.tec.alarmate.puzzle.math

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_alarm_basic.*
import kotlinx.android.synthetic.main.fragment_alarm_basic.btnSuccess
import kotlinx.android.synthetic.main.fragment_alarm_basic.txtAlarmName
import kotlinx.android.synthetic.main.fragment_alarm_math.*
import mx.tec.alarmate.R
import mx.tec.alarmate.db.model.Alarm
import mx.tec.alarmate.db.model.Puzzle
import mx.tec.alarmate.puzzle.PuzzleDifficulty
import mx.tec.alarmate.puzzle.PuzzleFragment

class MathPuzzleFragment(override var alarm: Alarm, override var puzzle: Puzzle) : PuzzleFragment(alarm, puzzle) {
    data class DifficultyPolicy(val difficulty: PuzzleDifficulty, val validOperands: ArrayList<Operand>, val numberOfLiteralsRange: Pair<Int, Int>, val literalsRange: Pair<Int, Int>)

    lateinit var equation: MathEquation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alarm_math, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtAlarmName.setText(alarm.name)
        btnSuccess.setOnClickListener {
            approveStop()
        }

        txtResult.setOnEditorActionListener { textView, i, keyEvent ->
            if(approveStop()){
                true
            } else {
                false
            }
        }
    }

    fun approveStop(): Boolean{
        if(equation.validate(txtResult.text.toString().toInt())){
            stopAlarm()
            listener?.onPuzzleSuccess()
            return true
        } else {
            Toast.makeText(context!!, "Inténtalo de nuevo", Toast.LENGTH_LONG).show()
            return false
        }
    }

    override fun startAlarm() {
        equation = MathEquation(puzzle.difficulty)
        txtEquation.setText(equation.toString())
        super.startAlarm()
    }

    companion object{
        val EASY_POLICY = MathPuzzleFragment.DifficultyPolicy(
            PuzzleDifficulty.EASY,
            arrayListOf(Operand.PLUS),
            Pair(2,2),
            Pair(5,15)
        )
        val MEDIUM_POLICY = MathPuzzleFragment.DifficultyPolicy(
            PuzzleDifficulty.MEDIUM,
            arrayListOf(Operand.PLUS, Operand.MULTIPLY),
            Pair(2,3),
            Pair(5,20)
        )
        val HARD_POLICY = MathPuzzleFragment.DifficultyPolicy(
            PuzzleDifficulty.HARD,
            arrayListOf(Operand.PLUS, Operand.MULTIPLY, Operand.DIVIDE),
            Pair(3, 4),
            Pair(5, 40)
        )
    }
}
