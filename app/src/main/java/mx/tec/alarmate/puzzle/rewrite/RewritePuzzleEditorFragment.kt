package mx.tec.alarmate.puzzle.rewrite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mx.tec.alarmate.puzzle.PuzzleEditorFragment
import mx.tec.alarmate.R

class RewritePuzzleEditorFragment : PuzzleEditorFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_math_puzzle, container, false)
    }

}
