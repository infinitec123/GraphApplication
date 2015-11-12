package vibhulabs.shopperbuddy.charts;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
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
 * <p/>
 * Talk to me know what it does
 */
public class CircleIndicatorView extends View {

    private static final String TAG = "CircleIndicatorView";

    private final static int DEFAULT_CENTRE_IMAGE_SIZE = 40;

    private int mViewHeight;
    private int mViewWidth;

    int mMinDistViewSize;
    int mMaxDistViewSize;
    private int translationX;
    private int translationY;
    private RectF mWheelBoundsRectF;

    private Paint mText1Paint;
    private String mText1;
    private int mText1Color;

    private Paint mText2Paint;
    private String mText2;
    private int mText2Color;


    private int mCentreImageWidth = DEFAULT_CENTRE_IMAGE_SIZE;
    private float mMultiplyFactor = 1f;

    @DrawableRes
    private Drawable mCentreDrawable;

    //*********************************************************************
    // Life cycles
    //*********************************************************************


    public CircleIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public CircleIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CircleIndicatorView(Context context) {
        super(context);
        init(null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewHeight = getMeasuredHeight();
        mViewWidth = getMeasuredWidth();

        int availableHeight = (int) (0.7 * mViewHeight); //Only top 70% of height will be given to image
        mMinDistViewSize = Math.min(mViewWidth, availableHeight);
        mMaxDistViewSize = Math.max(mViewWidth, availableHeight);
        mWheelBoundsRectF = new RectF(0, 0, mMinDistViewSize, mMinDistViewSize);
        if (mViewWidth <= availableHeight) {
            this.translationX = 0;
            this.translationY = (mMaxDistViewSize - mMinDistViewSize) / 2;
        } else {
            this.translationY = 0;
            this.translationX = (mMaxDistViewSize - mMinDistViewSize) / 2;
        }


        /*mMinDistViewSize = Math.min(getMeasuredWidth(), getMeasuredHeight());
        mMaxDistViewSize = Math.max(getMeasuredWidth(), getMeasuredHeight());

        if (mViewWidth <= mViewHeight) {
            this.translationX = 0;
            this.translationY = (mMaxDistViewSize - mMinDistViewSize) / 2;
        } else {
            this.translationY = 0;
            this.translationX = (mMaxDistViewSize - mMinDistViewSize) / 2;
        }
        // Adding artificial padding, depending on line width
        mWheelBoundsRectF = new RectF(0, 0, mMinDistViewSize, mMinDistViewSize);*/
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(translationX, translationY);
        //canvas.drawRect(mWheelBoundsRectF, mText2Paint);

        float mCentreImageWidth = (float) ((mWheelBoundsRectF.width() / 2.5) * mMultiplyFactor);

        // Draw centre image
        drawImage((int) mCentreImageWidth, mCentreDrawable, mWheelBoundsRectF.centerX(), mWheelBoundsRectF.centerY(), canvas);

        // Draw text1
        int textPadding = dpToPx(0); // No need of padding because centre image anyway is smaller than mWheelBoundsRectF
        float text1XPosition = (mWheelBoundsRectF.centerX() - (mText1Paint.measureText(mText1) / 2));
        float text1YPosition = mWheelBoundsRectF.centerY() + mWheelBoundsRectF.height() / 2 + textPadding + (mText1Paint.descent() - mText1Paint.ascent());
        canvas.drawText(mText1, text1XPosition, text1YPosition, mText1Paint);

        // Draw text2
        float text2XPosition = (mWheelBoundsRectF.centerX() - (mText1Paint.measureText(mText2) / 2));
        float text2YPosition = text1YPosition + (mText2Paint.descent() - mText2Paint.ascent());
        canvas.drawText(mText2, text2XPosition, text2YPosition, mText2Paint);
    }

    //*********************************************************************
    // Private utility classes
    //*********************************************************************

    private void init(AttributeSet attrs) {
        TypedArray attributesArray = getContext().getTheme()
                .obtainStyledAttributes(attrs, R.styleable.CircleIndicatorView, 0, 0);

        Drawable drawable = attributesArray.getDrawable(R.styleable.CircleIndicatorView_circleImage);
        if (drawable != null) {
            mCentreDrawable = drawable;
        }

        CharSequence text1 = attributesArray.getString(R.styleable.CircleIndicatorView_text1);
        if (text1 != null) {
            mText1 = text1.toString();
        }

        CharSequence text2 = attributesArray.getString(R.styleable.CircleIndicatorView_text2);
        if (text2 != null) {
            mText2 = text2.toString();
        }

        int text1Color = attributesArray.getColor(R.styleable.CircleIndicatorView_text1Color, -1);
        if (text1Color != -1) {
            mText1Color = text1Color;
        }

        int text2Color = attributesArray.getColor(R.styleable.CircleIndicatorView_text2Color, -1);
        if (text2Color != -1) {
            mText2Color = text2Color;
        }

        mMultiplyFactor = attributesArray.getFloat(R.styleable.CircleIndicatorView_weight, 1.0f);
        if (mMultiplyFactor > 1) {
            throw new RuntimeException("Weight cannot be more than 1");
        }

        mText1Paint = new Paint();
        mText1Paint.setColor(mText1Color);
        mText1Paint.setAntiAlias(true);
        mText1Paint.setTextSize(dpToPx(18));
        mText1Paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        mText2Paint = new Paint();
        mText2Paint.setColor(mText1Color);
        mText2Paint.setAntiAlias(true);
        mText2Paint.setTextSize(dpToPx(16));

        attributesArray.recycle();
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

    // *********************************************************************************************
    // Utility methods
    // *********************************************************************************************

    public int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }


    //*********************************************************************
    // APIs
    //*********************************************************************


    //*********************************************************************
    // End of class
    //*********************************************************************
}
