package com.example.watcher.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.example.watcher.R;

/**
 * The Regular Hours View class is a custom view. At its core, it represents a clock with a configurable time.
 *
 * @author georgedem975
 */

public class RegularHoursView extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);


    private ObjectAnimator objectAnimatorSecond;

    private ObjectAnimator objectAnimatorMinute;

    private ObjectAnimator objectAnimatorHour;

    private int maxValue;

    private int turningTheSecondHand;

    private int turningTheMinuteHand;

    private int clockwiseRotation;

    private int textColor;

    private int timeInterval;


    public RegularHoursView(Context context) {
        super(context);
        initialization(context, null);
    }

    public RegularHoursView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        initialization(context, attributeSet);
    }

    public RegularHoursView(Context context, @Nullable AttributeSet attributeSet, int defaultStyleAttribute) {
        super(context, attributeSet, defaultStyleAttribute);
        initialization(context, attributeSet);
    }

    private void initialization(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RegularHoursView);

            maxValue = a.getInt(R.styleable.RegularHoursView_maxValue, 120);
            turningTheSecondHand = a.getInt(R.styleable.RegularHoursView_turningTheSecondHand, 0);
            turningTheMinuteHand = a.getInt(R.styleable.RegularHoursView_turningTheMinuteHand, 0);
            clockwiseRotation = a.getInt(R.styleable.RegularHoursView_clockwiseRotation, 0);
            textColor = a.getColor(R.styleable.RegularHoursView_textColor, Color.BLACK);
            timeInterval = a.getInt(R.styleable.RegularHoursView_timeInterval, 3);

            a.recycle();
        }
    }

    /**
     * the method draws the clock
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();

        float width = getWidth();
        float height = getHeight();

        float aspect = width / height;
        final float normalAspect = 2f / 1f;
        if (aspect > normalAspect) {
            width = normalAspect * height;
        } if (aspect < normalAspect) {
            height = width / normalAspect;
        }

        drawClockBackGround(canvas, width, height, 1.2f, -1.2f);

        float scale = 0.9f;

        double step = Math.PI / maxValue;

        canvas.restore();

        canvas.save();

        scale = 0.7f;
        float longScale = 0.9f;
        float textPadding = 0.85f;

        canvas.translate(width / 2, height/14f);

        paint.setTextSize(height / 5);
        paint.setColor(textColor);
        paint.setStyle(Paint.Style.FILL);

        float factor = height * scale * longScale * textPadding;

        for (int i = 0, j = 9; i < 80; i += 20, j++) {
            drawNumbers(canvas, j, factor, step, i, height);
        }

        for (int i = 80, j = 1; i < 260; i += 20, j++) {
            drawNumbers(canvas, j, factor, step, i, height);
        }

        canvas.restore();

        canvas.save();

        canvas.translate(width / 2, height);
        canvas.scale(.5f * width, -1f * height);

        drawClockLine(canvas, 0.018f, 0.01f, 0, 0, 0.6f, turningTheSecondHand);

        drawClockLine(canvas, 0.02f, 0.01f, 0, 0, 0.5f, turningTheMinuteHand);

        drawClockLine(canvas, 0.03f, 0.01f, 0, 0, 0.4f, clockwiseRotation);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        canvas.drawCircle(0f, 0f, .05f, paint);

        canvas.restore();
    }

    public int getTimeInterval() {
        return this.timeInterval;
    }

    public int getClockwiseRotation() {
        return this.clockwiseRotation;
    }

    public void setClockwiseRotation(int value) {
        this.clockwiseRotation = value;
    }

    public int getTurningTheMinuteHand() {
        return this.turningTheMinuteHand;
    }

    public void setTurningTheMinuteHand(int value) {
        this.turningTheMinuteHand = value;
        invalidate();
    }

    public int getTurningTheSecondHand() {
        return this.turningTheSecondHand;
    }

    public void setTurningTheSecondHand(int value) {
        this.turningTheSecondHand = value;
        invalidate();
    }

    /**
     * the method draws an animation of the movement of the second hand
     * @param value changed arrow value
     */
    public void setTurningTheSecondHandAnimated(int value) {
        animation("turningTheSecondHand", this.turningTheSecondHand, value, objectAnimatorSecond);
    }

    /**
     * the method draws an animation of the movement of the minute hand
     * @param value changed arrow value
     */
    public void setTurningTheMinuteHandAnimated(int value) {
        animation("turningTheMinuteHand", this.turningTheMinuteHand, value, objectAnimatorMinute);
    }

    /**
     * the method draws an animation of clockwise movement
     * @param value changed arrow value
     */
    public void setClockwiseRotationAnimated(int value) {
        animation("clockwiseRotation", this.clockwiseRotation, value, objectAnimatorHour);
    }

    private void animation(String propertyName, int value, int newValue, ObjectAnimator objectAnimator) {
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        if (newValue == 0) {
            newValue = -360;
            setTurningTheSecondHand(0);
        }
        else {
            objectAnimator = ObjectAnimator.ofInt(this, propertyName, value, newValue);
            objectAnimator.setDuration(100 + Math.abs(value - newValue) * 5);
            objectAnimator.setInterpolator(new DecelerateInterpolator());
            objectAnimator.start();
        }
    }

    private void drawNumbers(Canvas canvas, int number, float factor, double step, int degree, float height) {
        float x = (float) Math.cos(Math.PI - step * degree) * factor;
        float y = (float) Math.sin(Math.PI - step * degree) * factor;
        String text = Integer.toString(number);
        int textLen = Math.round(paint.measureText(text));
        canvas.drawText(Integer.toString(number), (x - textLen / 2), (height - y), paint);
    }

    private void drawClockLine(Canvas canvas, float width, float startX, float startY, float stopX, float stopY, int turningHand) {
        canvas.rotate((float)turningHand);

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(width);
        canvas.drawLine(startX, startY, stopX, stopY, paint);
        canvas.drawLine(-startX, startY, stopX, stopY, paint);

        canvas.rotate((float) -turningHand);
    }

    private void drawClockBackGround(Canvas canvas, float width, float height, float dx, float dy) {
        canvas.scale(.5f * width / 1.2f, -1f * height / 1.2f);
        canvas.translate(dx, dy);

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(0, 0, 1, paint);

        paint.setColor(Color.LTGRAY);

        canvas.drawCircle(0, 0, 0.9f, paint);

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(0.005f);

        float scale = 0.9f;

        double step = Math.PI / maxValue;
        paint.setStyle(Paint.Style.FILL);

        for (int i = 0; i <= maxValue * 2; i++) {
            float x1 = (float) Math.cos(Math.PI - step * i);
            float y1 = (float) Math.sin(Math.PI - step * i);
            float x2;
            float y2;
            if (i % 20 == 0) {
                x2 = x1 * scale * 0.9f;
                y2 = y1 * scale * 0.9f;
                canvas.drawCircle(x2, y2, 0.025f, paint);
            }
            else if (i % 4 == 0) {
                x2 = x1 * scale * 0.9f;
                y2 = y1 * scale * 0.9f;
                canvas.drawCircle(x2, y2, 0.01f, paint);
            }
        }
    }
}
