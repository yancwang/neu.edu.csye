import java.util.Random;

public class Test {

	private static int[] array;
	
	public static void main(String[] args) {
		int sum = 0;
		int average = 0;
		for (int i = 0; i < 100; i++) {
			array = arrayGenerate(100);
			array = shuffleArray(array);
			BinarySearchTree bst = new BinarySearchTree(array);
			sum = sum +  bst.getHeight();
		}
		average = sum / 100;
		System.out.println("Tree height for 100 times and 100 size: " + average);
		
		sum = 0;
		for (int i = 0; i < 500; i++) {
			array = arrayGenerate(1000);
			array = shuffleArray(array);
			BinarySearchTree bst = new BinarySearchTree(array);
			sum = sum +  bst.getHeight();
		}
		average = sum / 500;
		System.out.println("Tree height for 500 times and 1000 size: " + average);
		
		sum = 0;
		for (int i = 0; i < 1000; i++) {
			array = arrayGenerate(1000);
			array = shuffleArray(array);
			BinarySearchTree bst = new BinarySearchTree(array);
			sum = sum +  bst.getHeight();
		}
		average = sum / 1000;
		System.out.println("Tree height for 1000 times and 1000 size: " + average);
	}
	
	private static int[] arrayGenerate(int N) {
		int[] array = new int[N];
		boolean[]  bool = new boolean[N];
		for(int i = 0; i < N; i++) {
			do {
				array[i] = (int)(Math.random() * N);
			} while(bool[array[i]]);
			bool[array[i]] = true;
		}
		return array;
	}
	
	private static int[] shuffleArray(int[] array) {
		int N = array.length;
		Random ran = new Random();
		for (int i = 0; i < N; i++) {
			int r = ran.nextInt(N);
			exch(array, i, r);
		}
		return array;
	}

	private static void exch(int[] array, int i, int r) {
		int temp = array[i];
		array[i] = array[r];
		array[r] = temp;
	}

}
