package vibhulabs.shopperbuddy.charts;


public class WheelIndicatorItem {

    private String mName;
    private float mWeight;
    private int mColor;

    public WheelIndicatorItem() {
        mWeight = 0;
    }

    public WheelIndicatorItem(String name, float weight, int color) {
        if (weight < 0)
            throw new IllegalArgumentException("weight value should be positive");

        this.mWeight = weight;
        this.mColor = color;
        this.mName = name;
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

}
