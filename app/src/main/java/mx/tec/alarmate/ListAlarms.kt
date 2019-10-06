package mx.tec.alarmate

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_list_alarms.*

class ListAlarms : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_alarms)

    }

    fun cmdAddAlarm(v: View){
        var intent = Intent(this, EditAlarm::class.java)
        intent.putExtra("state", "add")
        startActivity(intent)
    }
}
