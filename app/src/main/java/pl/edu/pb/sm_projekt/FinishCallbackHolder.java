package pl.edu.pb.sm_projekt;

public class FinishCallbackHolder {
    private static PomoTimerActivity.OnFinishCallback onFinishCallback;

    public static PomoTimerActivity.OnFinishCallback getOnFinishCallback() {
        return onFinishCallback;
    }

    public static void setOnFinishCallback(PomoTimerActivity.OnFinishCallback callback) {
        onFinishCallback = callback;
    }
}