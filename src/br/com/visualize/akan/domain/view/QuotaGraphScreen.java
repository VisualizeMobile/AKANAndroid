package br.com.visualize.akan.domain.view;

import java.util.ArrayList;
import java.util.List;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import br.com.visualize.akan.R;
import br.com.visualize.akan.domain.controller.CongressmanController;
import br.com.visualize.akan.domain.controller.QuotaController;
import br.com.visualize.akan.domain.controller.StatisticController;
import br.com.visualize.akan.domain.model.Quota;
import br.com.visualize.akan.domain.model.Statistic;

public class QuotaGraphScreen extends Activity implements OnChartValueSelectedListener {

	private LineChart mChart;
	private Context context;
	
	
	private QuotaController quotaController;
	private CongressmanController congressmanController;
	private StatisticController statisticController;
	
	int subquota;
	int year;
	List<Quota> quotas;
    List<Statistic> statistics;
    LineData data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quota_graph_screen);
        
        context = getApplicationContext();
        congressmanController = CongressmanController.getInstance(context);
        quotaController = QuotaController.getInstance(context);
        statisticController = StatisticController.getInstance(context);
        
        subquota = quotaController.getQuota().getTypeQuota().getValueSubQuota();
        year = quotaController.getQuota().getYearReferenceQuota();
        quotas = quotaController.getQuotasByTypeAndYear(subquota, year);
        statistics = statisticController.getStatisticByTypeAndYear( subquota, year);
        
        TextView congressmanLegend = (TextView) findViewById(R.id.congressman_expenditures_textview);
        congressmanLegend.setText("Gastos de "+congressmanController.getCongresman().getNameCongressman());
        TextView topbarText = (TextView) findViewById(R.id.topbar_quota);
        topbarText.setText(quotaController.getQuota().getDescriptionQuota());
        
        mChart = (LineChart) findViewById(R.id.chart1);
        mChart.setOnChartValueSelectedListener(this);
        
        // no description text
        mChart.setDescription("");
        mChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable value highlighting
        mChart.setHighlightEnabled(true);

        // enable touch gestures
        mChart.setTouchEnabled(true);
        
        mChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);
        mChart.setHighlightPerDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        mChart.setBackgroundColor(Color.WHITE);

        // add data
        setData(20, 30);

        mChart.animateX(2500);

        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        // get the legend (only possible after setting data)
        mChart.getLegend().setEnabled(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setTypeface(tf);
        xAxis.setTextSize(12f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setSpaceBetweenLabels(1);

        float max = Math.max((float)quotaController.getMaxQuotaValue(quotas),
        		(float)statisticController.getMaxStatisticValue(statistics)) * 1.3f;
        
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(tf);
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setAxisMaxValue(max);
        leftAxis.setDrawGridLines(true);
        leftAxis.setAxisMinValue(0);
        
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setTypeface(tf);
        rightAxis.setAxisMaxValue(0);
        rightAxis.setStartAtZero(false);
        rightAxis.setAxisMinValue(0);
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawGridLines(false);
		
	}

	@Override
	public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected() {
		// TODO Auto-generated method stub
		
	}
	
	private void setData(int count, float range) {
		
        ArrayList<String> xVals = new ArrayList<String>();
        int numMonths = Math.max(quotas.size(), statistics.size());
        for (int i = 1; i <= numMonths; i++) {
            xVals.add((i) + "");
        }
        
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        for (int i = 0; i <  numMonths; i++) {
        	if(i<quotas.size()){
        		yVals1.add(new Entry((float) quotas.get(i).getValueQuota(), i));
        	} else {
        		yVals1.add(new Entry(0f, i));
        	}
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals1, "Gastos de " + congressmanController.getCongresman().getNameCongressman());
        set1.setAxisDependency(AxisDependency.LEFT);
        set1.setColor(Color.RED);
        set1.setCircleColor(0x90536571);
        set1.setLineWidth(3f);
        set1.setCircleSize(6f);
        set1.setFillAlpha(35);
        set1.setFillColor(Color.RED);
        set1.setHighLightColor(Color.argb(30,244, 117, 117));
        set1.setDrawCircleHole(false);

        ArrayList<Entry> yVals2 = new ArrayList<Entry>();

        //TODO: Fix statistics list from dao.
        for (int i = 0; i < numMonths; i++) {
        	if(i<statistics.size()){
        		yVals2.add(new Entry((float) statistics.get(i).getAverage(), i));
        	} else {
        		yVals2.add(new Entry(0f, i));
        	}
        }

         //create a dataset and give it a type
        LineDataSet set2 = new LineDataSet(yVals2, "Gasto Médio dos Deputados");
        set2.setAxisDependency(AxisDependency.LEFT);
        set2.setColor(0xffF3D171);
        set2.setCircleColor(0xffF3D171);
        set2.setLineWidth(3f);
        set2.setCircleSize(0f);
        set1.setFillAlpha(35);
        set2.setDrawCircleHole(false);
        set2.setHighLightColor(Color.argb(30,244, 117, 117));

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set2);
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        data = new LineData(xVals, dataSets);
        //data.setValueTextColor(Color.BLACK);
        data.setValueTextSize(0f);

        // set data
        mChart.setData(data);
    }
	/**
	 * Back to quotas description
	 * 
	 * @param view
	 *            current View
	 */
	public void backToDescription( View view ) {
		this.finish();
	}
}
