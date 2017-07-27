package edu.neu.csye6200.ca;

public class CAGeneration {
	
	private int[] cellArray = new int[64];
	
	public CAGeneration(int row){
		
		if(row==0){
			for(int i=0; i<64; i++){
				cellArray[i] = 2;
			}
			cellArray[32] = 0;
		}
	}

	/**
	 * @return the cellArray
	 */
	public int[] getCellArray() {
		return cellArray;
	}

	/**
	 * @param cellArray the cellArray to set
	 */
	public void setCellArray(int[] cellArray) {
		this.cellArray = cellArray;
	}
	
}
