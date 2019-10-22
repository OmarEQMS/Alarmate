package mx.tec.alarmate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edit_alarm.*
import mx.tec.alarmate.db.model.Alarm
import mx.tec.alarmate.db.util.AppDatabase

class EditAlarm : AppCompatActivity() {

    var idAlarm: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_alarm)

        idAlarm = intent.getLongExtra("idAlarm", 0)
        val db = AppDatabase.getInstance(this)

        if(idAlarm.toInt() == 0) btnDeleteAlarm.visibility = View.INVISIBLE
        else {
            Thread {
                var alarma = db.alarmDao().getAlarm(idAlarm.toInt())
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
            var intent = Intent(this@EditAlarm, ListAlarms::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent);
        }.start()
    }

    fun DeleteAlarm(v: View){
        var alarm = Alarm(idAlarm, txtAlarmName.text.toString(), swtOnOffAlarm.isActivated, txtAlarmTime.text.toString(), radAlarmMonday.isChecked, radAlarmTuesday.isChecked, radAlarmWednesday.isChecked, radAlarmThursday.isChecked, radAlarmFriday.isChecked, radAlarmSaturday.isChecked, radAlarmSunday.isChecked, swtAlarmVibration.isActivated, swtAlarmFlash.isActivated)
        val db = AppDatabase.getInstance(this)
        Thread {
            db.alarmDao().deleteAlarm(alarm)
            //Toast.makeText(this@EditAlarm, "Se elimino correctamente", Toast.LENGTH_LONG).show()
            var intent = Intent(this@EditAlarm, ListAlarms::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent);
        }.start()
    }
}
