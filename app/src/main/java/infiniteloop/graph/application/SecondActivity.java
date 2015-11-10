package infiniteloop.graph.application;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import vibhulabs.shopperbuddy.charts.SimpleWheelIndicatorView;
import vibhulabs.shopperbuddy.charts.WheelIndicatorItem;

public class SecondActivity extends AppCompatActivity {

    @Bind(R.id.chart)
    LineChart mLineChart;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.simple_wheel_indicator_view_1)
    SimpleWheelIndicatorView mTargetedWheelIndicatorView;

    @Bind(R.id.simple_wheel_indicator_view_2)
    SimpleWheelIndicatorView mViewedWheelIndicatorView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);
        setupToolbar();

        customiseChart();
        setData1();

        setTargetedData();
    }

    private void setTargetedData() {
        mTargetedWheelIndicatorView.clearIndicators();
        mViewedWheelIndicatorView.clearIndicators();

        mTargetedWheelIndicatorView.addWheelIndicatorItem(new WheelIndicatorItem("Women", 198f, Color.parseColor("#EC008C"), -1));
        mTargetedWheelIndicatorView.addWheelIndicatorItem(new WheelIndicatorItem("Men", 112f, Color.parseColor("#448CCB"), -1));

        mViewedWheelIndicatorView.addWheelIndicatorItem(new WheelIndicatorItem("Women", 234f, Color.parseColor("#EC008C"), -1));
        mViewedWheelIndicatorView.addWheelIndicatorItem(new WheelIndicatorItem("Men", 98f, Color.parseColor("#448CCB"), -1));
    }

    private void setupToolbar() {
        mToolbar.setTitle("Page 2");
        setSupportActionBar(mToolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_up);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void customiseChart() {
        mLineChart.setNoDataTextDescription("You need to provide data for the chart.");
        mLineChart.setDescription("");
        mLineChart.getAxisRight().setEnabled(false);
        mLineChart.getAxisLeft().setAxisMinValue(40);
        mLineChart.getAxisLeft().setStartAtZero(false);
        mLineChart.getAxisLeft().disableGridDashedLine();
        mLineChart.getXAxis().disableGridDashedLine();
        mLineChart.getAxisLeft().setDrawGridLines(false);
        mLineChart.getXAxis().setDrawGridLines(false);
        mLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mLineChart.setDrawGridBackground(false);
        mLineChart.getLegend().setPosition(Legend.LegendPosition.ABOVE_CHART_RIGHT);

    }

    private void setData1() {

        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Entry> yVals = new ArrayList<Entry>();

        xVals.add("10am");
        yVals.add(new Entry(100, 0));
        xVals.add("12am");
        yVals.add(new Entry(75, 1));
        xVals.add("2pm");
        yVals.add(new Entry(50, 2));
        xVals.add("4pm");
        yVals.add(new Entry(75, 3));
        xVals.add("6pm");
        yVals.add(new Entry(50, 4));
        xVals.add("8pm");
        yVals.add(new Entry(75, 5));
        xVals.add("10pm");
        yVals.add(new Entry(100, 6));
        LineDataSet set1 = new LineDataSet(yVals, "Women");
        set1.setFillColor(ContextCompat.getColor(this, R.color.color_women));
        set1.setDrawFilled(true);
        //set1.setColor(getResources().getColor(R.color.color_women));
        //set1.setCircleColor(getResources().getColor(R.color.color_women));
        set1.setColor(ContextCompat.getColor(this, R.color.color_women));
        set1.setCircleColor(ContextCompat.getColor(this, R.color.color_women));
        set1.setLineWidth(0.1f);
        set1.setDrawValues(false);
        //set1.setValueTextSize(9f);
        set1.setDrawCircles(false); //Remo
        //set1.setCircleSize(3f);
        //set1.setFillAlpha(65);
        //set1.setFillColor(Color.BLACK);
        set1.setDrawCircleHole(false);


        xVals = new ArrayList<String>();
        yVals = new ArrayList<Entry>();
        xVals.add("10am");
        yVals.add(new Entry(80, 0));
        xVals.add("12am");
        yVals.add(new Entry(90, 1));
        xVals.add("2pm");
        yVals.add(new Entry(45, 2));
        xVals.add("4pm");
        yVals.add(new Entry(90, 3));
        xVals.add("6pm");
        yVals.add(new Entry(35, 4));
        xVals.add("8pm");
        yVals.add(new Entry(80, 5));
        xVals.add("10pm");
        yVals.add(new Entry(65, 6));
        // create a dataset and give it a type
        LineDataSet set2 = new LineDataSet(yVals, "Men");
        set2.setFillColor(ContextCompat.getColor(this, R.color.color_men));
        set2.setDrawFilled(true);
        set2.setColor(ContextCompat.getColor(this, R.color.color_men));
        set2.setCircleColor(ContextCompat.getColor(this, R.color.color_men));
        set2.setLineWidth(0.1f);
        set2.setDrawValues(false);
        set2.setDrawCircles(false); //Remo
        set2.setDrawCircleHole(false);


        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);

        LineData data = new LineData(xVals, dataSets);

        mLineChart.setData(data);
    }
}
