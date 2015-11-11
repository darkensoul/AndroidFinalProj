package com.example.brittany.hcd;

import android.content.Context;
import android.graphics.Color;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

/**
 * Created by Nehal on 11/7/15.
 */
public class LineGraph {
    private GraphicalView view;
    private TimeSeries dataset = new TimeSeries("Current Heart Rate");
    private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();

    private XYSeriesRenderer renderer = new XYSeriesRenderer();
    private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();

    public LineGraph()
    {
        mDataset.addSeries(dataset);
        renderer.setColor(Color.RED);
       // renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setFillPoints(true);
        mRenderer.setXAxisMin(0);
        mRenderer.setShowGrid(true);
        //mRenderer.setZoomButtonsVisible(true);
        mRenderer.setXTitle("Time");
        mRenderer.setYTitle("Heart rate");
        mRenderer.addSeriesRenderer(renderer);
    }

    public GraphicalView getView(Context context)
    {
        view = ChartFactory.getLineChartView(context,mDataset,mRenderer);
        return view;
    }

    public void addNewPoints(Point p)
    {
        dataset.add(p.getX(),p.getY());
    }
}
