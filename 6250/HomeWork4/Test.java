import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFrame;
import org.apache.commons.lang3.time.StopWatch;
import org.jfree.ui.RefineryUtilities;

public class Test {

	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StopWatch s = new StopWatch();
		try {
			System.out.println("Please input the minium number: ");
			int x = Integer.parseInt(br.readLine());
			System.out.println("Please input the maxium number: ");
			int y = Integer.parseInt(br.readLine());
			int loops = 9;
			long t1[] = new long[loops];
			long t2[] = new long[loops];
			int sum1[] = new int[loops];
			int sum2[] = new int[loops];
			for (int i = 3; i < loops + 3; i++) {
				Matrix m = new Matrix((int) Math.pow(2, i), (int) Math.pow(2, i), x, y);
				s.start();
				m.matrixAdd(m.getM());
				m.matrixDelete(m.getM());
				m.matrixTimes(m.getM());
				s.stop();
				t1[i - 3] = s.getTime();
				sum1[i - 3] = m.getSum1();
				s.reset();
				Matrix n = new Matrix((int) Math.pow(2, i), (int) Math.pow(2, i), x, y);
				s.start();
				n.matrixAdd(n.getM());
				n.matrixDelete(n.getM());
				n.matrixStrassenTimes(n.getM());
				s.stop();
				t2[i - 3] = s.getTime();
				sum2[i - 3] = n.getSum2();
				s.reset();				
			}
			int xline[] = new int[loops];
			for(int i = 0; i < loops; i++){
				xline[i] = i + 3;
			}
//			for (int i = 0; i < loops; i++) {
//				System.out.println(xline[i]);
//				System.out.println(t1[i]);
//				System.out.println(t2[i]);
//				System.out.println(sum1[i]);
//				System.out.println(sum2[i]);
//			}
			MyPanel chart = new MyPanel("Chart", xline, t1, t2);
			chart.pack( );          
		    RefineryUtilities.centerFrameOnScreen(chart);          
		    chart.setVisible(true); 
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
