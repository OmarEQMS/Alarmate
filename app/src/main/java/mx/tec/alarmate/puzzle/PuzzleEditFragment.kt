package mx.tec.alarmate.puzzle

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_edit_math_puzzle.swtEasy as swtMathEasy
import kotlinx.android.synthetic.main.fragment_edit_math_puzzle.swtMedium as swtMathMedium
import kotlinx.android.synthetic.main.fragment_edit_math_puzzle.swtHard as swtMathHard
import kotlinx.android.synthetic.main.fragment_edit_math_puzzle.btnSave as btnMathSave
import kotlinx.android.synthetic.main.fragment_edit_math_puzzle.btnDelete as btnMathDelete

import kotlinx.android.synthetic.main.fragment_edit_maze_puzzle.swtEasy as swtMazeEasy
import kotlinx.android.synthetic.main.fragment_edit_maze_puzzle.swtMedium as swtMazeMedium
import kotlinx.android.synthetic.main.fragment_edit_maze_puzzle.swtHard as swtMazeHard
import kotlinx.android.synthetic.main.fragment_edit_maze_puzzle.btnSave as btnMazeSave
import kotlinx.android.synthetic.main.fragment_edit_maze_puzzle.btnDelete as btnMazeDelete

import kotlinx.android.synthetic.main.fragment_edit_rewrite_puzzle.swtEasy as swtRewriteEasy
import kotlinx.android.synthetic.main.fragment_edit_rewrite_puzzle.swtMedium as swtRewriteMedium
import kotlinx.android.synthetic.main.fragment_edit_rewrite_puzzle.swtHard as swtRewriteHard
import kotlinx.android.synthetic.main.fragment_edit_rewrite_puzzle.btnSave as btnRewriteSave
import kotlinx.android.synthetic.main.fragment_edit_rewrite_puzzle.btnDelete as btnRewriteDelete

import kotlinx.android.synthetic.main.fragment_edit_sequence_puzzle.swtEasy as swtSequenceEasy
import kotlinx.android.synthetic.main.fragment_edit_sequence_puzzle.swtMedium as swtSequenceMedium
import kotlinx.android.synthetic.main.fragment_edit_sequence_puzzle.swtHard as swtSequenceHard
import kotlinx.android.synthetic.main.fragment_edit_sequence_puzzle.btnSave as btnSequenceSave
import kotlinx.android.synthetic.main.fragment_edit_sequence_puzzle.btnDelete as btnSequenceDelete

import kotlinx.android.synthetic.main.fragment_edit_scan_puzzle.swtEasy as swtScanEasy
import kotlinx.android.synthetic.main.fragment_edit_scan_puzzle.swtMedium as swtScanMedium
import kotlinx.android.synthetic.main.fragment_edit_scan_puzzle.swtHard as swtScanHard
import kotlinx.android.synthetic.main.fragment_edit_scan_puzzle.btnSave as btnScanSave
import kotlinx.android.synthetic.main.fragment_edit_scan_puzzle.btnDelete as btnScanDelete
import mx.tec.alarmate.R
import mx.tec.alarmate.db.model.Puzzle

open class PuzzleEditFragment(puzzle: Puzzle?, val idAlarm: Int, val type: PuzzleType, val isNew: Boolean?) : Fragment() {
    private var listener: PuzzleEditorListener? = null
    var puzzle: Puzzle
    lateinit var swtEasy: Switch
    lateinit var swtMedium: Switch
    lateinit var swtHard: Switch
    lateinit var btnSave: Button
    lateinit var btnDelete: Button

    // Initialize to default instance of Puzzle
    init {
        if(puzzle != null){
            this.puzzle = puzzle
        } else {
            this.puzzle = Puzzle()
        }
        this.puzzle.type = type
        this.puzzle.fkAlarm = idAlarm
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is PuzzleEditorListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        when(this.type){
            PuzzleType.MATH -> {
                return inflater.inflate(R.layout.fragment_edit_math_puzzle, container, false)
            }
            PuzzleType.MAZE -> {
                return inflater.inflate(R.layout.fragment_edit_maze_puzzle, container, false)
            }
            PuzzleType.REWRITE -> {
                return inflater.inflate(R.layout.fragment_edit_rewrite_puzzle, container, false)
            }
            PuzzleType.SEQUENCE -> {
                return inflater.inflate(R.layout.fragment_edit_sequence_puzzle, container, false)
            }
            PuzzleType.SCAN -> {
                return inflater.inflate(R.layout.fragment_edit_scan_puzzle, container, false)
            }
            else -> throw RuntimeException("must provide a valid type when creating")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        when(this.type){
            PuzzleType.MATH -> {
                swtEasy = swtMathEasy
                swtMedium = swtMathMedium
                swtHard = swtMathHard
                btnSave = btnMathSave
                btnDelete = btnMathDelete
            }
            PuzzleType.MAZE -> {
                swtEasy = swtMazeEasy
                swtMedium = swtMazeMedium
                swtHard = swtMazeHard
                btnSave = btnMazeSave
                btnDelete = btnMazeDelete
            }
            PuzzleType.REWRITE -> {
                swtEasy = swtRewriteEasy
                swtMedium = swtRewriteMedium
                swtHard = swtRewriteHard
                btnSave = btnRewriteSave
                btnDelete = btnRewriteDelete
            }
            PuzzleType.SEQUENCE -> {
                swtEasy = swtSequenceEasy
                swtMedium = swtSequenceMedium
                swtHard = swtSequenceHard
                btnSave = btnSequenceSave
                btnDelete = btnSequenceDelete
            }
            PuzzleType.SCAN -> {
                swtEasy = swtScanEasy
                swtMedium = swtScanMedium
                swtHard = swtScanHard
                btnSave = btnScanSave
                btnDelete = btnScanDelete
            }
        }
        swtEasy.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                swtMedium.isChecked = false
                swtHard.isChecked = false
                puzzle?.difficulty = PuzzleDifficulty.EASY
            }
        }
        swtMedium.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                swtEasy.isChecked = false
                swtHard.isChecked = false
                puzzle?.difficulty = PuzzleDifficulty.MEDIUM
            }
        }
        swtHard.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                swtEasy.isChecked = false
                swtMedium.isChecked = false
                puzzle?.difficulty = PuzzleDifficulty.HARD
            }
        }
        btnSave.setOnClickListener {
            listener?.onPuzzleEdited(puzzle)
        }
        btnDelete.setOnClickListener {
            listener?.onPuzzleDeleted(puzzle)
        }
        if(isNew != null && isNew){
            btnDelete.isGone = true
            swtEasy.isChecked = true
        } else{
            when(puzzle.difficulty){
                PuzzleDifficulty.EASY -> swtEasy.isChecked = true
                PuzzleDifficulty.MEDIUM -> swtMedium.isChecked = true
                PuzzleDifficulty.HARD -> swtHard.isChecked = true
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {
        @JvmStatic
        fun newInstance(puzzle: Puzzle) =
            PuzzleEditorFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PUZZLE, puzzle)
                }
            }
    }
}
