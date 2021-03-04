package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

/**
 * Razred koji crta stupičasti dijagram.
 * @author dario
 *
 */
public class BarChartComponent extends JComponent{

	private static final long serialVersionUID = 1L;
	
	private int gap = 10;
	private Dimension spaceForXTextAndValues;
	private Dimension spaceForYTextAndValues;
	private Dimension barChart;
	
	private BarChart chart;
	
	/**
	 * Konstruktor.
	 * @param chart stupičasti dijagrama koji se treba nacrtati
	 */
	public BarChartComponent(BarChart chart) {
		if(chart == null) {
			throw new NullPointerException();
		}
		this.chart = chart;

	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		int width = getSize().width;
		int height = getSize().height;
		spaceForXTextAndValues = new Dimension((int) (width-width*0.15), (int) (height-height*0.85));
		spaceForYTextAndValues = new Dimension((int) (width-spaceForXTextAndValues.width), (int) (height-spaceForXTextAndValues.height));
		barChart = new Dimension(spaceForXTextAndValues.width-gap*5, spaceForYTextAndValues.height);
		
		g.setFont(g.getFont().deriveFont(12));
		FontMetrics fm = g.getFontMetrics();
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		AffineTransform defaultTransform = g2d.getTransform();
		g2d.setTransform(at);
		int yTextWidth = fm.stringWidth(chart.getyAxisText());
		g2d.drawString(chart.getyAxisText(), -(height+yTextWidth)/2 , gap);
		g2d.setTransform(defaultTransform);
		int xTextWidth = fm.stringWidth(chart.getxAxisText());
		g.drawString(chart.getxAxisText(), (width-xTextWidth)/2, height-gap);
		
		
		g.setFont(g.getFont().deriveFont(Font.BOLD, 10f));
		int rowDistance = barChart.height / (((chart.getyMax()-chart.getyMin()) / chart.getSpace())+1);
		int currentY = barChart.height;
		for(int i = chart.getyMin(); i <= chart.getyMax(); i+=chart.getSpace()) {
			g.drawString(Integer.toString(i), gap*4-fm.stringWidth(Integer.toString(i)), currentY);
			currentY = currentY - rowDistance;
		}
		
		g.drawLine(gap*5-1, barChart.height+fm.getDescent(), gap*5-1, currentY+rowDistance-fm.getAscent());
		g.drawLine(gap*5-5, barChart.height, width-gap*5, barChart.height);
		
		drawArrowHead(g2d, gap*5-1, barChart.height-fm.getDescent(), gap*5-1, currentY+rowDistance-fm.getAscent());
		drawArrowHead(g2d, gap*5+gap/2, barChart.height+gap/2, width-gap*5+gap/2, barChart.height+gap/2);

		
		
		g.setColor(Color.RED);
		int yAxisLength = (barChart.height-(currentY+rowDistance+fm.getDescent()));
		rowDistance = yAxisLength / (chart.getyMax()-chart.getyMin());
		int numberOfColumns = chart.getValues().size();
		int columnWidth = (width-gap*5-gap*5) / numberOfColumns;
		int rectX = gap*5;
		int rectY;
		int rectHeight = 0;
		for(XYValue value : chart.getValues()) {
			rectY = yAxisLength-((value.getY()-chart.getSpace())*rowDistance);
			rectHeight = barChart.height-rectY;
			g.fillRect(rectX, rectY, columnWidth, rectHeight);
			rectX += columnWidth;
		}
		
		g.setColor(Color.BLACK);
		currentY = barChart.height + gap*2;
		int currentX = (gap*5-1) + columnWidth/2;
		for(int i = 1; i <= numberOfColumns; i++) {
			g.drawString(Integer.toString(i), currentX, currentY);
			currentX += columnWidth;
		}			
	}
	
	
	/**
	 * Pomoćna funkcija za crtanje strelice na vrhu koordinatnih osi.
	 * @param g2d crtač
	 * @param x1 x lokacija prve točke
	 * @param y1 y lokacija prve točke
	 * @param x2 x lokacija druge točke
	 * @param y2 y lokacija druge točke
	 */
	private void drawArrowHead(Graphics2D g2d, int x1, int y1, int x2, int y2) {
		
		Polygon arrowHead = new Polygon();
		arrowHead.addPoint(0, 5);
		arrowHead.addPoint(-5, -5);
		arrowHead.addPoint(5, -5);
		
		AffineTransform tx = new AffineTransform();
		tx.setToIdentity();
		
		double angle = Math.atan2(y2-y1, x2-x1);
		tx.translate(x2, y2+gap);
		tx.rotate((angle-Math.PI/2d));
		
		g2d = (Graphics2D) g2d.create();
		g2d.setTransform(tx);
		g2d.fill(arrowHead);
		
	}


}
