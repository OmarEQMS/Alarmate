package mx.tec.alarmate.puzzle

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import mx.tec.alarmate.R
import mx.tec.alarmate.db.model.Puzzle

class PuzzleAdapter(private val context: Context, private val layout: Int, private val dataSource: List<Puzzle>): BaseAdapter() {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private class ViewHolder {
        lateinit var txtType: TextView
        lateinit var txtDifficulty: TextView
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View
        val holder : ViewHolder

        if(convertView == null){
            view = inflater.inflate(layout, parent, false)
            holder = ViewHolder()
            holder.txtType = view.findViewById<TextView>(R.id.txtType)
            holder.txtDifficulty = view.findViewById<TextView>(R.id.txtDifficulty)
            view.tag = holder
        }else{
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val puzzle = getItem(position) as Puzzle
        holder.txtDifficulty.text = puzzle.difficulty.name
        holder.txtType.text = puzzle.type.name

        return view
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return dataSource[position].idPuzzle
    }

    override fun getCount(): Int {
        return dataSource.size
    }
}