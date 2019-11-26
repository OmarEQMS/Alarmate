package mx.tec.alarmate.puzzle

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.*
import androidx.fragment.app.Fragment
import mx.tec.alarmate.db.model.Alarm
import mx.tec.alarmate.db.model.Puzzle
import android.view.View
import android.media.RingtoneManager
import android.media.MediaPlayer
import android.media.AudioAttributes
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.content.DialogInterface
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager

open class PuzzleFragment(open val alarm: Alarm, open val puzzle: Puzzle?) : Fragment() {
    var listener: PuzzleListener? = null
    lateinit var vibrator: Vibrator
    lateinit var mediaPlayer: MediaPlayer
    var vibrating = false
    val vibrationHandler = Handler()
    var flashing = false
    var flashOn = false
    val flashingHandler = Handler()

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
        sound()

        if(alarm.vibration){
            vibrate()
        }

        if(alarm.flash){
            flash()
        }
    }

    open fun stopAlarm(){
        mediaPlayer.stop()

        vibrating = false

        flashing = false
        flashOn = true
        toggleFlash()
    }

    open fun sound(){
        var alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

        if (alert == null) {
            alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            if (alert == null) {
                alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
            }
        }
        mediaPlayer = MediaPlayer()
        mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build())
        mediaPlayer.setDataSource(context!!, alert)
        mediaPlayer.prepare()
        mediaPlayer.start()
    }

    open fun vibrate(){
        if(vibrating){
            return
        }
        vibrating = true
        vibrationHandler.postDelayed(object : Runnable {
            override fun run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(VIBRATION_TIME, VibrationEffect.EFFECT_HEAVY_CLICK))
                } else {
                    //deprecated in API 26
                    vibrator.vibrate(VIBRATION_TIME)
                }
                if (vibrating) {
                    vibrationHandler.postDelayed(this, VIBRATION_PAUSE_TIME)
                }
            }
        }, VIBRATION_PAUSE_TIME)
    }

    open fun flash(){
        if(flashing){
            return
        }
        if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!,
                    Manifest.permission.CAMERA)) {
                AlertDialog.Builder(context)
                    .setTitle("Permitir accesso a cámara")
                    .setMessage("Es necesario utilizar el flash de la cámara para la alarma")
                    .setPositiveButton(android.R.string.yes,
                        DialogInterface.OnClickListener { dialog, which ->
                            beginFlash()
                        })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show()
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.CAMERA), MY_PERMISSIONS_REQUEST_CAMERA)
            }
        } else {
            // Permission has already been granted
            beginFlash()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == MY_PERMISSIONS_REQUEST_CAMERA){
            beginFlash()
        }
    }

    private fun beginFlash(){
        if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.CAMERA), MY_PERMISSIONS_REQUEST_CAMERA)
            return
        }

        flashing = true
        flashingHandler.postDelayed(object : Runnable {
            override fun run() {
                if (flashing) {
                    flashingHandler.postDelayed(this, FLASHING_INTERVAL_TIME)
                } else {
                    return
                }
                toggleFlash()
            }
        }, FLASHING_INTERVAL_TIME)
    }

    private fun toggleFlash(){
        val cameraManager = context!!.getSystemService(Context.CAMERA_SERVICE) as CameraManager?
        try {
            val cameraId = cameraManager!!.cameraIdList[0]
            flashOn = !flashOn
            cameraManager.setTorchMode(cameraId, flashOn)
        } catch (e: CameraAccessException) {
        }
    }
}
