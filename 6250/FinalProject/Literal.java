package project;

public class Literal {

	private Variable v;
	
	/**
	 * constructor
	 */
	public Literal(int n) {
		v = new Variable(n);
	}
	
	public boolean lValue(int i) {
		if (i < v.getVlist().length) return value(i);
		else {
			i = i - v.getVlist().length;
			return !value(i);
		}
	}
	
	private boolean value(int i) {
		if (v.getValue(i) == -1) return true;
		if (v.getValue(i) == 0) return false;
		if (v.getValue(i) == 1) return true;
		return false;
	}

	/**
	 * @return the v
	 */
	public Variable getV() {
		return v;
	}

}
