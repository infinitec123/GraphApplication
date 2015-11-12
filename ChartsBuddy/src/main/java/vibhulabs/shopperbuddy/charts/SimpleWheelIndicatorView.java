package vibhulabs.shopperbuddy.charts;

import android.content.Context;
import android.content.res.Resources;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sharath Pandeshwar
 * @since 10/11/2015
 * <p/>
 * Talk to me know what it does
 */
public class SimpleWheelIndicatorView extends View {

    private static final String TAG = "SimpleWheelIndicatorView";

    private final static int ANGLE_INIT_OFFSET = -90;
    private final static int DEFAULT_FILLED_PERCENT = 100;
    private final static int DEFAULT_ITEM_LINE_WIDTH = 25;

    private Paint itemArcPaint;
    private Paint itemEndPointsPaint;
    private List<WheelIndicatorItem> mWheelIndicatorItems;
    private int mViewHeight;
    private int mViewWidth;
    private int mTranslationX;
    private int mTranslationY;
    private RectF mWheelBoundsRectF;
    private ArrayList<Float> wheelItemsAngles;     // calculated angle for each @WheelIndicatorItem
    private int mFilledPercent = 100; // Default is filled full always
    private int mItemsLineWidth = 25;

    @DrawableRes
    private Drawable mCentreIconDrawable;

    private Paint mCentreCirclePaint;
    private int mCentreCircleColor = Color.argb(255, 220, 220, 220);

    private String mCentreText;
    private Paint mCentreTextPaint;
    private int mCentreTextColor = Color.argb(255, 255, 255, 255);

    private Paint mBottomTextPaint;
    private String mText1;
    private String mText2;
    private int mText1Color = Color.argb(255, 0, 0, 0);

    //*********************************************************************
    // Life cycles
    //*********************************************************************


    public SimpleWheelIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public SimpleWheelIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SimpleWheelIndicatorView(Context context) {
        super(context);
        init(null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewHeight = getMeasuredHeight();
        mViewWidth = getMeasuredWidth();

        int availableHeight = (int) (0.7 * mViewHeight); //Only top 70% of height will be given to image
        int mMinDistViewSize = Math.min(mViewWidth, availableHeight);
        int mMaxDistViewSize = Math.max(mViewWidth, availableHeight);
        mWheelBoundsRectF = new RectF(mItemsLineWidth, mItemsLineWidth, mMinDistViewSize - mItemsLineWidth, mMinDistViewSize - mItemsLineWidth);
        if (mViewWidth <= availableHeight) {
            this.mTranslationX = 0;
            this.mTranslationY = (mMaxDistViewSize - mMinDistViewSize) / 2;
        } else {
            this.mTranslationY = 0;
            this.mTranslationX = (mMaxDistViewSize - mMinDistViewSize) / 2;
        }

        /*int minDistViewSize = Math.min(getMeasuredWidth(), getMeasuredHeight());
        int maxDistViewSize = Math.max(getMeasuredWidth(), getMeasuredHeight());

        if (mViewWidth <= mViewHeight) {
            this.traslationX = 0;
            this.traslationY = (maxDistViewSize - minDistViewSize) / 2;
        } else {
            this.traslationX = 0;
            this.traslationX = (maxDistViewSize - minDistViewSize) / 2;
        }
        // Adding artificial padding, depending on line width
        mWheelBoundsRectF = new RectF(mItemsLineWidth, mItemsLineWidth, minDistViewSize - mItemsLineWidth, minDistViewSize - mItemsLineWidth);*/
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.translate(traslationX, 0);
        canvas.translate(this.mTranslationX, this.mTranslationY);
        //canvas.drawRect(mWheelBoundsRectF, mBottomTextPaint);

        /* Draw centre circle */
        float centreCircleWidth = (float) (mWheelBoundsRectF.height() / 3.5);
        canvas.drawCircle(mWheelBoundsRectF.centerX(), mWheelBoundsRectF.centerY(), centreCircleWidth, mCentreCirclePaint);

        /* Draw centre text */
        int delta = 20;
        int textTotalXPosition = (int) (mWheelBoundsRectF.centerX() - mCentreTextPaint.measureText(mCentreText) / 2);
        float textTotalYPosition = mWheelBoundsRectF.centerY() - ((mCentreTextPaint.descent() + mCentreTextPaint.ascent()) / 2) + 2 * delta;
        canvas.drawText(mCentreText, textTotalXPosition, textTotalYPosition, mCentreTextPaint);

        /* Draw centre icon */
        drawImage((int) (centreCircleWidth / 3.5), mCentreIconDrawable, mWheelBoundsRectF.centerX(), (float) (mWheelBoundsRectF.centerY() - 1.5 * delta), canvas);

        /* Draw bottom text1*/
        int textPadding = dpToPx(4);
        float textXPosition = (mWheelBoundsRectF.centerX() - (mBottomTextPaint.measureText(mText1) / 2));
        //float textYPosition = (float) (mViewHeight - 1.5*textDelta - (mBottomTextPaint.descent() - mBottomTextPaint.ascent()));
        float text1YPosition = mWheelBoundsRectF.centerY() + mWheelBoundsRectF.height() / 2 + textPadding + mItemsLineWidth + (mBottomTextPaint.descent() - mBottomTextPaint.ascent());
        canvas.drawText(mText1, textXPosition, text1YPosition, mBottomTextPaint);

        /* Draw bottom text2*/
        float text2XPosition = (mWheelBoundsRectF.centerX() - (mBottomTextPaint.measureText(mText2) / 2));
        float text2YPosition = text1YPosition + (mBottomTextPaint.descent() - mBottomTextPaint.ascent());
        canvas.drawText(mText2, text2XPosition, text2YPosition, mBottomTextPaint);

        /* Draw Indicator Items */
        drawIndicatorItems(canvas);
    }

    //*********************************************************************
    // Private utility classes
    //*********************************************************************

    private void init(AttributeSet attrs) {

        TypedArray attributesArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.SimpleWheelIndicatorView, 0, 0);

        int itemsLineWidth = attributesArray.getDimensionPixelSize(R.styleable.SimpleWheelIndicatorView_sw_itemsLineWidth, DEFAULT_ITEM_LINE_WIDTH);
        setItemsLineWidth(itemsLineWidth);

        int filledPercent = attributesArray.getInt(R.styleable.SimpleWheelIndicatorView_sw_filledPercent, DEFAULT_FILLED_PERCENT);
        setFilledPercent(filledPercent);

        int bgColor = attributesArray.getColor(R.styleable.SimpleWheelIndicatorView_sw_backgroundColor, -1);
        if (bgColor != -1) {
            setBackgroundColor(bgColor);
        }

        CharSequence centreText = attributesArray.getString(R.styleable.SimpleWheelIndicatorView_sw_centreText);
        if (centreText != null) {
            mCentreText = centreText.toString();
        }

        int centreCircleColor = attributesArray.getColor(R.styleable.SimpleWheelIndicatorView_sw_centreColor, -1);
        if (centreCircleColor != -1) {
            mCentreCircleColor = centreCircleColor;
        }

        int centreTextColor = attributesArray.getColor(R.styleable.SimpleWheelIndicatorView_sw_centreTextColor, -1);
        if (centreTextColor != -1) {
            mCentreTextColor = centreTextColor;
        }

        Drawable drawable = attributesArray.getDrawable(R.styleable.SimpleWheelIndicatorView_sw_centreImage);
        if (drawable != null) {
            mCentreIconDrawable = drawable;
        }

        CharSequence text1 = attributesArray.getString(R.styleable.SimpleWheelIndicatorView_sw_text1);
        if (text1 != null) {
            mText1 = text1.toString();
        }

        CharSequence text2 = attributesArray.getString(R.styleable.SimpleWheelIndicatorView_sw_text2);
        if (text2 != null) {
            mText2 = text2.toString();
        }

        int text1Color = attributesArray.getColor(R.styleable.SimpleWheelIndicatorView_sw_bottomTextColor, -1);
        if (text1Color != -1) {
            mText1Color = text1Color;
        }

        this.mWheelIndicatorItems = new ArrayList<>();
        this.wheelItemsAngles = new ArrayList<>();

        itemArcPaint = new Paint();
        itemArcPaint.setStyle(Paint.Style.STROKE);
        itemArcPaint.setStrokeWidth(itemsLineWidth * 2);
        itemArcPaint.setAntiAlias(true);

        itemEndPointsPaint = new Paint();
        itemEndPointsPaint.setAntiAlias(true);

        mCentreCirclePaint = new Paint();
        mCentreCirclePaint.setColor(mCentreCircleColor);
        mCentreCirclePaint.setStyle(Paint.Style.FILL);
        mCentreCirclePaint.setAntiAlias(true);

        mCentreTextPaint = new Paint();
        mCentreTextPaint.setColor(mCentreTextColor);
        mCentreTextPaint.setAntiAlias(true);
        mCentreTextPaint.setTextSize(36);
        mCentreTextPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        mBottomTextPaint = new Paint();
        mBottomTextPaint.setColor(mText1Color);
        mBottomTextPaint.setAntiAlias(true);
        mBottomTextPaint.setTextSize(50);
        //mBottomTextPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        attributesArray.recycle();
    }

    private void recalculateItemsAngles() {
        wheelItemsAngles.clear();
        float total = 0;
        float angleAccumulated = 0;

        for (WheelIndicatorItem item : mWheelIndicatorItems) {
            total += item.getWeight();
        }
        for (int i = 0; i < mWheelIndicatorItems.size(); ++i) {
            float normalizedValue = mWheelIndicatorItems.get(i).getWeight() / total;
            float angle = 360 * normalizedValue * mFilledPercent / 100;
            wheelItemsAngles.add(angle + angleAccumulated);
            angleAccumulated += angle;
        }
    }


    private void drawIndicatorItems(Canvas canvas) {
        if (mWheelIndicatorItems.size() > 0) {
            for (int i = mWheelIndicatorItems.size() - 1; i >= 0; i--) { // Iterate backward to overlap larger items
                draw(mWheelIndicatorItems.get(i), mWheelBoundsRectF, wheelItemsAngles.get(i), canvas);
            }
        }
    }


    private void draw(WheelIndicatorItem indicatorItem, RectF surfaceRectF, float angle, Canvas canvas) {
        itemArcPaint.setColor(indicatorItem.getColor());
        itemEndPointsPaint.setColor(indicatorItem.getColor());
        canvas.drawArc(surfaceRectF, ANGLE_INIT_OFFSET, angle, false, itemArcPaint);
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

    public void setWheelIndicatorItems(List<WheelIndicatorItem> wheelIndicatorItems) {
        if (wheelIndicatorItems == null)
            throw new IllegalArgumentException("mWheelIndicatorItems cannot be null");
        this.mWheelIndicatorItems = wheelIndicatorItems;
        recalculateItemsAngles();
        invalidate();
    }

    public void clearIndicators() {
        this.mWheelIndicatorItems.clear();
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
        invalidate();
    }

    public void addWheelIndicatorItem(WheelIndicatorItem indicatorItem) {
        if (indicatorItem == null) {
            throw new IllegalArgumentException("mWheelIndicatorItems cannot be null");
        }
        this.mWheelIndicatorItems.add(indicatorItem);
        recalculateItemsAngles();
        invalidate();
    }

    public void addWheelIndicatorItems(ArrayList<WheelIndicatorItem> indicatorItems) {
        if (indicatorItems == null) {
            throw new IllegalArgumentException("mWheelIndicatorItems cannot be null");
        }

        this.mWheelIndicatorItems.addAll(indicatorItems);
        recalculateItemsAngles();
        invalidate();
    }

    public void notifyDataSetChanged() {
        recalculateItemsAngles();
        invalidate();
    }


    // *********************************************************************************************
    // Utility methods
    // *********************************************************************************************

    public int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }


    //*********************************************************************
    // End of class
    //*********************************************************************
}
