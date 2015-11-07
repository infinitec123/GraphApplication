package vibhulabs.shopperbuddy.charts;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Sharath Pandeshwar
 * @since 07/11/2015
 * <p>
 * Talk to me know what it does
 */
public class IndicatorView extends View {

    private static final String TAG = "IndicatorView";

    private final static int DEFAULT_CENTRE_IMAGE_SIZE = 40;

    int mMinDistViewSize;
    int mMaxDistViewSize;
    private int translationX;
    private int translationY;
    private RectF mWheelBoundsRectF;

    private int mIndicatorColor = Color.argb(255, 220, 220, 220);
    private Paint mCircleBackgroundPaint;

    private Paint mIndicatorTextPaint;
    private String mIndicatorText;
    private int mTextIndicatorColor = Color.argb(255, 0, 0, 0);


    private int mCentreImageWidth = DEFAULT_CENTRE_IMAGE_SIZE;

    @DrawableRes
    private Drawable mIndicatorDrawable;

    //*********************************************************************
    // Life cycles
    //*********************************************************************


    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public IndicatorView(Context context) {
        super(context);
        init(null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int viewHeight = getMeasuredHeight();
        int viewWidth = getMeasuredWidth();

        mMinDistViewSize = Math.min(getMeasuredWidth(), getMeasuredHeight());
        mMaxDistViewSize = Math.max(getMeasuredWidth(), getMeasuredHeight());

        if (viewWidth <= viewHeight) {
            this.translationX = 0;
            this.translationY = (mMaxDistViewSize - mMinDistViewSize) / 2;
        } else {
            this.translationX = 0;
            this.translationX = (mMaxDistViewSize - mMinDistViewSize) / 2;
        }
        // Adding artificial padding, depending on line width
        mWheelBoundsRectF = new RectF(0, 0, mMinDistViewSize, mMinDistViewSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(translationX, translationY);
        float circleWidth = (float) (mWheelBoundsRectF.width() / 2.5);
        int indicatorTextHeight = (int) (circleWidth / 5);

        if (mCircleBackgroundPaint != null) {
            canvas.drawCircle(mWheelBoundsRectF.centerX(), mWheelBoundsRectF.centerY() - indicatorTextHeight, circleWidth, mCircleBackgroundPaint);
        }

        /* Draw centre image */
        mCentreImageWidth = (int) (circleWidth / 2.5);
        drawImage(mCentreImageWidth, mIndicatorDrawable, mWheelBoundsRectF.centerX(), mWheelBoundsRectF.centerY() - indicatorTextHeight, canvas);

        /* Draw explanatory text */
        int textXPosition = (int) ((mMinDistViewSize / 2) - (mIndicatorTextPaint.measureText(mIndicatorText) / 2));
        float textYPosition = mMinDistViewSize;
        //int yPos = (int) ((canvas.getHeight() / 2) - ((mIndicatorTextPaint.descent() + textPaint.ascent()) / 2)) ;
        canvas.drawText(mIndicatorText, textXPosition, textYPosition, mIndicatorTextPaint);
    }

    //*********************************************************************
    // Private utility classes
    //*********************************************************************

    private void init(AttributeSet attrs) {
        TypedArray attributesArray = getContext().getTheme()
                .obtainStyledAttributes(attrs, R.styleable.IndicatorView, 0, 0);

        CharSequence indicatorText = attributesArray.getString(R.styleable.IndicatorView_indicatorText);
        if (indicatorText != null) {
            mIndicatorText = indicatorText.toString();
        }

        Drawable drawable = attributesArray.getDrawable(R.styleable.IndicatorView_indicatorImage);
        if (drawable != null) {
            mIndicatorDrawable = drawable;
        }

        int indicatorColor = attributesArray.getColor(R.styleable.IndicatorView_indicatorColor, -1);
        if (indicatorColor != -1) {
            mIndicatorColor = indicatorColor;
        }

        int textIndicatorColor = attributesArray.getColor(R.styleable.IndicatorView_indicatorTextColor, -1);
        if (textIndicatorColor != -1) {
            mTextIndicatorColor = textIndicatorColor;
        }

        mCircleBackgroundPaint = new Paint();
        mCircleBackgroundPaint.setColor(mIndicatorColor);
        mCircleBackgroundPaint.setStyle(Paint.Style.FILL);
        mCircleBackgroundPaint.setAntiAlias(true);

        mIndicatorTextPaint = new Paint();
        mIndicatorTextPaint.setColor(mTextIndicatorColor);
        mIndicatorTextPaint.setAntiAlias(true);
        mIndicatorTextPaint.setTextSize(36);
        mIndicatorTextPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
    }

    /**
     * @param centreBound    Size of the image that will be drawn
     * @param centreDrawable Drawable resource of the resource which should be drawn
     * @param x              X centre of image where it should be drawn.
     * @param y              Y centre of image where it should be drawn.
     * @param canvas
     */
    private void drawImage(int centreBound, Drawable centreDrawable, float x, float y, Canvas canvas) {
        if (centreDrawable != null) {
            int left = (int) (x - centreBound);
            int top = (int) (y - centreBound);
            int right = (int) (x + centreBound);
            int bottom = (int) (y + centreBound);

            centreDrawable.setBounds(left, top, right, bottom);
            centreDrawable.draw(canvas);
        }
    }

    //*********************************************************************
    // APIs
    //*********************************************************************

    public void setIndicatorColor(int color) {
        mIndicatorColor = color;
        mCircleBackgroundPaint = new Paint();
        mCircleBackgroundPaint.setColor(mIndicatorColor);
        mCircleBackgroundPaint.setStyle(Paint.Style.FILL);
        mCircleBackgroundPaint.setAntiAlias(true);
        invalidate();
    }

    public void setIndicatorText(String s) {
        mIndicatorText = s;
        invalidate();
    }

    public void notifyDataSetChanged() {
        invalidate();
    }


    //*********************************************************************
    // End of class
    //*********************************************************************
}
