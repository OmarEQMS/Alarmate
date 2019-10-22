package mx.tec.alarmate.Puzzle

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_edit_math_puzzle.swtEasy as swtMathEasy
import kotlinx.android.synthetic.main.fragment_edit_math_puzzle.swtMedium as swtMathMedium
import kotlinx.android.synthetic.main.fragment_edit_math_puzzle.swtHard as swtMathHard
import kotlinx.android.synthetic.main.fragment_edit_maze_puzzle.swtEasy as swtMazeEasy
import kotlinx.android.synthetic.main.fragment_edit_maze_puzzle.swtMedium as swtMazeMedium
import kotlinx.android.synthetic.main.fragment_edit_maze_puzzle.swtHard as swtMazeHard
import kotlinx.android.synthetic.main.fragment_edit_rewrite_puzzle.swtEasy as swtRewriteEasy
import kotlinx.android.synthetic.main.fragment_edit_rewrite_puzzle.swtMedium as swtRewriteMedium
import kotlinx.android.synthetic.main.fragment_edit_rewrite_puzzle.swtHard as swtRewriteHard
import kotlinx.android.synthetic.main.fragment_edit_sequence_puzzle.swtEasy as swtSequenceEasy
import kotlinx.android.synthetic.main.fragment_edit_sequence_puzzle.swtMedium as swtSequenceMedium
import kotlinx.android.synthetic.main.fragment_edit_sequence_puzzle.swtHard as swtSequenceHard
import kotlinx.android.synthetic.main.fragment_edit_scan_puzzle.swtEasy as swtScanEasy
import kotlinx.android.synthetic.main.fragment_edit_scan_puzzle.swtMedium as swtScanMedium
import kotlinx.android.synthetic.main.fragment_edit_scan_puzzle.swtHard as swtScanHard
import mx.tec.alarmate.R

@Suppress("UNREACHABLE_CODE")
open class PuzzleEditFragment(type: PuzzleType) : Fragment() {
    private var listener: PuzzleEditorListener? = null
    var puzzle: Puzzle?
    var type: PuzzleType
    lateinit var swtEasy: Switch
    lateinit var swtMedium: Switch
    lateinit var swtHard: Switch

    // Initialize to default instance of Puzzle
    init {
        puzzle = Puzzle()
        this.type = type
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            puzzle = it.getParcelable<Puzzle>(ARG_PUZZLE)
        }
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
            }
            PuzzleType.MAZE -> {
                swtEasy = swtMazeEasy
                swtMedium = swtMazeMedium
                swtHard = swtMazeHard
            }
            PuzzleType.REWRITE -> {
                swtEasy = swtRewriteEasy
                swtMedium = swtRewriteMedium
                swtHard = swtRewriteHard
            }
            PuzzleType.SEQUENCE -> {
                swtEasy = swtSequenceEasy
                swtMedium = swtSequenceMedium
                swtHard = swtSequenceHard
            }
            PuzzleType.SCAN -> {
                swtEasy = swtScanEasy
                swtMedium = swtScanMedium
                swtHard = swtScanHard
            }
        }
        swtEasy.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                swtMedium.isChecked = false
                swtHard.isChecked = false
            }
        }
        swtMedium.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                swtEasy.isChecked = false
                swtHard.isChecked = false
            }
        }
        swtHard.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                swtEasy.isChecked = false
                swtMedium.isChecked = false
            }
        }
        swtEasy.isChecked = true
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {
        @JvmStatic
        fun newInstance(puzzle: Puzzle) =
            PuzzleFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PUZZLE, puzzle)
                }
            }
    }
}
