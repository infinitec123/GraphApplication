package infiniteloop.graph.application;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import vibhulabs.shopperbuddy.charts.WheelIndicatorItem;
import vibhulabs.shopperbuddy.charts.WheelIndicatorView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public static final String TAG = "StatisticsFragment";

    @Bind(R.id.wheel_indicator_view)
    WheelIndicatorView mMenWheelIndicatorView;

    @Bind(R.id.wheel_female_indicator_view)
    WheelIndicatorView mWomenWheelIndicatorView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, v);

        setDummyData();
        return v;
    }

    private void setDummyData() {
        mMenWheelIndicatorView.addWheelIndicatorItem(new WheelIndicatorItem("Toddlers", 400f, Color.parseColor("#009D96"), R.drawable.icon_toddler));
        mMenWheelIndicatorView.addWheelIndicatorItem(new WheelIndicatorItem("Teens", 960f, Color.parseColor("#ADCF4A"), R.drawable.icon_teen));
        mMenWheelIndicatorView.addWheelIndicatorItem(new WheelIndicatorItem("Youth", 740f, Color.parseColor("#1CBBB4"), R.drawable.icon_youth));
        mMenWheelIndicatorView.addWheelIndicatorItem(new WheelIndicatorItem("Mid Life", 250f, Color.parseColor("#005D55"), R.drawable.icon_mid_life));
        mMenWheelIndicatorView.addWheelIndicatorItem(new WheelIndicatorItem("Seniors", 198f, Color.parseColor("#3CB878"), R.drawable.icon_seniors));

        mWomenWheelIndicatorView.addWheelIndicatorItem(new WheelIndicatorItem("Toddlers", 198f, Color.parseColor("#009D96"), R.drawable.icon_toddler));
        mWomenWheelIndicatorView.addWheelIndicatorItem(new WheelIndicatorItem("Teens", 250f, Color.parseColor("#ADCF4A"), R.drawable.icon_teen));
        mWomenWheelIndicatorView.addWheelIndicatorItem(new WheelIndicatorItem("Youth", 740f, Color.parseColor("#1CBBB4"), R.drawable.icon_youth));
        mWomenWheelIndicatorView.addWheelIndicatorItem(new WheelIndicatorItem("Mid Life", 960f, Color.parseColor("#005D55"), R.drawable.icon_mid_life));
        mWomenWheelIndicatorView.addWheelIndicatorItem(new WheelIndicatorItem("Seniors", 400f, Color.parseColor("#3CB878"), R.drawable.icon_seniors));


    }
}
