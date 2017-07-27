package project;

public class Clause {

	private int[] llist;

	/**
	 * constructor
	 */
	public Clause(int a[]) {
		llist = new int[3];
		for (int i = 0; i < 3; i++) {
			llist[i] = a[i];
		}
	}

	/**
	 * @return the clist
	 */
	public int[] getLlist() {
		return llist;
	}
	
}
