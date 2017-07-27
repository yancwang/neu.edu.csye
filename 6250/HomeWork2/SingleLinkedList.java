package neu.edu.algr;

import java.util.Iterator;
import java.util.Random;

public class SingleLinkedList {

	private Node header;
	private int counter1;
	private int counter2;
	private int counter3;
	
	/**
	 * Constructor
	 */
	public SingleLinkedList(int n) {
		header = new Node();
		Random r = new Random();
		for (int i=0; i<n; i++) {
			insert(header, r.nextInt(50000));
		}
	}
	
	public void insert(Node header, int n) {
		Node insert = new Node();
		insert.setItem(n);
		
		Node p, trailer;
		
		if (isEmpty()) {
			header.setNext(insert);
			insert.setNext(null);
		}
		else {
			p = header.getNext();
			trailer = header;
			
			while(p != null ) {
				trailer = p;
				p = p.getNext();
			}
			
			trailer.setNext(insert);
			insert.setNext(null);
		}
	}
	
	public boolean isEmpty() {
		return (header.getNext() == null);
	}
	
	public int size() {
		int counter = 0;
		if (isEmpty()) {
			return counter;
		}
		else {
			Node trailer = header.getNext();
			counter++;
			while(trailer != null) {
				trailer = trailer.getNext();
				counter++;
			}
		}
		return counter;
	}
	
	public Iterator<Integer> iterator() {
		return new ListIterator();
	}
	
	private class ListIterator implements Iterator<Integer> {

		private Node current = header.getNext();
		
		@Override
		public boolean hasNext() {
			if (current != null) {
				return true;
			}
			return false;
		}

		@Override
		public Integer next() {
			int i = current.getItem();
			current = current.getNext();
			return i;
		}
		
	}
	
	public void sortList() {
		Node past = header;
		Node current = header.getNext();
		Node next = current.getNext();
		Node last = findLast();
		
		setCounter1(0);
		setCounter2(0);
		setCounter3(0);
		
		while(!current.equals(last)) {
			counter3++;
			int i = counter2;
			while(!current.equals(last)) {
				counter1++;
				if(current.getItem() > next.getItem()) {
					if(next.equals(last)) {
						last = current;
					}
					swap(past, current, next);
					counter2++;
					past = past.getNext();
					next = current.getNext();
				}
				else {
					past = past.getNext();
					current = current.getNext();
					next = next.getNext();
				}
			}
			if (isSorted(i, counter2)) {
				counter3--;
				break;
			}
			last = past;
			past = header;
			current = header.getNext();
			next = current.getNext();
		}
	}
	
	private void swap(Node past, Node current, Node next) {
		Node temp = next.getNext();
		past.setNext(next);
		next.setNext(current);
		current.setNext(temp);
	}
	
	private Node findLast() {
		Node p, trailer;
		
		p = header.getNext();
		trailer = header;
		
		while(p != null ) {
			trailer = p;
			p = p.getNext();
		}
		return trailer;
	}
	
	private boolean isSorted(int i, int m) {
		return (i == m);
	}

	/**
	 * @return the counter1
	 */
	public int getCounter1() {
		return counter1;
	}

	/**
	 * @param counter1 the counter1 to set
	 */
	public void setCounter1(int counter1) {
		this.counter1 = counter1;
	}

	/**
	 * @return the counter2
	 */
	public int getCounter2() {
		return counter2;
	}

	/**
	 * @param counter2 the counter2 to set
	 */
	public void setCounter2(int counter2) {
		this.counter2 = counter2;
	}

	/**
	 * @return the counter3
	 */
	public int getCounter3() {
		return counter3;
	}

	/**
	 * @param counter3 the counter3 to set
	 */
	public void setCounter3(int counter3) {
		this.counter3 = counter3;
	}
	
}
