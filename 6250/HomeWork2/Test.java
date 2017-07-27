package neu.edu.algr;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Random;

public class Test {

	public static void main(String[] args) {
		Random r = new Random();
		int input = r.nextInt(100);
		
		try {
			FileOutputStream output = new FileOutputStream("output.text");
			PrintStream out = new PrintStream(output);
			
			SingleLinkedList s = new SingleLinkedList(input);
			Iterator<Integer> i = s.iterator();
			
			out.println("Assignment 2");
			out.println("Origin Single Linked List:");

			while (i.hasNext()) {
				out.print(i.next()+" ");
			}
			out.println();
			
			s.sortList();
			Iterator<Integer> m = s.iterator();
			
			out.println("Sorted Single Linked List:");
			while (m.hasNext()) {
				out.print(m.next()+" ");
			}
			out.println();
			out.println("Number of comparisons: " + s.getCounter1());
			out.println("Number of exchanges: " + s.getCounter2());
			out.println("Number of node traversals: " + s.getCounter3());
			out.println();
			
			DoubleLinkedList d = new DoubleLinkedList(input);
			Iterator <Integer> l = d.iterator();
			
			out.println("Origin Double Linked List:");
			while (l.hasNext()) {
				out.print(l.next()+" ");
			}
			out.println();
			
			d.sortList();
			Iterator <Integer> n = d.iterator();
			
			out.println("Sorted Double Linked List:");
			while (n.hasNext()) {
				out.print(n.next()+" ");
			}
			out.println();
			out.println("Number of comparisons: " + d.getCounter1());
			out.println("Number of exchanges: " + d.getCounter2());
			out.println("Number of node traversals: " + d.getCounter3());
			
			out.println();
			out.println("Number of comparisons: Each comparsion is made when comparing two nodes.");
			out.println("Number of exchanges: Each exchange is made when the first one is less than the second one of the two nodes comparing");
			out.println("Number of node traversals: Each traversal is made when the inner loop is made");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
