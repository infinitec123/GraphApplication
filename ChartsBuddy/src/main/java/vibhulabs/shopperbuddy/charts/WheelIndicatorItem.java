package vibhulabs.shopperbuddy.charts;


import android.support.annotation.DrawableRes;

public class WheelIndicatorItem {

    private String mName;
    private float mWeight;
    private int mColor;

    @DrawableRes
    private int mDrawableRes;

    public WheelIndicatorItem() {
        mWeight = 0;
    }

    public WheelIndicatorItem(String name, float weight, int color, @DrawableRes int drawableRes) {
        if (weight < 0)
            throw new IllegalArgumentException("weight value should be positive");

        this.mWeight = weight;
        this.mColor = color;
        this.mName = name;
        this.mDrawableRes = drawableRes;
    }

    public void setWeight(float weight) {
        if (weight < 0)
            throw new IllegalArgumentException("weight value should be positive");

        this.mWeight = weight;
    }

    public float getWeight() {
        return mWeight;
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
