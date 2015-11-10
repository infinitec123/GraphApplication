package infiniteloop.graph.application;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SecondActivity extends AppCompatActivity {

    @Bind(R.id.chart)
    LineChart mLineChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        customiseChart();
        setData();
    }

    private void customiseChart() {
        mLineChart.setNoDataTextDescription("You need to provide data for the chart.");
        mLineChart.getAxisRight().setEnabled(false);
        mLineChart.getAxisLeft().setAxisMinValue(40);
        mLineChart.getAxisLeft().setStartAtZero(false);
        mLineChart.getAxisLeft().disableGridDashedLine();
        mLineChart.getXAxis().disableGridDashedLine();
        mLineChart.getXAxis().setDrawGridLines(false);
        mLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mLineChart.setDrawGridBackground(false);
        mLineChart.getLegend().setPosition(Legend.LegendPosition.ABOVE_CHART_RIGHT);

    }

    private void setData() {

        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Entry> yVals = new ArrayList<Entry>();

        xVals.add(2006 + "");
        yVals.add(new Entry(100, 0));

        xVals.add(2007 + "");
        yVals.add(new Entry(75, 1));

        xVals.add(2008 + "");
        yVals.add(new Entry(50, 2));

        xVals.add(2009 + "");
        yVals.add(new Entry(75, 3));

        xVals.add(2010 + "");
        yVals.add(new Entry(50, 4));

        xVals.add(2011 + "");
        yVals.add(new Entry(75, 5));

        xVals.add(2012 + "");
        yVals.add(new Entry(100, 6));


        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "Women");
        //set1.setColor(getResources().getColor(R.color.color_women));
        //set1.setCircleColor(getResources().getColor(R.color.color_women));

        set1.setColor(ContextCompat.getColor(this, R.color.color_women));
        set1.setCircleColor(ContextCompat.getColor(this, R.color.color_women));
        set1.setLineWidth(1f);

        set1.setDrawValues(false);
        //set1.setValueTextSize(9f);


        set1.setDrawCircles(false); //Remo
        //set1.setCircleSize(3f);
        //set1.setFillAlpha(65);
        //set1.setFillColor(Color.BLACK);
        set1.setDrawCircleHole(false);

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set1);

        LineData data = new LineData(xVals, dataSets);

        mLineChart.setData(data);
    }


}
