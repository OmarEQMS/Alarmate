package mx.tec.alarmate.db.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import mx.tec.alarmate.R
import mx.tec.alarmate.db.model.Alarm

class AlarmAdapter(private val context: Context, private val layout: Int, private val dataSource: List<Alarm>): BaseAdapter() {

    //Un servicio del main para inflar mi xml
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private class ViewHolder {
        lateinit var txtAlarmHour: TextView
        lateinit var txtAlarmDays: TextView
        lateinit var btnAlarmPuzzle: TextView
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View
        val holder : ViewHolder

        if(convertView == null){
            view = inflater.inflate(layout, parent, false)
            holder = ViewHolder()
            holder.txtAlarmHour = view.findViewById<TextView>(R.id.txtAlarmHour)
            holder.txtAlarmDays = view.findViewById<TextView>(R.id.txtAlarmDays)
            holder.btnAlarmPuzzle = view.findViewById<TextView>(R.id.btnAlarmPuzzle)
            view.tag = holder
        }else{
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val alarm = getItem(position) as Alarm
        var days: String = ""
        days += if (alarm.monday) "L " else "  "
        days += if (alarm.tuesday) "M " else "  "
        days += if (alarm.wednesday) "M " else "  "
        days += if (alarm.thursday) "J " else "  "
        days += if (alarm.friday) "V " else "  "
        days += if (alarm.sunday) "S " else "  "
        days += if (alarm.saturday) "D" else "  "
        holder.txtAlarmHour.text = alarm.hour.toString()
        holder.txtAlarmDays.text = days
        holder.btnAlarmPuzzle.text = ""

        return view
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return dataSource[position].idAlarm
    }

    override fun getCount(): Int {
        return dataSource.size
    }
}