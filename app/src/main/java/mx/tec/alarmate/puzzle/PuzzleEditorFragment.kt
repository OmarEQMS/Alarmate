package mx.tec.alarmate.puzzle

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import mx.tec.alarmate.db.model.Puzzle

open class PuzzleEditorFragment : Fragment() {
    private var listener: PuzzleEditorListener? = null
    var puzzle: Puzzle?

    // Initialize to default instance of Puzzle
    init {
        puzzle = Puzzle()
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
