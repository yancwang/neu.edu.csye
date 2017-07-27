package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		System.out.println("Choose the way of generate:");
		System.out.println("1. Random generate");
		System.out.println("2. File input");
		Scanner s = null;
		s = new Scanner(System.in);
		int n = 0;
		int t = 0;
		ArrayList<Integer> list = new ArrayList<Integer>();
		int num_input = s.nextInt();
		switch(num_input) {
		case 1: System.out.println("Please input the number of variable: ");
				n = s.nextInt();
				System.out.println("Please input the number of clause: ");
				t = s.nextInt();
				for (int i = 0; i < 3 * t; i++) {
					list.add(i, (int)Math.random() * 2 * n);
				}
				break;
		case 2: File file = new File("input.txt");
				try {
					s = new Scanner(file);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				n = s.nextInt();
				t = s.nextInt();
				int temp = 0;
				while(s.hasNextInt()){
					list.add(temp++, s.nextInt());
				}
				break;
		default:
				System.out.println("Wrong input!");
		}

		if (t * 3 != list.size()) System.out.println("Data missing");
		else {
			int[] array = new int[list.size()];
			for (int i = 0; i < array.length; i++) {
				array[i] = list.get(i);
			}
			Predicate p = new Predicate(t, n, array);
			p.iniValue();
			int[] solution = p.iniValue();
			System.out.println("Find One Solution: ");
			for(int si = 0; si < solution.length; si++) {
				System.out.print(solution[si]);
			}
			System.out.println();
//			System.out.println("Number of backtrack node: " + p.getTrack());
			System.out.println("Find All Solution:");
			p.allSolution();
		} 
	}

}
