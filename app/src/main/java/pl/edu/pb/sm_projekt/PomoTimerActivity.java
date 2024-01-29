package pl.edu.pb.sm_projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class PomoTimerActivity extends AppCompatActivity {

    private TextView countdownText;
    private Button countdownButton;
    private CountDownTimer countdownTimer;
    private boolean timerRunning;
    private long timeLeftInms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomo_timer);

        countdownText = findViewById(R.id.countdownText);
        countdownButton = findViewById(R.id.countdownButton);

        Intent mIntent = getIntent();
        final int duration = mIntent.getIntExtra("prog", 150000);
        timeLeftInms = duration * 60 * 1000;

        String default_duration;
        if (duration < 10) {
            default_duration = "0" + duration;
        } else {
            default_duration = "" + duration;
        }
        default_duration += "\n00";
        countdownText.setText(default_duration);

        countdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStop(duration);
            }
        });
    }

    public void startStop(int duration) {
        if (timerRunning) {
            // Ask do you want to quit ?
            stopTimer(duration); // will stop the running timer
        } else {
            // snackbar 'timer started'
            showSnackbar("Timer started");
            startTimer(duration); // will start the countdown
        }
    }

    public void startTimer(final int duration) {
        countdownTimer = new CountDownTimer(duration * 60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInms = millisUntilFinished;
                updateTimer(); // will update the timer text from 25:00 to 24:59...
            }

            @Override
            public void onFinish() {
                // snackbar 'you did it!!!'
                showSnackbar("You did it!");
                String default_duration = (duration < 10) ? "0" + duration : "" + duration;
                default_duration += "\n00";
                countdownText.setText(default_duration);
            }
        }.start();
        countdownButton.setText("Give Up");
        timerRunning = true;
    }

    public void stopTimer(int duration) {
        countdownTimer.cancel();
        timerRunning = false;
        String show_duration = (duration < 10) ? "0" + duration : "" + duration;
        show_duration += "\n00";
        countdownText.setText(show_duration);
        timeLeftInms = duration * 60000;
        countdownButton.setText("START");
    }

    public void updateTimer() {
        int minutes = (int) timeLeftInms / 60000;
        int seconds = (int) timeLeftInms % 60000 / 1000;

        String timeLeftText = (minutes < 10) ? "0" + minutes : "" + minutes;
        timeLeftText += "\n";
        if (seconds < 10) {
            timeLeftText += "0";
        }
        timeLeftText += seconds;

        countdownText.setText(timeLeftText);
    }

    private void showSnackbar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }
}
