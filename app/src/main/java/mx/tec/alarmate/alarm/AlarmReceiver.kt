package mx.tec.alarmate.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import mx.tec.alarmate.db.model.Alarm
import mx.tec.alarmate.db.util.AppDatabase

class AlarmReceiver : BroadcastReceiver() {
    companion object {
        val ACTION_TRIGGER_ALARM = "mx.tec.alarmate.alarm.trigger."

        val ARG_ACTION = "ARG_ACTION"
        val ARG_ALARM = "ARG_ALARM"
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(AlarmReceiver::class.java.simpleName, "Received action ${intent.action}")

        if(intent.action != null){
            val idAlarm = intent.action!!.substring(ACTION_TRIGGER_ALARM.length, intent.action!!.length).toInt()
            val db = AppDatabase.getInstance(context)

            Thread {
                val alarm = db.alarmDao().getAlarm(idAlarm)
                Log.d(AlarmReceiver::class.java.simpleName, "Triggering alarm with id: ${idAlarm}")

                val i = Intent(context, AlarmActivity::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                i.putExtra(ARG_ACTION, intent.action)
                i.putExtra(ARG_ALARM, alarm)
                context.startActivity(i)
            }.start()
        }else {
            Log.e(AlarmReceiver::class.java.simpleName, "Received invalid action ${intent.action}")
        }
    }

}