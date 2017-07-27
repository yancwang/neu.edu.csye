package neu.edu.algr;

import java.util.Iterator;
import java.util.Random;

public class DoubleLinkedList {

	private Node rear;
	private Node front;
	private int counter1;
	private int counter2;
	private int counter3;

	/**
	 * constructor
	 */
	public DoubleLinkedList(int n) {
		front = new Node();
		rear = new Node();
		Random r = new Random();
		for (int i=0; i<n; i++) {
			insert(front, rear, r.nextInt(50000));
		}
	}
	
	public void insert(Node front, Node rear, int n) {
		Node insert = new Node();
		insert.setItem(n);
		
		Node p, trailer;
		
		if (isEmpty()) {
			front.setNext(insert);
			insert.setPrevious(front);
			insert.setNext(rear);
			rear.setPrevious(insert);
		}
		else {
			p = front.getNext();
			trailer = front;
			while (!p.equals(rear)) {
				trailer = p;
				p = p.getNext();
			}
			trailer.setNext(insert);
			insert.setPrevious(trailer);
			insert.setNext(rear);
			rear.setPrevious(insert);	
		}
	}
	
	public boolean isEmpty() {
		return (front.getNext() == null);
	}
	
	public int size() {
		int counter = 0;
		if (isEmpty()) {
			return counter;
		}
		else {
			Node trailer = front.getNext();
			counter++;
			while(trailer != null) {
				trailer = trailer.getNext();
				counter++;
			}
		}
		return counter;
	}
	
	public Iterator<Integer> iterator() {
		return new IteratorList();
	}
	
	private class IteratorList implements Iterator<Integer> {

		private Node current_1 = front.getNext();
		private Node current_2 = rear.getPrevious();

		@Override
		public boolean hasNext() {
			if (current_1.getNext() != null) {
				return true;
			}
			return false;
		}

//		public boolean hasPrevious() {
//			if (current_2.getPrevious() != null) {
//				return true;
//			}
//			return false;
//		}

		@Override
		public Integer next() {
			int i = current_1.getItem();
			current_1 = current_1.getNext();
			return i;
		}

//		public Integer previous() {
//			int i = current_2.getItem();
//			current_2 = current_2.getPrevious();
//			return i;
//		}
	}
	
	public void sortList() {
		Node current = front.getNext();
		Node last = rear.getPrevious();
		setCounter1(0);
		setCounter2(0);
		setCounter3(0);
		while (!current.equals(last)) {
			setCounter3(getCounter3()+1);;
			int i = getCounter2();
			while(!current.equals(last)) {
				if (current.getItem() > current.getNext().getItem()) {
					if (current.getNext().equals(last)) {
						last = current;
					}
					swap(current);
					setCounter2(getCounter2()+1);
					
				}
				else {
					current = current.getNext();
				}
				setCounter1(getCounter1()+1);
			}
			if (isSorted(i, counter2)) {
				counter3--;
				break;
			}
			last = last.getPrevious();
			current = front.getNext();
		}
	}
	
	private void swap(Node current) {
		Node past;
		Node next;
		Node follow;
		past = current.getPrevious();
		next = current.getNext();
		if (next.getNext() == null) {
			current.setNext(null);
		}
		else {
			follow = next.getNext();		
			current.setNext(follow);
			follow.setPrevious(current);
		}
		past.setNext(next);
		next.setPrevious(past);
		next.setNext(current);
		current.setPrevious(next);
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
