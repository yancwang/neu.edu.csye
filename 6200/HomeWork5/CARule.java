package edu.neu.csye6200.ca;

public class CARule {

	private int[] newArray;
	
	public CARule(int[] array, int ruleNum){
		
		int left;
		int middle;
		int right;
		int newStatus;
		
		setNewArray(new int[array.length]);
		
		for(int i=1;i<array.length-1;i++){
			left = array[i-1];
			middle = array[i];
			right = array[i+1];
			
			switch(ruleNum) {
			case 0:
				newStatus = ruleOne(left, middle, right);
				newArray[i] = newStatus;
				break;
			case 1:
				newStatus = ruleTwo(left, middle, right);
				newArray[i] = newStatus;
				break;
			case 2:
				newStatus = ruleThree(left, middle, right);
				newArray[i] = newStatus;
				break;
			case 3:
				newStatus = ruleFour(left, middle, right);
				newArray[i] = newStatus;
				break;
			case 4:
				newStatus = ruleFive(left, middle, right);
				newArray[i] = newStatus;
				break;
			case 5:
				newStatus = ruleSix(left, middle, right);
				newArray[i] = newStatus;
				break;
			}
			
		}
		newArray[0] = 2;
		newArray[array.length-1] = 2;
	}
	
	private int ruleOne(int left, int middle, int right){
		int sum = left + middle + right;
		switch(sum){
		case 0:
			return 0;
		case 1:
			return 1;
		case 2:
			return 1;
		case 3:
			return 2;
		case 4:
			return 0;
		case 5:
			return 1;
		case 6:
			return 2;
		}
		return 0;
	}

	private int ruleTwo(int left, int middle, int right) {
		int sum = left + middle + right;
		switch(sum){
		case 0:
			return 0;
		case 1:
			return 2;
		case 2:
			return 1;
		case 3:
			return 0;
		case 4:
			return 2;
		case 5:
			return 0;
		case 6:
			return 0;
		}
		return 0;
	}
	
	private int ruleThree(int left, int middle, int right) {
		int sum = left + middle + right;
		switch(sum){
		case 0:
			return 0;
		case 1:
			return 0;
		case 2:
			return 1;
		case 3:
			return 1;
		case 4:
			return 2;
		case 5:
			return 2;
		case 6:
			return 0;
		}
		return 0;
	}
	
	private int ruleFour(int left, int middle, int right) {
		int sum = left + middle + right;
		switch(sum){
		case 0:
			return 0;
		case 1:
			return 2;
		case 2:
			return 1;
		case 3:
			return 2;
		case 4:
			return 0;
		case 5:
			return 0;
		case 6:
			return 1;
		}
		return 0;
	}
	
	private int ruleFive(int left, int middle, int right) {
		int sum = left + middle + right;
		switch(sum){
		case 0:
			return 0;
		case 1:
			return 2;
		case 2:
			return 2;
		case 3:
			return 0;
		case 4:
			return 1;
		case 5:
			return 1;
		case 6:
			return 0;
		}
		return 0;
	}
	
	private int ruleSix(int left, int middle, int right) {
		int sum = left + middle + right;
		switch(sum){
		case 0:
			return 1;
		case 1:
			return 2;
		case 2:
			return 2;
		case 3:
			return 0;
		case 4:
			return 1;
		case 5:
			return 0;
		case 6:
			return 0;
		}
		return 0;
	}
	
	/**
	 * @return the newArray
	 */
	public int[] getNewArray() {
		return newArray;
	}

	/**
	 * @param newArray the newArray to set
	 */
	public void setNewArray(int[] newArray) {
		this.newArray = newArray;
	}
	
}
