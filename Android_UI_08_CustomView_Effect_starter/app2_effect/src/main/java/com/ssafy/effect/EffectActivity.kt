package com.ssafy.effect

import android.content.Context
import android.media.RingtoneManager
import android.net.Uri
import android.os.*
import android.util.Log
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

private const val TAG = "EffectActivity_싸피"
class EffectActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_effect)
        val btn1 = findViewById<Button>(R.id.btn1)
        val btn2 = findViewById<Button>(R.id.btn2)
        val btn3 = findViewById<Button>(R.id.btn3)
        val btn4 = findViewById<Button>(R.id.btn4)
        val btn5 = findViewById<Button>(R.id.btn5)
        val btn6 = findViewById<Button>(R.id.btn6)
        val btn7 = findViewById<Button>(R.id.btn7)

        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) { // API Level 31에서 VibratorManager로 변경됨
            val vibratorManager = this.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator;
        } else {
            getSystemService(VIBRATOR_SERVICE) as Vibrator
        }

        // 1. 진동 발생(일회성)
        btn1.setOnClickListener {

            Log.d(TAG, "onCreate: ${vibrator}")
            // deprecated  Build.VERSION_CODES.O
            if(Build.VERSION.SDK_INT >= 26 ) {
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            }else{
                vibrator.vibrate(500);
            }
        }

        // 2. 패턴 진동 발생(일회성)
        btn2.setOnClickListener {
            val vibratorTiming = longArrayOf(100, 400, 100, 100, 100, 100)
            val effect = VibrationEffect.createWaveform(vibratorTiming, -1)
            vibrator.vibrate(effect)
        }

        // 3. 패턴 진동 반복
        btn3.setOnClickListener {
            val vibratorTiming = longArrayOf(100, 400, 100, 100, 100, 100)
            val effect = VibrationEffect.createWaveform(vibratorTiming, 2)
            vibrator.vibrate(effect)
        }

//        // 4. 패턴 진동 취소
        btn4.setOnClickListener {
            vibrator.cancel()
        }

        // 5. 패턴 진동 세기 조절 (일회성)
        btn5.setOnClickListener {
            val vibratorTiming = longArrayOf(100, 400, 100, 100, 100, 100)
            val vibratorAmplitudes = intArrayOf(0, 50, 0, 200,0, 200)
            val effect = VibrationEffect.createWaveform(vibratorTiming, vibratorAmplitudes,-1)
            vibrator.vibrate(effect)
        }

        val uriRingtone = Uri.parse("android.resource://" + packageName + "/" + R.raw.jazzbyrima)
        val ringtone = RingtoneManager.getRingtone(this, uriRingtone)

        // 6.Ringtone
        btn6.setOnClickListener {
            if(btn6.text == "Ringtone_start"){
                ringtone.play()
                btn6.setText("Ringtone_stop")
            }else{
                ringtone.stop()
                btn6.setText("Ringtone_start")
            }
        }

        val uriRingtone2 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        //val uriRingtone2 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val ringtone2 = RingtoneManager.getRingtone(this, uriRingtone2)

        // 7.default_bellsound_start
        btn7.setOnClickListener {
            if(btn7.text == "default_bellsound_start"){
                ringtone2.play()
                btn7.setText("default_bellsound_stop")
            }else{
                ringtone2.stop()
                btn7.setText("default_bellsound_start")
            }
        }
    }
}
