
public class Node {

	private int item;
	private Node root;
	private Node left;
	private Node right;
	
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
	 * @return the left
	 */
	public Node getLeft() {
		return left;
	}
	/**
	 * @param left the left to set
	 */
	public void setLeft(Node left) {
		this.left = left;
	}
	/**
	 * @return the right
	 */
	public Node getRight() {
		return right;
	}
	/**
	 * @param right the right to set
	 */
	public void setRight(Node right) {
		this.right = right;
	}
	/**
	 * @return the root
	 */
	public Node getRoot() {
		return root;
	}
	/**
	 * @param root the root to set
	 */
	public void setRoot(Node root) {
		this.root = root;
	}
	
}
