package pl.edu.pb.sm_projekt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

@SuppressLint("unused")
public class PomoTimerActivity extends AppCompatActivity {

    private TextView countdownText;
    private Button countdownButton;
    private CountDownTimer countdownTimer;
    private ShakeDetector shakeDetector;
    private boolean timerRunning;
    private long timeLeftInms;
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "pomo_timer_channel";

    // Add a new interface for onFinish callback
    public interface OnFinishCallback {
        void onFinish();
    }

    private OnFinishCallback onFinishCallback;

    // Add a default (zero-argument) constructor
    public PomoTimerActivity() {
        // Default constructor
    }

    // Add a custom constructor with the OnFinishCallback
    public PomoTimerActivity(OnFinishCallback onFinishCallback) {
        this.onFinishCallback = onFinishCallback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomo_timer);

        countdownText = findViewById(R.id.countdownText);
        countdownButton = findViewById(R.id.countdownButton);

        createNotificationChannel();

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

        // Initialize ShakeDetector
        shakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake() {
                // Start or stop the timer when a shake is detected
                startStop(duration);
            }
        });

        countdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStop(duration);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register the sensor listener when the activity resumes
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometerSensor != null) {
            sensorManager.registerListener(shakeDetector, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the sensor listener when the activity pauses
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManager.unregisterListener(shakeDetector);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void startStop(int duration) {
        if (timerRunning) {
            // Ask do you want to quit ?
            stopTimer(duration); // will stop the running timer
        } else {
            // Snackbar 'timer started'
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
                // Retrieve the callback from the holder class
                PomoTimerActivity.OnFinishCallback onFinishCallback = FinishCallbackHolder.getOnFinishCallback();
                if (onFinishCallback != null) {
                    onFinishCallback.onFinish();
                }

                String default_duration = (duration < 10) ? "0" + duration : "" + duration;
                default_duration += "\n00";
                countdownText.setText(default_duration);

                // Snackbar 'you did it!!!'
                showSnackbar("You did it!");

                // Show the notification only if the timer finished naturally (not stopped by the user)
                if (timerRunning) {
                    showNotification("Pomo Timer", "You did it!");
                    // Play alarm sound
                    playAlarm();
                }
            }
            private void playAlarm() {
                // Use the specific sound file 'mixkit_alarm_tone_996.wav'
                MediaPlayer mediaPlayer = MediaPlayer.create(PomoTimerActivity.this, R.raw.mixkit_alarm_tone_996);
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayer.release();
                    }
                });
            }


        }.start();

        // Show snackbar 'timer started' only when the timer is initially started
        if (!timerRunning) {
            showSnackbar("Timer started");
        }

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

        // Snackbar 'timer stopped'
        showSnackbar("Timer stopped");
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
        View rootView = findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT);

        // Set the anchor view to the countdownButton or another appropriate view
        snackbar.setAnchorView(countdownButton);

        snackbar.show();
    }

    private void showNotification(String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Set the notification to be automatically canceled when the user taps it
        builder.setAutoCancel(true);

        // Create an explicit intent for an activity in your app
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(pendingIntent);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
