package mx.tec.alarmate.Puzzle.Maze

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mx.tec.alarmate.Puzzle.PuzzleFragment
import mx.tec.alarmate.R

class MazePuzzleFragment : PuzzleFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_maze_puzzle, container, false)
    }

}
