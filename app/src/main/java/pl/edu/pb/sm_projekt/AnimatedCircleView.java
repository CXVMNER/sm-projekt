package pl.edu.pb.sm_projekt;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class AnimatedCircleView extends View {
    private Paint paint;
    private float radius;

    public AnimatedCircleView(Context context) {
        super(context);
        init();
    }

    public AnimatedCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(R.color.your_circle_color));
        radius = 0;
    }

    public void setRadius(float radius) {
        this.radius = radius;
        invalidate(); // Redraw the view
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, paint);
    }
}
