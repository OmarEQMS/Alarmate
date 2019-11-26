package mx.tec.alarmate.puzzle

import android.content.Context
import android.media.Ringtone
import android.os.*
import androidx.fragment.app.Fragment
import mx.tec.alarmate.db.model.Alarm
import mx.tec.alarmate.db.model.Puzzle
import android.view.View
import android.media.RingtoneManager
import android.media.MediaPlayer

open class PuzzleFragment(open val alarm: Alarm, open val puzzle: Puzzle?) : Fragment() {
    var listener: PuzzleListener? = null
    lateinit var vibrator: Vibrator
    lateinit var mediaPlayer: MediaPlayer
    var vibrating = false
    val handler = Handler()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is PuzzleListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement PuzzleListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        startAlarm()
    }

    open fun startAlarm(){
        val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        mediaPlayer = MediaPlayer.create(context, notification)
        mediaPlayer.start()

        if(alarm.vibration){
            vibrate()
        }
    }

    open fun stopAlarm(){
        mediaPlayer.stop()
        vibrating = false
    }

    private fun vibrate(){
        if(vibrating){
            return
        }
        vibrating = true
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(VIBRATION_TIME, VibrationEffect.EFFECT_HEAVY_CLICK))
                } else {
                    //deprecated in API 26
                    vibrator.vibrate(VIBRATION_TIME)
                }
                if (vibrating) {
                    handler.postDelayed(this, VIBRATION_PAUSE_TIME)
                }
            }
        }, VIBRATION_PAUSE_TIME)
    }
}
