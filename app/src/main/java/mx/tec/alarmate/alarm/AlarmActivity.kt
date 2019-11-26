package mx.tec.alarmate.alarm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_alarm.*
import mx.tec.alarmate.R
import mx.tec.alarmate.db.model.Alarm
import mx.tec.alarmate.puzzle.PuzzleListener
import mx.tec.alarmate.puzzle.basic.BasicAlarmFragment

class AlarmActivity : AppCompatActivity(), PuzzleListener {

    lateinit var alarm: Alarm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        val alarm = intent.getParcelableExtra<Alarm>(AlarmReceiver.ARG_ALARM)
        if(alarm != null){
            this.alarm = alarm
            Log.d(AlarmActivity::class.java.simpleName, "Alarm was triggered with id: ${alarm.idAlarm}, name: ${alarm.name}")

            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.lytFragment, BasicAlarmFragment(alarm, null))
            ft.commit()

        }else{
            Log.e(AlarmActivity::class.java.simpleName, "Alarm is not stored in database")
            finish()
        }
    }

    override fun onPuzzleSuccess() {
        Toast.makeText(this, "La alarma se ha detenido", Toast.LENGTH_LONG).show()
    }

    override fun onPuzzleFail() {
        Toast.makeText(this, "La alarma ha fallado", Toast.LENGTH_LONG).show()
    }
}
