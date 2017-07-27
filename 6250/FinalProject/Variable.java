package project;

public class Variable {

	private int[] vlist;

	/**
	 * constructor
	 */
	public Variable(int n) {
		vlist = new int[n];
		for(int i = 0; i < n; i++) {
			vlist[i] = -1;
		}
	}
	
	public void iniVlist(int i) {
		vlist[i] = 0;
	}
	
	public void changeVlist(int i) {
		if (vlist[i] == 0) 
			vlist[i] = 1;
		else 
			vlist[i] = 0;
	}
	
	public void setTrue(int i) {
		vlist[i] = 1;
	}
	
	public void setFalse(int i) {
		vlist[i] = 0;
	}
	
	public int getValue(int i) {
		return vlist[i];
	}

	/**
	 * @return the vlist
	 */
	public int[] getVlist() {
		return vlist;
	}
	
}
