package edu.neu.csye6200.ca;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.util.ArrayList;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CAAppCanvas extends Canvas {

	private ArrayList<int[]> arrayList = new ArrayList<int[]>();
	CAGenerationSet caSet = null;
	private boolean suspend = false;
	
//	public CAAppCanvas(int row, int rule) {
//		// TODO Auto-generated constructor stub
//		caSet = new CAGenerationSet(row, rule);
//	}

	public CAAppCanvas() {
	}
	
	public void produce(CAGenerationSet caSet) {
		// TODO Auto-generated constructor stub
		// caSet = new CAGenerationSet(row, rule);
		this.caSet = caSet;
	}

	public CAAppCanvas(GraphicsConfiguration arg0) {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void paint(Graphics g) {
		drawCAApp(g);
	}

	private void drawCAApp(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
	
		int w = 10;
		int h = 20;
	
		for(int[] listTest : caSet.getArrayList()){
//			if (suspend == true)break;
			for(int i : listTest){
				
				CACell cCell = new CACell(i);
				g.setColor(cCell.getColor(i));
				g.fillRect(w=w+10, h, 10, 10);
			}
			w = 10;
			h = h + 10;
		}
		
//		try {
//		Thread.sleep(50);
//	} catch (InterruptedException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
	}
	
	public void stop(){
		suspend = true;
	}
	
	

}
