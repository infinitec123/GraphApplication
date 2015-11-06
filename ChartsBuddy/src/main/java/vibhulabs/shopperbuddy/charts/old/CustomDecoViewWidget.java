package vibhulabs.shopperbuddy.charts.old;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;
import vibhulabs.shopperbuddy.charts.R;


public class CustomDecoViewWidget extends FrameLayout {

    DecoView mDecoView;
    private TextView mPercentTextView;
    private TextView mCategoryTextView;

    final float mSeriesMax = 100f;


    public CustomDecoViewWidget(final Context context, AttributeSet attrs) {
        super(context, attrs);

        /* Inflate the XML */
        String inflatorservice = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li;
        li = (LayoutInflater) getContext().getSystemService(inflatorservice);
        li.inflate(R.layout.widget_custom_decoview, this, true);

        mDecoView = (DecoView) findViewById(R.id.id_decoview);
        mPercentTextView = (TextView) findViewById(R.id.id_text_percentage);
        mCategoryTextView = (TextView) findViewById(R.id.id_text_statement);
    }

    //*********************************************************************
    // APIs
    //*********************************************************************

    /**
     * TODO: Complete
     */

    public void setData() {
        mDecoView.deleteAll();
        mDecoView.configureAngles(360, 0);

        mDecoView.addSeries(new SeriesItem.Builder(Color.argb(255, 218, 218, 218))
                .setRange(0, mSeriesMax, mSeriesMax)
                .setInitialVisibility(true)
                .setLineWidth(16f)
                .build());

        SeriesItem seriesItem1 = new SeriesItem.Builder(Color.argb(255, 247, 112, 96))
                .setRange(0, mSeriesMax, 46)
                .setInterpolator(new BounceInterpolator())
                .setLineWidth(16f)
                .build();

        int mSeries1Index = mDecoView.addSeries(seriesItem1);
        mDecoView.addEvent(new DecoEvent.Builder(15).setIndex(mSeries1Index).setDelay(1000).build());
        mDecoView.addEvent(new DecoEvent.Builder(54).setIndex(mSeries1Index).setDelay(5000).build());
        mDecoView.addEvent(new DecoEvent.Builder(46).setIndex(mSeries1Index).setDelay(13000).build());

        mPercentTextView.setText("46%");
        mCategoryTextView.setText("Individuals");
    }
}

//*********************************************************************
// End of class
//*********************************************************************