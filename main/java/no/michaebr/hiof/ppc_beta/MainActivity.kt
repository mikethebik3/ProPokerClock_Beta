package no.michaebr.hiof.ppp_alpha

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import java.util.*

class MainActivity : AppCompatActivity() {

    var mTextViewCountDown: AppCompatTextView? = null
    var mButtonStartPause: AppCompatButton? = null
    var mCountDownTimer: CountDownTimer? = null
    var mTimerRunning = false
    lateinit var smallBlindBtn: AppCompatButton
    lateinit var bigBlindBtn: AppCompatButton
    var mTimeLeftInMillis: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mTextViewCountDown = findViewById(R.id.text_view_countdown)
        mButtonStartPause = findViewById(R.id.button_start_pause)
        smallBlindBtn = findViewById(R.id.small_blnd_txt)
        bigBlindBtn = findViewById(R.id.big_blnd_txt)
        mTimeLeftInMillis = START_TIME_IN_MILLIS

        updateCountDownText()

        mButtonStartPause?.setOnClickListener {

            if (mTimerRunning) {

                pauseTimer()
            } else {

                startTimer()
            }
        }
    }

    fun startTimer() {

        mCountDownTimer = object : CountDownTimer(mTimeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                mTimeLeftInMillis = millisUntilFinished
                updateCountDownText()
            }

            override fun onFinish() {
                updateSmallAndBigButtons()
                resetTimer()
            }
        }.start()

        mTimerRunning = true
        updateButtons()
    }

    fun updateSmallAndBigButtons() {
        val smallBlind = smallBlindBtn!!.text.toString().toInt()
        val bigBlind = bigBlindBtn!!.text.toString().toInt()
        val smallUpdateBlind = smallBlind * 2
        val bigUpdateBlind = bigBlind * 2
        smallBlindBtn!!.text = smallUpdateBlind.toString() + ""
        bigBlindBtn!!.text = bigUpdateBlind.toString() + ""
    }

    fun pauseTimer() {
        mCountDownTimer!!.cancel()
        mTimerRunning = false
        updateButtons()
    }

    fun resetTimer() {
        updateButtons()
        mTimeLeftInMillis = START_TIME_IN_MILLIS
        updateCountDownText()
        startTimer()
    }

    fun updateCountDownText() {

        val minutes = (mTimeLeftInMillis / 1000).toInt() / 60

        val seconds = (mTimeLeftInMillis / 1000).toInt() % 60

        val timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)

        mTextViewCountDown!!.text = timeLeftFormatted
    }

    fun updateButtons() {
        if (mTimerRunning) {
            mButtonStartPause!!.text = "Pause"
        } else {
            mButtonStartPause!!.text = "Start"
            if (mTimeLeftInMillis < 1000) {
                mButtonStartPause!!.visibility = View.INVISIBLE
            } else {
                mButtonStartPause!!.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (mCountDownTimer != null) {
            mCountDownTimer!!.cancel()
        }
    }

    companion object {

        private const val START_TIME_IN_MILLIS: Long = 600000
    }
}