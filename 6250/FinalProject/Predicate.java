package project;

public class Predicate {
	
	private Clause[] clist;
	private Literal l;
	private int track = 0;
	
	/**
	 * constructor
	 */
	public Predicate(int t, int n, int[] array) {
		l = new Literal(n);
		clist = new Clause[t];
		int temp = 0;
		for(int i = 0; i < t; i++) {
			int[] a = new int[3];
			a[0] = array[temp++];
			a[1] = array[temp++];
			a[2] = array[temp++];
			clist[i] = new Clause(a);
		}
	}
	
//	public void allSolution() {
//		track = 0;
//		for (int i = 0; i < l.getV().getVlist().length; i++) {
//			l.getV().iniVlist(i);
//		}
//		int i = 0;
//		boolean j = checkStatus();
//		l.getV().setTrue(i);
//		if (j == checkStatus()) {
//			l.getV().setFalse(i);
//			findAllSolution(i + 1);
//			l.getV().setTrue(i);
//			trackNode();
//			findAllSolution(i + 1);
//		}
//		else {
//			trackNode();
//			findAllSolution(i + 1);
//		}
//	}
//	
//	private void findAllSolution(int i) {
//		int length = l.getV().getVlist().length - 1;
//		if(i < length) {
//			boolean j = checkStatus();
//			l.getV().setTrue(i);
//			if (j == checkStatus()) {
//				l.getV().setFalse(i);
//				findAllSolution(i + 1);
//				l.getV().setTrue(i);
//				trackNode();
//				findAllSolution(i + 1);
//			}
//			else {
//				trackNode();
//				findAllSolution(i + 1);
//			}
//		}
//		else {
//			if (checkStatus()) printList(l.getV().getVlist());
//			else {
//				l.getV().setTrue(i);
//				trackNode();
//				if (checkStatus()) printList(l.getV().getVlist());
//			}
//		}
//	}
	
	public void allSolution() {
		track = 0;
		for (int i = 0; i < l.getV().getVlist().length; i++) {
			l.getV().iniVlist(i);
		}
		int length = l.getV().getVlist().length - 1;
		for (int i = length; i >= 0; i--) {
			if(i < length) {
				l.getV().setTrue(i);
				track = track + length + 1 - i;
				findAllSolution(i + 1);
			}
			else {
				if (checkStatus()) printList(l.getV().getVlist());
				else {
					l.getV().setTrue(i);
					trackNode();
					if (checkStatus()) printList(l.getV().getVlist());
				}
			}
		}
	}
	
	private void findAllSolution(int i) {
		int length = l.getV().getVlist().length - 1;
		if(i < length) {
			l.getV().setFalse(i);
			findAllSolution(i + 1);
			l.getV().setTrue(i);
			track = track + length + 1 - i;
			findAllSolution(i + 1);
		}
		else {
			l.getV().setFalse(i);
			if (checkStatus()) printList(l.getV().getVlist());
			else {
				l.getV().setTrue(i);
				trackNode();
				if (checkStatus()) printList(l.getV().getVlist());
			}
		}
	}
	
	private void printList(int[] a) {
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i]);
		}
		System.out.print(" " + track);
		System.out.println();
	}
	
//	public int[] oneSolution() {
//		int length = l.getV().getVlist().length - 1;
//		for (int i = length; i >= 0; i--) {
//			if(i < length) {
//				l.getV().setTrue(i);
//				track = track + length + 1 - i;
//				if (findOneSolution(i + 1)) return l.getV().getVlist();
//			}
//			else {
//				if (checkStatus()) return l.getV().getVlist();
//				else {
//					l.getV().setTrue(i);
//					trackNode();
//					if (checkStatus()) return l.getV().getVlist();
//				}
//			}
//		}
//		return null;
//	}
//	
//	private boolean findOneSolution(int i) {
//		int length = l.getV().getVlist().length - 1;
//		if(i < length) {
//			l.getV().setFalse(i);
//			if (findOneSolution(i + 1)) return true;
//			l.getV().setTrue(i);
//			track = track + length + 1 - i;
//			if (findOneSolution(i + 1)) return true;
//		}
//		else {
//			l.getV().setFalse(i);
//			if (checkStatus()) return true;
//			else {
//				l.getV().setTrue(i);
//				trackNode();
//				if (checkStatus()) return true;
//			}
//		}
//		return false;
//	}
	
	public int[] iniValue() {
		track = 0;
		for (int i = 0; i < l.getV().getVlist().length; i++) {
			l.getV().iniVlist(i);
			if (!checkStatus()) {
				l.getV().setTrue(i);
				trackNode();
			}
		}
		return l.getV().getVlist();
	}
	
	private boolean checkStatus() {
		for (int i = 0; i < clist.length; i++) {
			int[] c = clist[i].getLlist();
			boolean c1 = !l.lValue(c[0]);
			boolean c2 = !l.lValue(c[1]);
			boolean c3 = !l.lValue(c[2]);
			if (c1 && c2 && c3) return false;
		}
		return true;
	}
	
	private void trackNode() {
		track++;
	}

	/**
	 * @return the track
	 */
	public int getTrack() {
		return track;
	}
	
}
