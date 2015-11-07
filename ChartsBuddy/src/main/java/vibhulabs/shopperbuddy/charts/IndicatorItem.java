package vibhulabs.shopperbuddy.charts;


import android.support.annotation.DrawableRes;

public class IndicatorItem {

    private String mName;
    private int mColor;

    @DrawableRes
    private int mDrawableRes;


    public IndicatorItem(String name, int color, @DrawableRes int drawableRes) {
        this.mColor = color;
        this.mName = name;
        this.mDrawableRes = drawableRes;
    }

    public void setColor(int color) {
        this.mColor = color;
    }

    public int getColor() {
        return mColor;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public int getDrawableRes() {
        return mDrawableRes;
    }

    public void setDrawableRes(int mDrawableRes) {
        this.mDrawableRes = mDrawableRes;
    }
}
