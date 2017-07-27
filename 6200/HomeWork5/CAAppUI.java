package edu.neu.csye6200.ca;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

public class CAAppUI extends JFrame implements ActionListener {

	private CAAppCanvas caAppCanvas = null;
	private JButton startButton, stopButton, resumeButton;
	protected JPanel mainPanel = null;
	private JPanel nPanel = null;
	private JFrame nFrame = null;
	private MyThread thread = null;
	private JTextField rowNum = null;   
	private JComboBox ruleList = null;
	private int row;
	private int rule;
	private UITask uiTask;
	private CAGenerationSet caSet;
	private Thread t;
	
	public CAAppUI() {
		super("CA Application");
		setSize(500, 500);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		getContentPane().setLayout(new BorderLayout());
		add(getMainPanel(),BorderLayout.NORTH);
		
        
        setVisible(true);
	}

	private Component getMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new FlowLayout());
		
		//make button
		startButton = makeButton("Start");
//		
//		startButton.addActionListener(new ActionListener(){
//
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				// TODO Auto-generated method	
//	    		
//			}
//			
//		});
		stopButton = makeButton("Stop");
		resumeButton = makeButton("Resume");
        stopButton.setEnabled(false);
		
        //make text and combo
        rowNum = makeText();
        ruleList = makeComboBox();
        
		mainPanel.add(startButton);
		mainPanel.add(stopButton);
		mainPanel.add(resumeButton);
		mainPanel.add(rowNum);
		mainPanel.add(ruleList);
		
		return mainPanel;
	}
	
	class MyThread implements Runnable {

		private boolean suspended = false;
		
		@Override
		public void run() {
            if(ruleList.getSelectedItem()!=null) {
    			switch (ruleList.getSelectedIndex()) {
    			case 0:
    				setRule(0);
    				break;
    			case 1:
    				setRule(1);
    				break;
    			case 2:
    				setRule(2);
    				break;
    			case 3:
    				setRule(3);
    				break;
    			case 4:
    				setRule(4);
    				break;
    			case 5:
    				setRule(5);
    				break;
    			}
    			}
    		if(rowNum.getText() != null) {
    			setRow(Integer.parseInt(rowNum.getText()));
    		}
    		
    		caSet = new CAGenerationSet();
    		caSet.produce(getRow(), getRule());
    		
			nFrame = new JFrame();
			nFrame.setSize(1000, 1000);
			nFrame.getContentPane().setLayout(new BorderLayout());
			
			nPanel = new JPanel();
			nPanel.setLayout(new FlowLayout());
			caAppCanvas = new CAAppCanvas();
			nFrame.add(caAppCanvas);
			
//			caAppCanvas.produce(getRow(), getRule());
			caAppCanvas.produce(caSet);
			nFrame.setVisible(true);

		}
		
		void suspend() {
			suspended = true;
			caSet.stopdraw(suspended);
		}
	
		synchronized void resume() {
			suspended = false;
			notify();
		}
	}

	private class UITask extends SwingWorker<Void, Void> {

		@Override
		protected Void doInBackground() throws Exception {
			setThread(new MyThread());
			getThread().run();
			
			return null;
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand()=="Start"){
//    		(setUiTask(new UITask())).execute();
			MyThread mt = new MyThread();
    		setT(new Thread(mt));
    		getT().start();
    		startButton.setEnabled(false);
            stopButton.setEnabled(true);
            resumeButton.setEnabled(false);
		}
		
		if (e.getActionCommand()=="Stop"){
			startButton.setEnabled(true);
            stopButton.setEnabled(false);
            resumeButton.setEnabled(true);
            suspendDraw();
            //getThread().suspend();
            //getThread().suspend();
            //setUiTask(null);
		}
		
		if (e.getActionCommand()=="Resume") {
			startButton.setEnabled(false);
			stopButton.setEnabled(true);
			resumeButton.setEnabled(false);
			getThread().resume();
		}
	}
	
	private void suspendDraw(){
		this.caSet.stopdraw();
	}
	
	private JButton makeButton(String caption) {
        JButton b = new JButton(caption);
        b.setActionCommand(caption);
        b.addActionListener(this);
        return b;
    }
	
	private JComboBox makeComboBox(){
		String[] ruleListStrings = { "Rule 1", "Rule 2", "Rule 3", "Rule 4", "Rule 5", "Rule 6"};
		JComboBox rule = new JComboBox(ruleListStrings);
		rule.setSelectedIndex(0);
		rule.addActionListener(this);
		return rule;
	}
	
	private JTextField makeText(){
		JTextField row = new JTextField(8);
		row.setActionCommand("rowNum");
		return row;
	}

	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}


	/**
	 * @param row the row to set
	 */
	public void setRow(int row) {
		this.row = row;
	}


	/**
	 * @return the rule
	 */
	public int getRule() {
		return rule;
	}


	/**
	 * @param rule the rule to set
	 */
	public void setRule(int rule) {
		this.rule = rule;
	}


	/**
	 * @return the caAppCanvas
	 */
	public CAAppCanvas getCaAppCanvas() {
		return caAppCanvas;
	}


	/**
	 * @param caAppCanvas the caAppCanvas to set
	 */
	public void setCaAppCanvas(CAAppCanvas caAppCanvas) {
		this.caAppCanvas = caAppCanvas;
	}

	/**
	 * @return the thread
	 */
	public MyThread getThread() {
		return thread;
	}

	/**
	 * @param thread the thread to set
	 */
	public void setThread(MyThread thread) {
		this.thread = thread;
	}

	/**
	 * @return the uiTask
	 */
	public UITask getUiTask() {
		return uiTask;
	}

	/**
	 * @param uiTask the uiTask to set
	 */
	public UITask setUiTask(UITask uiTask) {
		this.uiTask = uiTask;
		return uiTask;
	}

	/**
	 * @return the nPanel
	 */
	public JPanel getnPanel() {
		return nPanel;
	}

	/**
	 * @param nPanel the nPanel to set
	 */
	public void setnPanel(JPanel nPanel) {
		this.nPanel = nPanel;
	}

	/**
	 * @return the t
	 */
	public Thread getT() {
		return t;
	}

	/**
	 * @param t the t to set
	 */
	public void setT(Thread t) {
		this.t = t;
	}

}
