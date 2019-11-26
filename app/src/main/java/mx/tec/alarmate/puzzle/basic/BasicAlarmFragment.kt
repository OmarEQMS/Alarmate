package mx.tec.alarmate.puzzle.basic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_basic_alarm.*
import mx.tec.alarmate.R
import mx.tec.alarmate.db.model.Alarm
import mx.tec.alarmate.db.model.Puzzle
import mx.tec.alarmate.puzzle.PuzzleFragment
import java.lang.Error

class BasicAlarmFragment(override var alarm: Alarm, override var puzzle: Puzzle?) : PuzzleFragment(alarm, puzzle) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_basic_alarm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtAlarmName.setText(alarm.name)
        btnSuccess.setOnClickListener {
            stopAlarm()
            listener?.onPuzzleSuccess()
        }
    }

    override fun stopAlarm(){
        super.stopAlarm()
    }

}
