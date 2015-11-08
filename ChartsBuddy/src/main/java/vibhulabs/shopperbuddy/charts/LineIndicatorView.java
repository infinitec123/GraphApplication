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
 * <p/>
 * Talk to me to know what it does
 */
public class LineIndicatorView extends View {

    private static final String TAG = "LineIndicatorView";

    private final static int DEFAULT_LEFT_IMAGE_SIZE = 40;

    int mMinDistViewSize;
    int mMaxDistViewSize;
    private int translationX;
    private int translationY;
    private RectF mBoundsRectF;

    public static final int LEFT_CIRCLE_COLOR = Color.argb(255, 220, 220, 220);
    private Paint mLeftCirclePaint;
    private int mLeftCircleColor = LEFT_CIRCLE_COLOR;

    private Paint mCategory1Paint;
    private Paint mCategory1TextPaint;
    private String mCategory1Text;
    private int mCategory1Color = Color.argb(255, 236, 0, 140);
    private float mCategory1Percentage = 0.5f;

    private Paint mCategory2Paint;
    private Paint mCategory2TextPaint;
    private String mCategory2Text;
    private int mCategory2Color = Color.argb(255, 68, 140, 203);


    private Paint mCategoryTotalTextPaint;
    private String mCategoryTotalText;
    private String mCategoryTitleText;
    private int mCategoryTotalColor = Color.argb(255, 220, 220, 220);


    @DrawableRes
    private Drawable mCategoryDrawable;

    //*********************************************************************
    // Life cycles
    //*********************************************************************


    public LineIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public LineIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public LineIndicatorView(Context context) {
        super(context);
        init(null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mMinDistViewSize = Math.min(getMeasuredWidth(), getMeasuredHeight());
        mMaxDistViewSize = Math.max(getMeasuredWidth(), getMeasuredHeight());

        // Adding artificial padding, depending on line width
        mBoundsRectF = new RectF(0, 0, mMinDistViewSize, mMinDistViewSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        /**
         * TODO: Solve this cropping issue
         */
        int delta = 8;

        /**
         * TODO: Remove this hard coding
         */
        float mTextBlockWidth = 120;


        float circleRadius = (float) (mMinDistViewSize / 2);
        float rectangularBlockHeight = (float) (mMinDistViewSize / 3);

        float topLeftXPos = 2 * circleRadius - delta;
        float topLeftYPos = circleRadius - (rectangularBlockHeight / 2);
        float bottomRightXPos = mMaxDistViewSize - mTextBlockWidth;
        float bottomRightYPos = circleRadius + (rectangularBlockHeight / 2);
        float blockWidth = bottomRightXPos - topLeftXPos;
        float block1Width = blockWidth * mCategory1Percentage;
        float block1XEndPos = topLeftXPos + block1Width;

        /* Draw left (category-1) block */
        canvas.drawRect(topLeftXPos, topLeftYPos, block1XEndPos, bottomRightYPos, mCategory1Paint);

        /* Draw left (category-1) text */
        int text1XPosition = (int) (block1XEndPos - 3 * mCategory1Paint.measureText(mCategory1Text));
        float text1YPosition = (float) (bottomRightYPos - 1.5 * ((mCategory1TextPaint.descent() + mCategory1TextPaint.ascent())));
        canvas.drawText(mCategory1Text, text1XPosition, text1YPosition, mCategory1TextPaint);

        /* Draw right (category-2) block */
        canvas.drawRect(block1XEndPos, topLeftYPos, bottomRightXPos, bottomRightYPos, mCategory2Paint);

        /* Draw right (category-2) text */
        int text2XPosition = (int) (bottomRightXPos - 3 * mCategory2Paint.measureText(mCategory2Text));
        float text2YPosition = (float) (bottomRightYPos - 1.5 * ((mCategory2TextPaint.descent() + mCategory2TextPaint.ascent())));
        canvas.drawText(mCategory2Text, text2XPosition, text2YPosition, mCategory2TextPaint);

        /* Draw category title text */
        float textTitleXPosition = topLeftXPos + delta;
        float textTitleYPosition = (float) (topLeftYPos - 1.5 * mCategoryTotalTextPaint.descent());
        canvas.drawText(mCategoryTitleText, textTitleXPosition, textTitleYPosition, mCategoryTotalTextPaint);

        /* Draw category total text */
        int textTotalXPosition = (int) (mMaxDistViewSize - mCategoryTotalTextPaint.measureText(mCategoryTotalText));
        float textTotalYPosition = (float) (bottomRightYPos - rectangularBlockHeight / 2 + mCategoryTotalTextPaint.descent()); //+ (mCategoryTotalTextPaint.descent() + mCategoryTotalTextPaint.ascent()) / 2);
        canvas.drawText(mCategoryTotalText, textTotalXPosition, textTotalYPosition, mCategoryTotalTextPaint);

        /* Draw left image and circle around it */
        canvas.drawCircle(circleRadius, circleRadius, circleRadius - delta, mLeftCirclePaint);
        drawImage((int) (circleRadius / 2), mCategoryDrawable, circleRadius, circleRadius, canvas);
    }

    //*********************************************************************
    // Private utility classes
    //*********************************************************************

    private void init(AttributeSet attrs) {
        TypedArray attributesArray = getContext().getTheme()
                .obtainStyledAttributes(attrs, R.styleable.LineIndicatorView, 0, 0);

        Drawable drawable = attributesArray.getDrawable(R.styleable.LineIndicatorView_categoryImage);
        if (drawable != null) {
            mCategoryDrawable = drawable;
        }

        int leftCircleColor = attributesArray.getColor(R.styleable.LineIndicatorView_leftCircleColor, -1);
        if (leftCircleColor != -1) {
            mLeftCircleColor = leftCircleColor;
            mCategoryTotalColor = leftCircleColor;
        }

        int category1Color = attributesArray.getColor(R.styleable.LineIndicatorView_category1Color, -1);
        if (category1Color != -1) {
            mCategory1Color = category1Color;
        }

        int category2Color = attributesArray.getColor(R.styleable.LineIndicatorView_category2Color, -1);
        if (category2Color != -1) {
            mCategory2Color = category2Color;
        }

        CharSequence category1Text = attributesArray.getString(R.styleable.LineIndicatorView_category1Text);
        if (category1Text != null) {
            mCategory1Text = category1Text.toString();
        }

        CharSequence category2Text = attributesArray.getString(R.styleable.LineIndicatorView_category2Text);
        if (category2Text != null) {
            mCategory2Text = category2Text.toString();
        }

        CharSequence categoryTotalText = attributesArray.getString(R.styleable.LineIndicatorView_categoryTotalText);
        if (categoryTotalText != null) {
            mCategoryTotalText = categoryTotalText.toString();
        }

        CharSequence categoryTitleText = attributesArray.getString(R.styleable.LineIndicatorView_categoryText);
        if (categoryTitleText != null) {
            mCategoryTitleText = categoryTitleText.toString();
        }

        mCategory1Percentage = attributesArray.getFloat(R.styleable.LineIndicatorView_category1Percentage, 0.5f);

        mLeftCirclePaint = new Paint();
        mLeftCirclePaint.setColor(mLeftCircleColor);
        mLeftCirclePaint.setStyle(Paint.Style.STROKE);
        mLeftCirclePaint.setStrokeWidth(6);
        mLeftCirclePaint.setAntiAlias(true);

        mCategory1Paint = new Paint();
        mCategory1Paint.setColor(mCategory1Color);
        mCategory1Paint.setStyle(Paint.Style.FILL);
        mCategory1Paint.setAntiAlias(true);

        mCategory2Paint = new Paint();
        mCategory2Paint.setColor(mCategory2Color);
        mCategory2Paint.setStyle(Paint.Style.FILL);
        mCategory2Paint.setAntiAlias(true);

        mCategory1TextPaint = new Paint();
        mCategory1TextPaint.setColor(mCategory1Color);
        mCategory1TextPaint.setAntiAlias(true);
        mCategory1TextPaint.setTextSize(36);

        mCategory2TextPaint = new Paint();
        mCategory2TextPaint.setColor(mCategory2Color);
        mCategory2TextPaint.setAntiAlias(true);
        mCategory2TextPaint.setTextSize(36);

        mCategoryTotalTextPaint = new Paint();
        mCategoryTotalTextPaint.setColor(mCategoryTotalColor);
        mCategoryTotalTextPaint.setAntiAlias(true);
        mCategoryTotalTextPaint.setTextSize(36);
        mCategoryTotalTextPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

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

    //*********************************************************************
    // APIs
    //*********************************************************************

    public void refreshData(String category1Text, String category2Text, String total, float category1Percentage) {
        mCategoryTotalText = total;
        mCategory1Text = category1Text;
        mCategory2Text = category2Text;
        mCategory1Percentage = category1Percentage;

        invalidate();
    }


    //*********************************************************************
    // End of class
    //*********************************************************************
}
