package pl.edu.pb.sm_projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    // declarations
    TextView timer4pomo;
    SeekBar timeDuration;
    TextView timeDurationText;
    Button okButton;
    int prog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //declarations
        prog = 25;
        timer4pomo = findViewById(R.id.time4pomo);
        timer4pomo.setText(R.string.main_label);

        timeDuration = findViewById(R.id.timeDuration);
        timeDuration.setMax(60);
        timeDuration.setProgress(5);

        timeDurationText = findViewById(R.id.timeDurationText);
        timeDurationText.setText("" + prog);

        timeDuration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                prog = progress;
                timeDurationText.setText("" + prog);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        okButton = findViewById(R.id.button1);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity(prog);
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Obsłuż zmianę konfiguracji, na przykład orientację ekranu
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Dostosuj interfejs dla orientacji poziomej
            // Tu możesz dodać odpowiednie zmiany dla interfejsu w orientacji poziomej
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Dostosuj interfejs dla orientacji pionowej
            // Tu możesz dodać odpowiednie zmiany dla interfejsu w orientacji pionowej
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Aktywność staje się widoczna dla użytkownika
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Wywoływane, gdy aktywność staje się aktywna i gotowa do interakcji z użytkownikiem
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Wywoływane, gdy aktywność traci focus, ale pozostaje widoczna (na przykład w trakcie rozmowy telefonicznej)
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Wywoływane, gdy aktywność przestaje być widoczna dla użytkownika
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Wywoływane przed zniszczeniem aktywności. Tutaj możesz zwalniać zasoby.
    }

    // Metoda wywoływana, gdy użytkownik wraca do tej aktywności z innej aktywności
    @Override
    protected void onRestart() {
        super.onRestart();
        // Tu możesz przywrócić stan aktywności po zakończeniu onStop
    }

    public void openNewActivity(int prog) {
        // Pass the onFinish callback to PomoTimerActivity
        PomoTimerActivity.OnFinishCallback onFinishCallback = new PomoTimerActivity.OnFinishCallback() {
            @Override
            public void onFinish() {
                // Handle onFinish event in MainActivity (update UI or perform other actions)
                // Display a Snackbar message instead of Toast
                View rootView = findViewById(android.R.id.content);
                Snackbar.make(rootView, R.string.pomodoro_completed, Snackbar.LENGTH_SHORT).show();
            }
        };

        // Set the callback in the holder class
        FinishCallbackHolder.setOnFinishCallback(onFinishCallback);

        Intent intent = new Intent(this, PomoTimerActivity.class);
        intent.putExtra("prog", prog);
        startActivity(intent);
    }
}