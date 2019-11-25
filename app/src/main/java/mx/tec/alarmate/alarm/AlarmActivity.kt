package mx.tec.alarmate.alarm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_alarm.*
import mx.tec.alarmate.R
import mx.tec.alarmate.db.model.Alarm

class AlarmActivity : AppCompatActivity() {
    lateinit var alarm: Alarm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        val alarm = intent.getParcelableExtra<Alarm>(AlarmReceiver.ARG_ALARM)
        if(alarm != null){
            this.alarm = alarm
            Log.d(AlarmActivity::class.java.simpleName, "Alarm was triggered with id: ${alarm.idAlarm}, name: ${alarm.name}")

            textView2.setText(alarm.name)
        }else{
            Log.e(AlarmActivity::class.java.simpleName, "Alarm is not stored in database")
            finish()
        }
    }
}
