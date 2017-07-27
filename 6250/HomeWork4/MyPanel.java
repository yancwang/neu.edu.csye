import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Panel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

public class MyPanel extends ApplicationFrame  {
	
	private int[] x;
	private long[] y1;
	private long[] y2;
	
	public MyPanel(String title, int[] x, long[] y, long[] z) {
		super(title);
		JFreeChart xylineChart = ChartFactory.createXYLineChart(title, "Size", "Time", createDataset(x, y, z), PlotOrientation.VERTICAL, true, true, false);
		ChartPanel chartPanel = new ChartPanel(xylineChart);
	    chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
	    final XYPlot plot = xylineChart.getXYPlot();
	    XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
	    renderer.setSeriesPaint(0, Color.RED);
	    renderer.setSeriesPaint(1, Color.BLUE);
	    renderer.setSeriesStroke(0, new BasicStroke(4.0f));
	    renderer.setSeriesStroke(1, new BasicStroke(3.0f));
	    plot.setRenderer(renderer); 
	    setContentPane(chartPanel); 
	}
	
	private XYDataset createDataset(int[] x, long[] y, long[] z) {
		final XYSeries l1 = new XYSeries("Common");
		for (int i = 0; i < x.length; i++) {
			l1.add(x[i], y[i]);
		}
		final XYSeries l2 = new XYSeries("Strassen");
		for (int i = 0; i < x.length; i++) {
			l2.add(x[i], z[i]);
		}
		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(l1);
		dataset.addSeries(l2);
		return dataset;
	}
	
}
