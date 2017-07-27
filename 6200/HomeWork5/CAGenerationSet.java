package edu.neu.csye6200.ca;

import java.util.ArrayList;

import javax.swing.JPanel;

public class CAGenerationSet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int[] formerArray;
	private ArrayList<int[]> arrayList = new ArrayList<int[]>();  
	private boolean suspend = false;
	
	public void produce(int rowNum, int ruleNum){
		
		for(int row = 0; row<rowNum; row++){
			if (isSuspend() == true) break;
//			System.out.println(isSuspend());
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			CAGeneration ca = new CAGeneration(row);
			
			if(row>0){
				CARule cRule = new CARule(getFormerArray(), ruleNum);
				ca.setCellArray(cRule.getNewArray());
			}
			arrayList.add(ca.getCellArray());			
			setFormerArray(ca.getCellArray());
		}
	
		for(int[] i : arrayList){
			for(int j : i){
				System.out.print(j);
			}
			System.out.println("");
		}
	}
	
	/**
	 * @return the formerArray
	 */
	public int[] getFormerArray() {
		return formerArray;
	}

	/**
	 * @param formerArray the formerArray to set
	 */
	public void setFormerArray(int[] formerArray) {
		this.formerArray = formerArray;
	}

	/**
	 * @return the arrayList
	 */
	public ArrayList<int[]> getArrayList() {
		return arrayList;
	}

	/**
	 * @param arrayList the arrayList to set
	 */
	public void setArrayList(ArrayList<int[]> arrayList) {
		this.arrayList = arrayList;
	}
	
	public void stopdraw(boolean it){
//		System.out.println("stop it");
		this.setSuspend(it);
	}

	/**
	 * @return the suspend
	 */
	public boolean isSuspend() {
		return suspend;
	}

	/**
	 * @param suspend the suspend to set
	 */
	public void setSuspend(boolean suspend) {
		this.suspend = suspend;
	}
	
	public void stopdraw(){
		suspend = true;
	}
	
}