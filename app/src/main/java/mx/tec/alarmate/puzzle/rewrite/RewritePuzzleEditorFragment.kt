package mx.tec.alarmate.puzzle.rewrite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_edit_rewrite_puzzle.*
import mx.tec.alarmate.puzzle.PuzzleEditorFragment
import mx.tec.alarmate.R
import mx.tec.alarmate.alarm.AlarmActivity
import mx.tec.alarmate.alarm.AlarmReceiver.Companion.ARG_ALARM
import mx.tec.alarmate.db.model.Alarm
import mx.tec.alarmate.puzzle.ARG_PUZZLE

class RewritePuzzleEditorFragment : PuzzleEditorFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_math_puzzle, container, false)
    }

}
