package pl.edu.pb.sm_projekt;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

public class AlarmPlayer {

    public static void playAlarm(Context context) {
        try {
            // Use the specific sound file 'mixkit_alarm_tone_996.wav'
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.mixkit_alarm_tone_996);
            if (mediaPlayer != null) {
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayer.release();
                    }
                });
            } else {
                Log.e("AudioError", "MediaPlayer is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("AudioError", "Error playing audio");
        }
    }
}