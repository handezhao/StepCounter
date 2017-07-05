package com.hdz.step.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.hdz.step.R;

/**
 *
 * Description:  This is a view to display progress of current steps.
 * Created by hdz on 2017/7/3.
 */

public class StepView extends View {

    //圆环的直径
    private int dia;

    private Paint progressPaint;

    private Paint stepTextPaint;
    private Paint contentTextPaint;

    private RectF rect;

    private float cirqueWidth;
    private int progressColor;
    private int progressBgColor;
    float stepTextSize;
    int stepTextColor;
    float contentTextSize;
    int contentTextColor;

    private float progress;
    private float currentPorgress;

    private Paint.FontMetrics stepMetrics;
    private Paint.FontMetrics contentMetrics;

    ValueAnimator animator;

    /**
     * 目标步数，用于计算进度
     */
    private int goal = 20000;

    /**
     * 当前步数
     */
    private int currentSteps;

    private float stepTextBaseline;


    public StepView(Context context) {
        this(context, null, 0);
    }

    public StepView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        if (attrs == null) return;

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.StepView, defStyleAttr, 0);
        progressColor = array.getColor(R.styleable.StepView_progress_color, Color.RED);
        progressBgColor = array.getColor(R.styleable.StepView_progress_bg_color, Color.GRAY);
        stepTextSize = array.getDimension(R.styleable.StepView_step_text_size, 50);
        stepTextColor = array.getColor(R.styleable.StepView_step_text_color, Color.BLACK);
        contentTextSize = array.getDimension(R.styleable.StepView_content_text_size, 20);
        contentTextColor = array.getColor(R.styleable.StepView_content_text_color, Color.BLACK);
        cirqueWidth = array.getDimension(R.styleable.StepView_cirque_width, 20);
        array.recycle();

        progressPaint = new Paint();
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setAntiAlias(true);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setStrokeWidth(cirqueWidth);

        stepTextPaint = new Paint();
        stepTextPaint.setColor(stepTextColor);
        stepTextPaint.setTextSize(stepTextSize);
        stepTextPaint.setAntiAlias(true);

        contentTextPaint = new Paint();
        contentTextPaint.setColor(contentTextColor);
        contentTextPaint.setTextSize(contentTextSize);
        contentTextPaint.setAntiAlias(true);

        rect = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        stepMetrics = stepTextPaint.getFontMetrics();
        contentMetrics = contentTextPaint.getFontMetrics();
        int width = measureValue(400, widthMeasureSpec);
        int height = measureValue(400, heightMeasureSpec);
        dia = Math.min(width, height);
        setMeasuredDimension(dia, dia);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        dia = Math.min(w, h);
        rect.left = cirqueWidth / 2;
        rect.top = cirqueWidth / 2;
        rect.right = dia - cirqueWidth / 2;
        rect.bottom = dia - cirqueWidth / 2;
    }

    private int measureValue(int defaultValue, int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int value = MeasureSpec.getSize(measureSpec);
        int result = defaultValue;
        if (MeasureSpec.EXACTLY == mode) {
            result = value;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCirque(canvas);
        drawSteps(canvas);
        drawContent(canvas);
    }

    /**
     * 避免过度绘制，先画走过的进度，再画底色
     *
     * @param canvas
     */
    private void drawCirque(Canvas canvas) {
        //startAngle的0度是3点钟，顺时针为正，逆时针为负
        progressPaint.setColor(progressColor);
        canvas.drawArc(rect, 120, 300 * currentPorgress, false, progressPaint);
        progressPaint.setColor(progressBgColor);
        canvas.drawArc(rect, (120 + 300 * currentPorgress), 300 * (1 - currentPorgress), false, progressPaint);
    }

    private void drawSteps(Canvas canvas) {
        //画在正中央
        float width = stepTextPaint.measureText(formateSteps(currentSteps));
        stepTextBaseline = dia / 2 + (Math.abs(stepMetrics.ascent) - stepMetrics.descent) / 2;
        canvas.drawText(formateSteps(currentSteps), (dia - width) / 2, stepTextBaseline, stepTextPaint);
    }

    private void drawContent(Canvas canvas) {
        StringBuilder sb = new StringBuilder();
        sb.append("今日目标步数 ").append(formateSteps(goal));
        float w = contentTextPaint.measureText(sb.toString());
        float y = stepTextBaseline + contentMetrics.descent - contentMetrics.ascent + 40;
        canvas.drawText(sb.toString(), (dia - w) / 2, y, contentTextPaint);
    }

    private void startAnimator(float start, float end, long time) {
        animator = ValueAnimator.ofFloat(start, end);
        animator.setDuration(time);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPorgress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }

    private void startAnimator() {
        startAnimator(currentPorgress, progress, 1000);
    }

    /**
     * @param goal  目标步数
     * @param steps 当前步数
     */
    public void setSteps(int steps, int goal) {
        this.goal = goal;
        this.currentSteps = steps;
        progress = Math.min(steps / (float) goal, 1.0f);
        startAnimator();
    }

    /**
     * @param steps 当前步数
     */
    public void setSteps(int steps) {
        setSteps(steps, 20000);
    }

    /**
     * 设置目标步数
     * @param goal 目标步数
     */
    public void setGoal(int goal) {
        setSteps(currentSteps, goal);
    }

    /**
     * 当步数过大时，省略显示
     *
     * @return
     */
    private String formateSteps(int steps) {
        String result = "";
        if (steps <= 0) {
            result = "0";
        } else if (steps > 99999) {
            result = steps / 10000 + "万";
        } else {
            result = String.valueOf(steps);
        }
        return result;
    }
}
