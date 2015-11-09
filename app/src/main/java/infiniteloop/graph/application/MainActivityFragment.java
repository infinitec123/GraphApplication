package infiniteloop.graph.application;

import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import vibhulabs.shopperbuddy.charts.LineIndicatorView;
import vibhulabs.shopperbuddy.charts.WheelIndicatorItem;
import vibhulabs.shopperbuddy.charts.WheelIndicatorView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public static final String TAG = "StatisticsFragment";

    public static final int TIME_INTERVAL = 4000;
    ArrayList<WheelIndicatorItem> mMenWheelIndicatorItems = new ArrayList<>();
    ArrayList<WheelIndicatorItem> mWomenWheelIndicatorItems = new ArrayList<>();

    @Bind(R.id.wheel_indicator_view)
    WheelIndicatorView mMenWheelIndicatorView;

    @Bind(R.id.wheel_female_indicator_view)
    WheelIndicatorView mWomenWheelIndicatorView;

    @Bind(R.id.line_indicator_view_mobiles)
    LineIndicatorView mMobileLineIndicatorView;

    @Bind(R.id.line_indicator_view_men)
    LineIndicatorView mMenLineIndicatorView;

    @Bind(R.id.line_indicator_view_women)
    LineIndicatorView mWomenLineIndicatorView;


    Handler mHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, v);

        mHandler = new Handler();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "Setting data-1");
                setDummyData1();
                mHandler.postDelayed(this, 2 * TIME_INTERVAL);
            }
        }, TIME_INTERVAL);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "Setting data-2");
                setDummyData2();
                mHandler.postDelayed(this, 2 * TIME_INTERVAL);
            }
        }, TIME_INTERVAL * 2);
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacksAndMessages(null);
    }

    private void setDummyData1() {
        mMenWheelIndicatorView.clearIndicators();
        mWomenWheelIndicatorView.clearIndicators();
        mMenWheelIndicatorItems.clear();
        mWomenWheelIndicatorItems.clear();

        mMenWheelIndicatorItems.add(new WheelIndicatorItem("Toddlers", 400f, Color.parseColor("#009D96"), R.drawable.icon_toddler));
        mMenWheelIndicatorItems.add(new WheelIndicatorItem("Teens", 960f, Color.parseColor("#ADCF4A"), R.drawable.icon_teen));
        mMenWheelIndicatorItems.add(new WheelIndicatorItem("Youth", 740f, Color.parseColor("#1CBBB4"), R.drawable.icon_youth));
        mMenWheelIndicatorItems.add(new WheelIndicatorItem("Mid Life", 250f, Color.parseColor("#005D55"), R.drawable.icon_mid_life));
        mMenWheelIndicatorItems.add(new WheelIndicatorItem("Seniors", 198f, Color.parseColor("#3CB878"), R.drawable.icon_seniors));
        mMenWheelIndicatorView.addWheelIndicatorItems(mMenWheelIndicatorItems);


        mWomenWheelIndicatorItems.add(new WheelIndicatorItem("Toddlers", 198f, Color.parseColor("#009D96"), R.drawable.icon_toddler));
        mWomenWheelIndicatorItems.add(new WheelIndicatorItem("Teens", 250f, Color.parseColor("#ADCF4A"), R.drawable.icon_teen));
        mWomenWheelIndicatorItems.add(new WheelIndicatorItem("Youth", 740f, Color.parseColor("#1CBBB4"), R.drawable.icon_youth));
        mWomenWheelIndicatorItems.add(new WheelIndicatorItem("Mid Life", 960f, Color.parseColor("#005D55"), R.drawable.icon_mid_life));
        mWomenWheelIndicatorItems.add(new WheelIndicatorItem("Seniors", 400f, Color.parseColor("#3CB878"), R.drawable.icon_seniors));
        mWomenWheelIndicatorView.addWheelIndicatorItems(mWomenWheelIndicatorItems);


        /*mMenWheelIndicatorView.addWheelIndicatorItem(new WheelIndicatorItem("Toddlers", 400f, Color.parseColor("#009D96"), R.drawable.icon_toddler));
        mMenWheelIndicatorView.addWheelIndicatorItem(new WheelIndicatorItem("Teens", 960f, Color.parseColor("#ADCF4A"), R.drawable.icon_teen));
        mMenWheelIndicatorView.addWheelIndicatorItem(new WheelIndicatorItem("Youth", 740f, Color.parseColor("#1CBBB4"), R.drawable.icon_youth));
        mMenWheelIndicatorView.addWheelIndicatorItem(new WheelIndicatorItem("Mid Life", 250f, Color.parseColor("#005D55"), R.drawable.icon_mid_life));
        mMenWheelIndicatorView.addWheelIndicatorItem(new WheelIndicatorItem("Seniors", 198f, Color.parseColor("#3CB878"), R.drawable.icon_seniors));

        mWomenWheelIndicatorView.addWheelIndicatorItem(new WheelIndicatorItem("Toddlers", 198f, Color.parseColor("#009D96"), R.drawable.icon_toddler));
        mWomenWheelIndicatorView.addWheelIndicatorItem(new WheelIndicatorItem("Teens", 250f, Color.parseColor("#ADCF4A"), R.drawable.icon_teen));
        mWomenWheelIndicatorView.addWheelIndicatorItem(new WheelIndicatorItem("Youth", 740f, Color.parseColor("#1CBBB4"), R.drawable.icon_youth));
        mWomenWheelIndicatorView.addWheelIndicatorItem(new WheelIndicatorItem("Mid Life", 960f, Color.parseColor("#005D55"), R.drawable.icon_mid_life));
        mWomenWheelIndicatorView.addWheelIndicatorItem(new WheelIndicatorItem("Seniors", 400f, Color.parseColor("#3CB878"), R.drawable.icon_seniors));*/

        mMobileLineIndicatorView.refreshData("31%", "69%", "3.3k", 0.31f);
        mMenLineIndicatorView.refreshData("48%", "52%", "7.6k", 0.48f);
        mWomenLineIndicatorView.refreshData("64%", "36%", "12.2k", 0.64f);
    }


    private void setDummyData2() {
        mMenWheelIndicatorView.clearIndicators();
        mWomenWheelIndicatorView.clearIndicators();

        mMenWheelIndicatorItems.clear();
        mWomenWheelIndicatorItems.clear();

        mMenWheelIndicatorItems.add(new WheelIndicatorItem("Toddlers", 312f, Color.parseColor("#009D96"), R.drawable.icon_toddler));
        mMenWheelIndicatorItems.add(new WheelIndicatorItem("Teens", 1260f, Color.parseColor("#ADCF4A"), R.drawable.icon_teen));
        mMenWheelIndicatorItems.add(new WheelIndicatorItem("Youth", 640f, Color.parseColor("#1CBBB4"), R.drawable.icon_youth));
        mMenWheelIndicatorItems.add(new WheelIndicatorItem("Mid Life", 650f, Color.parseColor("#005D55"), R.drawable.icon_mid_life));
        mMenWheelIndicatorItems.add(new WheelIndicatorItem("Seniors", 498f, Color.parseColor("#3CB878"), R.drawable.icon_seniors));
        mMenWheelIndicatorView.addWheelIndicatorItems(mMenWheelIndicatorItems);


        mWomenWheelIndicatorItems.add(new WheelIndicatorItem("Toddlers", 402f, Color.parseColor("#009D96"), R.drawable.icon_toddler));
        mWomenWheelIndicatorItems.add(new WheelIndicatorItem("Teens", 614f, Color.parseColor("#ADCF4A"), R.drawable.icon_teen));
        mWomenWheelIndicatorItems.add(new WheelIndicatorItem("Youth", 340f, Color.parseColor("#1CBBB4"), R.drawable.icon_youth));
        mWomenWheelIndicatorItems.add(new WheelIndicatorItem("Mid Life", 1045f, Color.parseColor("#005D55"), R.drawable.icon_mid_life));
        mWomenWheelIndicatorItems.add(new WheelIndicatorItem("Seniors", 306f, Color.parseColor("#3CB878"), R.drawable.icon_seniors));
        mWomenWheelIndicatorView.addWheelIndicatorItems(mWomenWheelIndicatorItems);

        /*mMenWheelIndicatorView.addWheelIndicatorItem(new WheelIndicatorItem("Toddlers", 312f, Color.parseColor("#009D96"), R.drawable.icon_toddler));
        mMenWheelIndicatorView.addWheelIndicatorItem(new WheelIndicatorItem("Teens", 1260f, Color.parseColor("#ADCF4A"), R.drawable.icon_teen));
        mMenWheelIndicatorView.addWheelIndicatorItem(new WheelIndicatorItem("Youth", 640f, Color.parseColor("#1CBBB4"), R.drawable.icon_youth));
        mMenWheelIndicatorView.addWheelIndicatorItem(new WheelIndicatorItem("Mid Life", 650f, Color.parseColor("#005D55"), R.drawable.icon_mid_life));
        mMenWheelIndicatorView.addWheelIndicatorItem(new WheelIndicatorItem("Seniors", 498f, Color.parseColor("#3CB878"), R.drawable.icon_seniors));

        mWomenWheelIndicatorView.addWheelIndicatorItem(new WheelIndicatorItem("Toddlers", 402f, Color.parseColor("#009D96"), R.drawable.icon_toddler));
        mWomenWheelIndicatorView.addWheelIndicatorItem(new WheelIndicatorItem("Teens", 614f, Color.parseColor("#ADCF4A"), R.drawable.icon_teen));
        mWomenWheelIndicatorView.addWheelIndicatorItem(new WheelIndicatorItem("Youth", 340f, Color.parseColor("#1CBBB4"), R.drawable.icon_youth));
        mWomenWheelIndicatorView.addWheelIndicatorItem(new WheelIndicatorItem("Mid Life", 1045f, Color.parseColor("#005D55"), R.drawable.icon_mid_life));
        mWomenWheelIndicatorView.addWheelIndicatorItem(new WheelIndicatorItem("Seniors", 306f, Color.parseColor("#3CB878"), R.drawable.icon_seniors));*/

        mMobileLineIndicatorView.refreshData("18%", "82%", "3.1k", 0.18f);
        mMenLineIndicatorView.refreshData("45%", "55%", "6.4k", 0.45f);
        mWomenLineIndicatorView.refreshData("72%", "28%", "12.8k", 0.72f);
    }
}