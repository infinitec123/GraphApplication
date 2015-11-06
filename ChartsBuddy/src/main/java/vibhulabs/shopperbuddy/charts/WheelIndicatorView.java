package vibhulabs.shopperbuddy.charts;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.util.AttributeSet;
import android.view.View;


import java.util.ArrayList;
import java.util.List;

/**
 * Displays a graphic in style with Google Fit Application.
 *
 * @author David Lázaro.
 * @attr ref android.R.styleable#mItemsLineWidth the width of the wheel indicator lines
 * @attr ref android.R.styleable#backgroundColor color for the inner circle
 * @attr ref android.R.styleable#mFilledPercent percent of circle filled by WheelItems values
 */
public class WheelIndicatorView extends View {

    private final static int ANGLE_INIT_OFFSET = -90;
    private final static int DEFAULT_FILLED_PERCENT = 100;
    private final static int DEFAULT_ITEM_LINE_WIDTH = 25;
    private final static int DEFAULT_ITEM_CIRCLE_WIDTH = 55;
    public static final int ANIMATION_DURATION = 2400;
    public static final int INNER_BACKGROUND_CIRCLE_COLOR = Color.argb(255, 220, 220, 220); // Color for

    private Paint itemArcPaint;
    private Paint itemEndPointsPaint;
    private Paint innerBackgroundCirclePaint;
    private List<WheelIndicatorItem> wheelIndicatorItems;
    private int minDistViewSize;
    private int maxDistViewSize;
    private int traslationX;
    private int traslationY;
    private RectF mWheelBoundsRectF;
    private Paint circleBackgroundPaint;
    private ArrayList<Float> wheelItemsAngles;     // calculated angle for each @WheelIndicatorItem
    private int mFilledPercent = 100; // Default is filled full always

    private int mItemsLineWidth = 25;
    private int mCircleWidth = 55;
    private float mDelta = mCircleWidth - mItemsLineWidth;

    //*********************************************************************
    // Life cycles
    //*********************************************************************


    public WheelIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public WheelIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public WheelIndicatorView(Context context) {
        super(context);
        init(null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int viewHeight = getMeasuredHeight();
        int viewWidth = getMeasuredWidth();

        this.minDistViewSize = Math.min(getMeasuredWidth(), getMeasuredHeight());
        this.maxDistViewSize = Math.max(getMeasuredWidth(), getMeasuredHeight());

        if (viewWidth <= viewHeight) {
            this.traslationX = 0;
            this.traslationY = (maxDistViewSize - minDistViewSize) / 2;
        } else {
            this.traslationX = 0;
            this.traslationX = (maxDistViewSize - minDistViewSize) / 2;
        }
        // Adding artificial padding, depending on line width
        mWheelBoundsRectF = new RectF(mItemsLineWidth + mDelta, mItemsLineWidth + mDelta, minDistViewSize - mItemsLineWidth - mDelta, minDistViewSize - mItemsLineWidth - mDelta);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(traslationX, traslationY);
        if (circleBackgroundPaint != null) {
            canvas.drawCircle(mWheelBoundsRectF.centerX(), mWheelBoundsRectF.centerY(), mWheelBoundsRectF.width() / 2 - mItemsLineWidth, circleBackgroundPaint);
        }

        canvas.drawArc(mWheelBoundsRectF, ANGLE_INIT_OFFSET, 360, false, innerBackgroundCirclePaint);

        /* Draw centre image */
        drawCentreImage(canvas);

        /* Draw Indicator Items */
        drawIndicatorItems(canvas);
    }

    //*********************************************************************
    // Private utility classes
    //*********************************************************************

    private void init(AttributeSet attrs) {
        TypedArray attributesArray = getContext().getTheme()
                .obtainStyledAttributes(attrs, R.styleable.WheelIndicatorView, 0, 0);

        int itemsLineWidth = attributesArray.getDimensionPixelSize(R.styleable.WheelIndicatorView_itemsLineWidth, DEFAULT_ITEM_LINE_WIDTH);
        setItemsLineWidth(itemsLineWidth);

        int circleWidth = attributesArray.getDimensionPixelSize(R.styleable.WheelIndicatorView_circleWidth, DEFAULT_ITEM_CIRCLE_WIDTH);
        setCircleWidth(circleWidth);

        int filledPercent = attributesArray.getInt(R.styleable.WheelIndicatorView_filledPercent, DEFAULT_FILLED_PERCENT);
        setFilledPercent(filledPercent);

        int bgColor = attributesArray.getColor(R.styleable.WheelIndicatorView_backgroundColor, -1);
        if (bgColor != -1) {
            setBackgroundColor(bgColor);
        }

        this.wheelIndicatorItems = new ArrayList<>();
        this.wheelItemsAngles = new ArrayList<>();

        itemArcPaint = new Paint();
        itemArcPaint.setStyle(Paint.Style.STROKE);
        itemArcPaint.setStrokeWidth(itemsLineWidth * 2);
        itemArcPaint.setAntiAlias(true);

        innerBackgroundCirclePaint = new Paint();
        innerBackgroundCirclePaint.setColor(INNER_BACKGROUND_CIRCLE_COLOR);
        innerBackgroundCirclePaint.setStyle(Paint.Style.STROKE);
        innerBackgroundCirclePaint.setStrokeWidth(itemsLineWidth * 2);
        innerBackgroundCirclePaint.setAntiAlias(true);

        itemEndPointsPaint = new Paint();
        itemEndPointsPaint.setAntiAlias(true);
    }

    private void recalculateItemsAngles() {
        wheelItemsAngles.clear();
        float total = 0;
        float angleAccumulated = 0;

        for (WheelIndicatorItem item : wheelIndicatorItems) {
            total += item.getWeight();
        }
        for (int i = 0; i < wheelIndicatorItems.size(); ++i) {
            float normalizedValue = wheelIndicatorItems.get(i).getWeight() / total;
            float angle = 360 * normalizedValue * mFilledPercent / 100;
            wheelItemsAngles.add(angle + angleAccumulated);
            angleAccumulated += angle;
        }
    }


    private void drawIndicatorItems(Canvas canvas) {
        if (wheelIndicatorItems.size() > 0) {
            for (int i = wheelIndicatorItems.size() - 1; i >= 0; i--) { // Iterate backward to overlap larger items
                draw(wheelIndicatorItems.get(i), mWheelBoundsRectF, wheelItemsAngles.get(i), canvas);
            }
        }
    }

    private void drawCentreImage(Canvas canvas) {
        Drawable centreDrawable = getDrawable(getContext(), R.drawable.drawable_men);
        if (centreDrawable != null) {
            int centreBound = 120;
            int left = (int) (mWheelBoundsRectF.centerX() - centreBound);
            int top = (int) (mWheelBoundsRectF.centerY() - centreBound);
            int right = (int) (mWheelBoundsRectF.centerX() + centreBound);
            int bottom = (int) (mWheelBoundsRectF.centerY() + centreBound);

            centreDrawable.setBounds(left, top, right, bottom);
            centreDrawable.draw(canvas);
        }
    }

    private void draw(WheelIndicatorItem indicatorItem, RectF surfaceRectF, float angle, Canvas canvas) {
        itemArcPaint.setColor(indicatorItem.getColor());
        itemEndPointsPaint.setColor(indicatorItem.getColor());

        // Draw arc
        canvas.drawArc(surfaceRectF, ANGLE_INIT_OFFSET, angle, false, itemArcPaint);
        // Draw top circle
        canvas.drawCircle(minDistViewSize / 2, mItemsLineWidth + mDelta, mItemsLineWidth + mDelta / 2, itemEndPointsPaint);

        canvas.drawCircle((float) (mWheelBoundsRectF.centerX() + (mWheelBoundsRectF.width() / 2) * Math.cos(Math.toRadians(angle + ANGLE_INIT_OFFSET))), (float) (mWheelBoundsRectF.centerY() + (mWheelBoundsRectF.width() / 2) * Math.sin(Math.toRadians(angle + ANGLE_INIT_OFFSET))), mItemsLineWidth + mDelta, itemEndPointsPaint);
    }

    private Drawable getDrawable(final Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getResources().getDrawable(id, context.getTheme());
        } else {
            return getResources().getDrawable(id);
        }
    }

    //*********************************************************************
    // APIs
    //*********************************************************************

    public void setWheelIndicatorItems(List<WheelIndicatorItem> wheelIndicatorItems) {
        if (wheelIndicatorItems == null)
            throw new IllegalArgumentException("wheelIndicatorItems cannot be null");
        this.wheelIndicatorItems = wheelIndicatorItems;
        recalculateItemsAngles();
        invalidate();
    }

    public void setFilledPercent(int mFilledPercent) {
        if (mFilledPercent < 0)
            this.mFilledPercent = 0;
        else if (mFilledPercent > 100)
            this.mFilledPercent = 100;
        else
            this.mFilledPercent = mFilledPercent;
        invalidate();
    }

    public int getFilledPercent() {
        return mFilledPercent;
    }

    public void setItemsLineWidth(int itemLineWidth) {
        if (itemLineWidth <= 0) {
            throw new IllegalArgumentException("itemLineWidth must be greater than 0");
        }
        this.mItemsLineWidth = itemLineWidth;
        mDelta = mCircleWidth - this.mItemsLineWidth;
        invalidate();
    }

    public void setCircleWidth(int circleWidth) {
        if (circleWidth <= 0) {
            throw new IllegalArgumentException("itemLineWidth must be greater than 0");
        }
        this.mCircleWidth = circleWidth;
        mDelta = mCircleWidth - this.mItemsLineWidth;
        invalidate();
    }

    public void addWheelIndicatorItem(WheelIndicatorItem indicatorItem) {
        if (indicatorItem == null)
            throw new IllegalArgumentException("wheelIndicatorItems cannot be null");

        this.wheelIndicatorItems.add(indicatorItem);
        recalculateItemsAngles();
        invalidate();
    }

    public void setBackgroundColor(int color) {
        circleBackgroundPaint = new Paint();
        circleBackgroundPaint.setColor(color);
        invalidate();
    }


    public void notifyDataSetChanged() {
        recalculateItemsAngles();
        invalidate();
    }


    public void startItemsAnimation() {
        ObjectAnimator animation = ObjectAnimator.ofInt(WheelIndicatorView.this, "mFilledPercent", 0, mFilledPercent);
        animation.setDuration(ANIMATION_DURATION);
        animation.setInterpolator(PathInterpolatorCompat.create(0.4F, 0.0F, 0.2F, 1.0F));
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                recalculateItemsAngles();
                invalidate();
            }
        });
        animation.start();
    }

    //*********************************************************************
    // End of class
    //*********************************************************************
}
