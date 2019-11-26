package mx.tec.alarmate

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.view.isGone
import kotlinx.android.synthetic.main.activity_edit_alarm.*
import kotlinx.android.synthetic.main.activity_list_alarms_content.*
import mx.tec.alarmate.alarm.AlarmActivity
import mx.tec.alarmate.alarm.AlarmReceiver
import mx.tec.alarmate.db.model.Alarm
import mx.tec.alarmate.db.model.Puzzle
import mx.tec.alarmate.db.util.AppDatabase
import mx.tec.alarmate.puzzle.*
import mx.tec.alarmate.util.AlarmTimePicker

class EditAlarm : AppCompatActivity(), AlarmTimePicker.Listener, AdapterView.OnItemSelectedListener  {

    data class Interval(val interval: Long, val name: String)
    var idAlarm: Long = 0
    var hourOfDay: Int = 0
    var minute: Int = 0
    lateinit var puzzles: Array<Puzzle>
    val TUNE_REQUEST = 1
    var uri: Uri? = Uri.EMPTY
    var intervals = arrayListOf(Interval(INTERVAL_TIME_SLOW, "Lento"), Interval(INTERVAL_TIME_NORMAL, "Normal"), Interval(INTERVAL_TIME_FAST, "Rápido"))
    var selectedInterval = intervals[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_alarm)

        idAlarm = intent.getLongExtra("idAlarm", 0)
        btnAddPuzzle.setOnClickListener {
            val intent = Intent(this, EditPuzzle::class.java)
            intent.putExtra(ARG_PUZZLE_ALARM_ID, idAlarm.toInt())
            startActivity(intent)
        }
        btnAlarmSong.setOnClickListener {
            val intent_upload = Intent()
            intent_upload.type = "audio/*"
            intent_upload.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent_upload, TUNE_REQUEST)
        }
        txtAlarmTime.setOnClickListener {
            val timePicker = AlarmTimePicker()
            timePicker.listener = this
            timePicker.show(supportFragmentManager, "timePicker")
        }

        if(idAlarm == 0L){
            btnAddPuzzle.isGone = true
        }
        lvAlarmPuzzles.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val puzzle = parent.adapter.getItem(position) as Puzzle
                val intent = Intent(this@EditAlarm, EditPuzzle::class.java)
                intent.putExtra(ARG_PUZZLE_ALARM_ID, idAlarm.toInt())
                intent.putExtra(ARG_PUZZLE, puzzle)
                startActivity(intent)
            }
        }
        txtAlarmTime.setText(getAlarmTime())
        spnAlarmDuration.adapter = ArrayAdapter<String>(this, R.layout.custom_spinner_item, getIntervalStrings())
        spnAlarmDuration.onItemSelectedListener = this


        val db = AppDatabase.getInstance(this)
        if(idAlarm.toInt() == 0) btnDeleteAlarm.visibility = View.INVISIBLE
        else {
            Thread {
                val alarma = db.alarmDao().getAlarm(idAlarm.toInt())
                txtAlarmName.setText(alarma.name)
                txtAlarmTime.setText(alarma.hour)
                radAlarmMonday.isChecked = alarma.monday
                radAlarmTuesday.isChecked = alarma.tuesday
                radAlarmWednesday.isChecked = alarma.wednesday
                radAlarmThursday.isChecked = alarma.thursday
                radAlarmFriday.isChecked = alarma.friday
                radAlarmSaturday.isChecked = alarma.saturday
                radAlarmSunday.isChecked = alarma.sunday
                this.runOnUiThread {
                    swtOnOffAlarm.isChecked = alarma.active
                    swtAlarmFlash.isChecked = alarma.flash
                    swtAlarmVibration.isChecked = alarma.vibration
                }

                // Get alarm puzzles
                puzzles = db.puzzleDao().listAlarmPuzzles(idAlarm.toInt()).toTypedArray()
                this@EditAlarm.runOnUiThread {
                    lvAlarmPuzzles.adapter = PuzzleAdapter(this, R.layout.custom_puzzle_item, puzzles.toList())
                    var totalHeight = 0
                    for (i in 0 until lvAlarmPuzzles.count) {
                        val listItem = lvAlarmPuzzles.adapter.getView(i, null, lvAlarmPuzzles)
                        listItem.measure(0, 0)
                        totalHeight += listItem.measuredHeight
                    }

                    val params = lvAlarmPuzzles.layoutParams
                    params.height = totalHeight + lvAlarmPuzzles.dividerHeight* (lvAlarmPuzzles!!.count - 1)
                    lvAlarmPuzzles.layoutParams = params
                    lvAlarmPuzzles.requestLayout()
                }
            }.start()
        }
    }

    override fun onTimeSet(hourOfDay: Int, minute: Int) {
        this.hourOfDay = hourOfDay
        this.minute = minute
        txtAlarmTime.setText(getAlarmTime())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == TUNE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                onTuneSelected(data!!.data)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(p0: AdapterView<*>?, v: View?, index: Int, id: Long) {
        selectedInterval = intervals[index]
    }

    fun getIntervalStrings(): ArrayList<String>{
        val strs = arrayListOf<String>()
        for(i in 0 until intervals.size){
            strs.add(intervals[i].name)
        }
        return strs
    }

    fun onTuneSelected(uri: Uri?){
        Log.d("EditAlarm", "Selected uri")
        this.uri = uri
    }

    fun SaveAlarm(v: View){
        val alarm = Alarm(idAlarm, txtAlarmName.text.toString(), swtOnOffAlarm.isChecked, txtAlarmTime.text.toString(), radAlarmMonday.isChecked, radAlarmTuesday.isChecked, radAlarmWednesday.isChecked, radAlarmThursday.isChecked, radAlarmFriday.isChecked, radAlarmSaturday.isChecked, radAlarmSunday.isChecked, swtAlarmVibration.isChecked, swtAlarmFlash.isChecked, uri.toString(), selectedInterval.interval)
        val db = AppDatabase.getInstance(this)
        Thread{
            if(idAlarm.toInt()==0)
                db.alarmDao().createAlarm(alarm)
            else
                db.alarmDao().updateAlarm(alarm)

            alarm.registerNextAlarm(this)
            //Toast.makeText(this@EditAlarm, "Se guardo correctamente", Toast.LENGTH_LONG).show()
            finish()
        }.start()
    }

    fun DeleteAlarm(v: View){
        val alarm = Alarm(idAlarm, txtAlarmName.text.toString(), swtOnOffAlarm.isChecked, txtAlarmTime.text.toString(), radAlarmMonday.isChecked, radAlarmTuesday.isChecked, radAlarmWednesday.isChecked, radAlarmThursday.isChecked, radAlarmFriday.isChecked, radAlarmSaturday.isChecked, radAlarmSunday.isChecked, swtAlarmVibration.isChecked, swtAlarmFlash.isChecked, uri.toString(), selectedInterval.interval)
        val db = AppDatabase.getInstance(this)
        Thread {
            db.alarmDao().deleteAlarm(alarm)
            //Toast.makeText(this@EditAlarm, "Se elimino correctamente", Toast.LENGTH_LONG).show()
            finish()
        }.start()
    }

    fun getAlarmTime(): String {
        var hourOfDayStr = appendLeadingZero(hourOfDay.toString())
        var minutStr = appendLeadingZero(minute.toString())
        return "${hourOfDayStr}:${minutStr}"
    }

    fun appendLeadingZero(str: String): String {
        return if (str.length == 1) "0${str}" else str
    }
}
