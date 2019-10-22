package mx.tec.alarmate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.view.isGone
import kotlinx.android.synthetic.main.activity_edit_alarm.*
import kotlinx.android.synthetic.main.activity_list_alarms_content.*
import mx.tec.alarmate.db.model.Alarm
import mx.tec.alarmate.db.model.Puzzle
import mx.tec.alarmate.db.util.AppDatabase
import mx.tec.alarmate.puzzle.ARG_PUZZLE
import mx.tec.alarmate.puzzle.ARG_PUZZLE_ALARM_ID
import mx.tec.alarmate.puzzle.PuzzleAdapter

class EditAlarm : AppCompatActivity() {
    var idAlarm: Long = 0
    lateinit var puzzles: Array<Puzzle>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_alarm)

        idAlarm = intent.getLongExtra("idAlarm", 0)
        btnAddPuzzle.setOnClickListener {
            val intent = Intent(this, EditPuzzle::class.java)
            intent.putExtra(ARG_PUZZLE_ALARM_ID, idAlarm.toInt())
            startActivity(intent)
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
    }

    override fun onResume() {
        super.onResume()
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
                swtOnOffAlarm.isActivated = alarma.active
                swtAlarmFlash.isActivated = alarma.flash
                swtAlarmVibration.isActivated = alarma.vibration

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

    fun SaveAlarm(v: View){
        var alarm = Alarm(idAlarm, txtAlarmName.text.toString(), swtOnOffAlarm.isActivated, txtAlarmTime.text.toString(), radAlarmMonday.isChecked, radAlarmTuesday.isChecked, radAlarmWednesday.isChecked, radAlarmThursday.isChecked, radAlarmFriday.isChecked, radAlarmSaturday.isChecked, radAlarmSunday.isChecked, swtAlarmVibration.isActivated, swtAlarmFlash.isActivated)
        val db = AppDatabase.getInstance(this)
        Thread{
            if(idAlarm.toInt()==0)
                db.alarmDao().createAlarm(alarm)
            else
                db.alarmDao().updateAlarm(alarm)
            //Toast.makeText(this@EditAlarm, "Se guardo correctamente", Toast.LENGTH_LONG).show()
            finish()
        }.start()
    }

    fun DeleteAlarm(v: View){
        var alarm = Alarm(idAlarm, txtAlarmName.text.toString(), swtOnOffAlarm.isActivated, txtAlarmTime.text.toString(), radAlarmMonday.isChecked, radAlarmTuesday.isChecked, radAlarmWednesday.isChecked, radAlarmThursday.isChecked, radAlarmFriday.isChecked, radAlarmSaturday.isChecked, radAlarmSunday.isChecked, swtAlarmVibration.isActivated, swtAlarmFlash.isActivated)
        val db = AppDatabase.getInstance(this)
        Thread {
            db.alarmDao().deleteAlarm(alarm)
            //Toast.makeText(this@EditAlarm, "Se elimino correctamente", Toast.LENGTH_LONG).show()
            finish()
        }.start()
    }
}
