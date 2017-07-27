package edu.neu.csye6200.ca;

import java.awt.Color;

public class CACell {

	private CellStatus cstatus;
	
	enum CellStatus{
		ON(2), GREY(1),OFF(0);
		
		private int value;
		
		CellStatus(int n){
			value = n;
		}
		
		public int getValue(){
			return value;
		}
	}
	
	public CACell(int i){
		
		switch(i){
			case 2: setCstatus(CellStatus.ON);
					break;
			case 1: setCstatus(CellStatus.GREY);
					break;
			case 0: setCstatus(CellStatus.OFF);
					break;
			default: 
					break;
		}
		
	}
	
	public Color getColor(int i){
		
		switch(i){
		case 2: return Color.black;
		case 1: return Color.gray;
		case 0: return Color.white;
		default: 
				return Color.blue;
		}
		
	}
		

	/**
	 * @return the cstatus
	 */
	public CellStatus getCstatus() {
		return cstatus;
	}

	/**
	 * @param cstatus the cstatus to set
	 */
	public void setCstatus(CellStatus cstatus) {
		this.cstatus = cstatus;
	}
	
}
