package pl.edu.pb.sm_projekt;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

class ShakeDetector implements SensorEventListener {

    private static final float SHAKE_THRESHOLD = 20.0f; // Adjust as needed
    private static final int SHAKE_TIME_INTERVAL = 1000; // Adjust as needed

    private long lastShakeTime;

    private OnShakeListener onShakeListener;

    public interface OnShakeListener {
        void onShake();
    }

    public ShakeDetector(OnShakeListener listener) {
        onShakeListener = listener;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            detectShake(event);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not needed for this example
    }

    private void detectShake(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        float acceleration = (float) Math.sqrt(x * x + y * y + z * z) - SensorManager.GRAVITY_EARTH;

        if (acceleration > SHAKE_THRESHOLD) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastShakeTime > SHAKE_TIME_INTERVAL) {
                lastShakeTime = currentTime;
                onShakeListener.onShake();
            }
        }
    }
}
