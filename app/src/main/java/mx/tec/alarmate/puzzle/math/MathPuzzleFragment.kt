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
import mx.tec.alarmate.puzzle.PuzzleFragment

class MathPuzzleFragment(override var alarm: Alarm, override var puzzle: Puzzle) : PuzzleFragment(alarm, puzzle) {
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
            if(equation.validate(txtResult.text.toString().toInt())){
                stopAlarm()
                listener?.onPuzzleSuccess()
            } else {
                Toast.makeText(context!!, "Inténtalo de nuevo", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun startAlarm() {
        equation = MathEquation(puzzle.difficulty)
        txtEquation.setText(equation.toString())
        super.startAlarm()
    }

}
