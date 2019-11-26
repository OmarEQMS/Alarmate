package mx.tec.alarmate.util

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*
import mx.tec.alarmate.R

class AlarmTimePicker : DialogFragment(), TimePickerDialog.OnTimeSetListener {
    interface Listener {
        fun onTimeSet(hourOfDay: Int, minute: Int)
    }

    var listener: Listener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(activity, R.style.AlarmTimePickerTheme, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        if(listener != null){
            listener!!.onTimeSet(hourOfDay, minute)
        } else {
            Log.d(TimePicker::class.java.simpleName, "No listener to notify time set, hour:${hourOfDay}, minute: ${minute}")
        }
    }
}