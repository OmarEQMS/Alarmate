package mx.tec.alarmate.puzzle.sequence

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_alarm_basic.*
import kotlinx.android.synthetic.main.fragment_alarm_basic.btnSuccess
import kotlinx.android.synthetic.main.fragment_alarm_basic.txtAlarmName
import kotlinx.android.synthetic.main.fragment_alarm_math.*
import kotlinx.android.synthetic.main.fragment_alarm_math.txtResult
import kotlinx.android.synthetic.main.fragment_alarm_rewrite.*
import mx.tec.alarmate.R
import mx.tec.alarmate.db.model.Alarm
import mx.tec.alarmate.db.model.Puzzle
import mx.tec.alarmate.puzzle.PuzzleDifficulty
import mx.tec.alarmate.puzzle.PuzzleFragment
import mx.tec.alarmate.puzzle.math.Operand
import java.util.*
import kotlin.collections.ArrayList

class SequencePuzzleFragment(override var alarm: Alarm, override var puzzle: Puzzle) : PuzzleFragment(alarm, puzzle) {
    data class DifficultyPolicy(val difficulty: PuzzleDifficulty, val alphabet: ArrayList<Char>, val uppercase: Boolean, val digits: Boolean, val size: Int)

    lateinit var password: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alarm_rewrite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtAlarmName.setText(alarm.name)
        btnSuccess.setOnClickListener {
            if(password == txtResult.text.toString()){
                stopAlarm()
                listener?.onPuzzleSuccess()
            } else {
                Toast.makeText(context!!, "Inténtalo de nuevo", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun startAlarm() {
        password = generatePassword()
        txtPassword.setText(password)
        super.startAlarm()
    }

    fun generatePassword(): String{
        val policy: DifficultyPolicy

        when(puzzle.difficulty){
            PuzzleDifficulty.EASY -> {
                policy = EASY_POLICY
            }
            PuzzleDifficulty.MEDIUM -> {
                policy = MEDIUM_POLICY
            }
            PuzzleDifficulty.HARD -> {
                policy = HARD_POLICY
            }
            else -> {
                policy = EASY_POLICY
            }
        }

        if(policy.uppercase){
            val currentSize = policy.alphabet.size
            for(i in 0 until currentSize){
                if(policy.alphabet[i].isLetter() && policy.alphabet[i].isLowerCase()){
                    policy.alphabet.add(policy.alphabet[i].toUpperCase())
                }
            }
        }

        if(policy.digits){
            for(i in 0 until 10){
                policy.alphabet.add(i.toString()[0])
            }
        }

        var password = ""
        val r = Random()
        for(i in 0 until policy.size){
            password += policy.alphabet[r.nextInt(policy.alphabet.size)]
        }
        return password
    }

    companion object{
        val EASY_POLICY = DifficultyPolicy(
            PuzzleDifficulty.EASY,
            arrayListOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'),
            false,
            true,
            5
        )
        val MEDIUM_POLICY = DifficultyPolicy(
            PuzzleDifficulty.MEDIUM,
            arrayListOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'),
            true,
            true,
            7
        )
        val HARD_POLICY = DifficultyPolicy(
            PuzzleDifficulty.HARD,
            arrayListOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '_', '-', '*', '=', '+', '@', '&'),
            true,
            true,
            10
        )
    }
}
