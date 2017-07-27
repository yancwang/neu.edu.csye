
public class BinarySearchTree {
	
	private Node head;
	
	/**
	 * Constructor
	 */
	public BinarySearchTree(int[] array) {
		head = new Node();
		head.setItem(array[0]); 
		head.setRoot(null);
		for(int i = 1; i < array.length; i++) {
			insert(array[i]);
		}
	}

	private void insert(int i) {
		Node track = head;
		Node temp;
		while(track != null) {
			if(i > track.getItem()) {
				if(track.getRight() != null) {
					track = track.getRight();
				}
				else {
					temp = new Node();
					temp.setItem(i);
					temp.setRoot(track);
					track.setRight(temp);
					break;
				}
			}
			if(i < track.getItem()) {
				if(track.getLeft() != null) {
					track = track.getLeft();
				}
				else {
					temp = new Node();
					temp.setItem(i);
					temp.setRoot(track);
					track.setLeft(temp);
					break;
				}
			}
			if(i == track.getItem()) {
				break;
			}
		}
	}
	
	private int calculateTreeHeight(Node track) {
		int height = 0;
		if (track != null) {
			height = Math.max(calculateTreeHeight(track.getLeft()), calculateTreeHeight(track.getRight())) + 1;
		}
		else {
			height = height + 0;
		}
		return height;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return calculateTreeHeight(head);
	}

}
