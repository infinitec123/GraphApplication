package vibhulabs.shopperbuddy.charts;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;

/**
 * Created by abhinav.chauhan on 09/11/15.
 *
 * Sample usage in xml
 *
 *
 */
public class FunnelChart extends View {

    private Paint mPaint;
    private float mScreenDensity;
    private int mRequiredHeight;
    private int mRequiredWidth;

    private ArrayList<Circle> mCircles;
    private ArrayList<CircleTail> mCircleTails;

    private OnCircleClickListener mOnCircleClickListener;

    // *********************************************************************************************
    // * Constructors
    // *********************************************************************************************

    public FunnelChart(Context context) {
        this(context, null);
    }

    public FunnelChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FunnelChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.FILL);

        mScreenDensity = getResources().getDisplayMetrics().density;

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.FunnelChart,
                0, 0);
        mRequiredHeight = a.getDimensionPixelOffset(R.styleable.FunnelChart_required_height,
                getResources().getDisplayMetrics().heightPixels / 3);
        mRequiredWidth = a.getDimensionPixelOffset(R.styleable.FunnelChart_required_width,
                getResources().getDisplayMetrics().widthPixels);

        initCircles();
        initCircleTails(mCircles);
    }

    // *********************************************************************************************
    // * Method overrides
    // *********************************************************************************************

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        for (int i = 0; i < mCircles.size(); i++) {
            if (mCircles.get(i).handleTouchEvent(event)) {
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < mCircleTails.size(); i++) {
            mCircleTails.get(i).draw(canvas, mPaint);
        }
        for (int i = 0; i < mCircles.size(); i++) {
            mCircles.get(i).draw(canvas, mPaint);
        }
    }

    // *********************************************************************************************
    // * Initialization code
    // *********************************************************************************************

    private void initCircles() {
        Circle circle;
        mCircles = new ArrayList<>();

        circle = new Circle();
        circle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnCircleClickListener != null) {
                    mOnCircleClickListener.onCircleClick(0);
                }
            }
        });
        mCircles.add(circle);

        circle = new Circle();
        circle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnCircleClickListener != null) {
                    mOnCircleClickListener.onCircleClick(1);
                }
            }
        });
        mCircles.add(circle);

        circle = new Circle();
        circle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnCircleClickListener != null) {
                    mOnCircleClickListener.onCircleClick(2);
                }
            }
        });
        mCircles.add(circle);

        circle = new Circle();
        circle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnCircleClickListener != null) {
                    mOnCircleClickListener.onCircleClick(3);
                }
            }
        });
        mCircles.add(circle);
        setCircleDimensions(mRequiredHeight, mRequiredWidth);
        setLabel("123 Hello", 0, false);
        setLabel("4354 Hello", 1, false);
        setLabel("43666 Hello", 2, false);
        setLabel("123666 Hello", 3, false);
    }

    private void setCircleDimensions(int requiredHeight, int requiredWidth) {
        float ratio = ((float) requiredHeight) / 484f;
        int firstCenterX = (int) (requiredWidth * 0.4f);
        mCircles.get(0).init(firstCenterX, 86 * ratio, 44 * ratio, Color.parseColor("#004B42"), R.drawable.ic_redeemed);
        mCircles.get(1).init(firstCenterX - 86 * ratio, 154 * ratio, 50 * ratio, Color.parseColor("#2EAF63"), R.drawable.ic_walkins);
        mCircles.get(2).init(firstCenterX + 8 * ratio, 250 * ratio, 59 * ratio, Color.parseColor("#9ECA30"), R.drawable.ic_viewed);
        mCircles.get(3).init(firstCenterX - 104 * ratio, 368 * ratio, 74 * ratio, Color.parseColor("#008E84"), R.drawable.ic_target);
    }

    private void initCircleTails(ArrayList<Circle> circles) {
        mCircleTails = new ArrayList<>();
        mCircleTails.add(generateCircleTail(circles.get(0), circles.get(1), Color.parseColor("#004B42")));
        mCircleTails.add(generateCircleTail(circles.get(1), circles.get(2), Color.parseColor("#2EAF63")));
        mCircleTails.add(generateCircleTail(circles.get(2), circles.get(3), Color.parseColor("#9ECA30")));

        Circle lastCircle = circles.get(3);
        CircleTail lastTail = new CircleTail(
                (int) (lastCircle.centerX),
                (int) (lastCircle.centerY + lastCircle.radius * 1.2),
                0,
                lastCircle.radius * dpToPx(10) / 350f,
                Color.parseColor("#008E84")
        );
        mCircleTails.add(lastTail);
    }

    private CircleTail generateCircleTail(Circle circle0, Circle circle1, int color) {
        float deltaX = circle0.centerX - circle1.centerX;
        float deltaY = circle0.centerY - circle1.centerY;
        double angleInRadians = Math.atan2(deltaY, deltaX);
        float angleInDegrees = (float) Math.toDegrees(angleInRadians) + 90;
        double vx = circle0.centerX - circle1.centerX;
        double vy = circle0.centerY - circle1.centerY;
        double mag = Math.sqrt(vx * vx + vy * vy);
        double d = (mag - circle0.radius - circle1.radius) / 2 + circle0.radius;
        vx /= mag;
        vy /= mag;
        int x = (int) (circle0.centerX - vx * d);
        int y = (int) (circle0.centerY - vy * d);
        return new CircleTail(x, y, angleInDegrees, circle0.radius * dpToPx(10) / 350f, color);
    }

    // *********************************************************************************************
    // * public methods
    // *********************************************************************************************

    public void setLabel(String label, int index, boolean invalidate) {
        if (index < 0 || index >= mCircles.size()) {
            throw new IllegalArgumentException("Index must be between 0 and "
                    + (mCircles.size() - 1) + " inclusive");
        }
        mCircles.get(index).label = label;
        if (invalidate) {
            invalidate();
        }
    }

    public void setOnCircleClickListener(OnCircleClickListener onCircleClickListener) {
        mOnCircleClickListener = onCircleClickListener;
    }

    public void setRequiredHeight(int height) {
        mRequiredHeight = height;
        setCircleDimensions(mRequiredHeight, mRequiredWidth);
        invalidate();
    }

    public void setRequiredWidth(int width) {
        mRequiredWidth = width;
        setCircleDimensions(mRequiredHeight, mRequiredWidth);
        invalidate();
    }

    // *********************************************************************************************
    // * utility methods
    // *********************************************************************************************

    public int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    // *********************************************************************************************
    // * Private classes
    // *********************************************************************************************

    private class Circle {
        private static final int ANIM_DURATION = 50;
        private float centerX;
        private float centerY;
        private float radius;
        private float currentRadius;
        private float strokeWidth;
        private int color;

        private Bitmap icon;
        private float iconScale;
        private Matrix iconMatrix;
        private RectF rectF;

        private OnClickListener onClickListener;

        private boolean touching = false;

        private String label = "";

        public void init(float centerX, float centerY, float radius, int color, int iconRes) {
            this.centerX = centerX;
            this.centerY = centerY;
            this.radius = this.currentRadius = radius;
            this.color = color;

            iconScale = currentRadius / 100;
            icon = BitmapFactory.decodeResource(getResources(), iconRes);
            iconMatrix = new Matrix();
            rectF = new RectF();
        }

        public void draw(Canvas canvas, Paint paint) {
            this.strokeWidth = 0.15f * currentRadius;
            paint.setColor(color);
            canvas.drawCircle(centerX, centerY, currentRadius, paint);

            paint.setColor(Color.WHITE);
            canvas.drawCircle(centerX, centerY, currentRadius - strokeWidth, paint);

            paint.setColor(color);
            canvas.drawCircle(centerX, centerY, currentRadius - 3f * strokeWidth, paint);

            paint.setTextSize(dpToPx(20));
            canvas.drawText(label, centerX + radius * 1.3f, centerY - (paint.descent() + paint.ascent()) / 2, paint);


            iconMatrix.setScale(iconScale, iconScale, icon.getWidth() / 2, icon.getHeight() / 2);
            iconMatrix.postTranslate(centerX - icon.getWidth() / 2, centerY - icon.getHeight() / 2);
            canvas.drawBitmap(icon, iconMatrix, paint);
        }

        public boolean handleTouchEvent(MotionEvent event) {
            float x2 = (event.getX() - centerX) * (event.getX() - centerX);
            float y2 = (event.getY() - centerY) * (event.getY() - centerY);
            if (x2 + y2 <= currentRadius * currentRadius) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        touching = true;
                        expand();
                        return true;
                    case MotionEvent.ACTION_UP:
                        if (touching) {
                            if (onClickListener != null) {
                                onClickListener.onClick(FunnelChart.this);
                            }
                            collapse();
                        }
                        touching = false;
                        return true;
                    case MotionEvent.ACTION_MOVE:

                }
            } else if (touching) {
                touching = false;
                collapse();
                return true;
            }
            return false;
        }

        public void setOnClickListener(OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }

        private void expand() {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(currentRadius, radius * 1.2f);
            valueAnimator.setDuration(ANIM_DURATION);
            valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    currentRadius = (float) animation.getAnimatedValue();
                    iconScale = currentRadius / 100;
                    invalidate();
                }
            });
            valueAnimator.start();
        }

        private void collapse() {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(currentRadius, radius);
            valueAnimator.setDuration(ANIM_DURATION);
            valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    currentRadius = (float) animation.getAnimatedValue();
                    iconScale = currentRadius / 100;
                    invalidate();
                }
            });
            valueAnimator.start();
        }
    }

    private class CircleTail {
        // https://www.desmos.com/calculator/cahqdxeshd
        private float[][] endPoints = {{-7.5f, 10}, {7.5f, 10}, {7.5f, -10}, {-7.5f, -10}};
        private float[][] controlPoints = {{0, 10}, {0, 10}, {1.5f, 4}, {1.5f, -4}, {0, -10}, {0, -10}, {-1.5f, -4}, {-1.5f, 4}};

        private Path path;
        int centerX;
        int centerY;
        private int color;

        public CircleTail(int centerX, int centerY, float angleInDegrees, float scale, int color) {
            init(centerX, centerY, angleInDegrees, scale, color);
        }

        public void init(int centerX, int centerY, float angleInDegrees, float scale, int color) {
            this.centerX = centerX;
            this.centerY = centerY;
            this.color = color;
            initPath(angleInDegrees, scale);
        }

        private void initPath(float angleInDegrees, float scale) {
            Matrix matrix = new Matrix();
            RectF rectF = new RectF();
            path = new Path();
            path.reset();

            path.moveTo(endPoints[0][0], endPoints[0][1]);
            for (int i = 0; i < 3; i++) {
                path.cubicTo(controlPoints[i * 2][0], controlPoints[i * 2][1], controlPoints[i * 2 + 1][0], controlPoints[i * 2 + 1][1], endPoints[i + 1][0], endPoints[i + 1][1]);
            }
            path.cubicTo(controlPoints[6][0], controlPoints[6][1], controlPoints[7][0], controlPoints[7][1], endPoints[0][0], endPoints[0][1]);

            path.computeBounds(rectF, true);
            matrix.setRotate(angleInDegrees);
            matrix.postScale(scale, scale, rectF.centerX(), rectF.centerY());
            matrix.postTranslate(centerX, centerY);
            path.transform(matrix);
        }

        public void draw(Canvas canvas, Paint paint) {
            paint.setColor(color);
            canvas.drawPath(path, paint);
        }
    }

    public interface OnCircleClickListener {
        void onCircleClick(int index);
    }
}
