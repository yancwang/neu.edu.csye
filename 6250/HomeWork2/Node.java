package neu.edu.algr;

public class Node {

	private int item;
	private Node next;
	private Node previous;
	/**
	 * @return the item
	 */
	public int getItem() {
		return item;
	}
	/**
	 * @param item the item to set
	 */
	public void setItem(int item) {
		this.item = item;
	}
	/**
	 * @return the next
	 */
	public Node getNext() {
		return next;
	}
	/**
	 * @param next the next to set
	 */
	public void setNext(Node next) {
		this.next = next;
	}
	/**
	 * @return the previous
	 */
	public Node getPrevious() {
		return previous;
	}
	/**
	 * @param previous the previous to set
	 */
	public void setPrevious(Node previous) {
		this.previous = previous;
	}
	
}
