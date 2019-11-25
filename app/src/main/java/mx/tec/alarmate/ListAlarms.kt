package mx.tec.alarmate

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.annotation.MainThread
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_list_alarms.*
import kotlinx.android.synthetic.main.activity_list_alarms_content.*
import mx.tec.alarmate.db.adapter.AlarmAdapter
import mx.tec.alarmate.db.model.Alarm
import mx.tec.alarmate.db.util.AppDatabase
import java.sql.Time

class ListAlarms : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_alarms)
    }

    override fun onResume() {
        super.onResume()
        Thread{
            val db = AppDatabase.getInstance(this)
            val datos = db.alarmDao().listAlarms()
            val adaptador = AlarmAdapter(this, R.layout.alarm_layout, datos)
            this@ListAlarms.runOnUiThread {
                lvAlarms.adapter = adaptador
            }
        }.start()

        lvAlarms.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val alarm = parent.adapter.getItem(position) as Alarm
                val intent = Intent(this@ListAlarms, EditAlarm::class.java)
                intent.putExtra("idAlarm", alarm.idAlarm)
                startActivity(intent);
            }
        }
    }

    fun cmdAddAlarm(v: View){
        var intent = Intent(this, EditAlarm::class.java)
        startActivity(intent)
    }
}
