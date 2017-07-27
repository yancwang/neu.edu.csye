/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.neu.info6205.src;

/**
 *
 * @author Rachan Hegde
 */
public class Assignment1Solution {
    
    public boolean validateClass(String inputfile){
        /*
        *
        *TO-DO please implement the code here
        */
        
        return false;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.neu.info6205.src;

import java.util.Stack;
import java.util.StringTokenizer;

/**
 * @author Rachan Hegde
 */
public class Assignment1Solution {
	private Stack<String> stack1 = new Stack<String>();
	private Stack<String> stack2 = new Stack<String>();
	private Stack<String> stack3 = new Stack<String>();
    
    public boolean validateClass(String inputfile){
        /**
        * TODO please implement the code here
        */
    	int n=0;
    	int m=0;
    	int l=0;
    	StringTokenizer st = new StringTokenizer(inputfile);

    	while (st.hasMoreTokens()) {
    		String s = st.nextToken();

    		if(s.equals("/**")){
    			stack2.push("*");
    		}	
    		if(s.equals("/*")){
    			stack2.push("*");
    		}
    		if(s.equals("*/")){
    			stack2.pop();
    			l++;
    			System.out.println("l=" +l);
    		}
//    		if(s.equals("\"")){
//    			if (stack3.isEmpty()){
//    				stack3.push("\"");
//        			continue;
//    			}
//    			else {
//    				stack3.pop();
//        			continue;
//    			}
//    		}
    		
//    		if(stack2.isEmpty()&&stack3.isEmpty()) {
    			if(s.equals("{")){
//    				stack1.push("*");
    				n++;
    				System.out.println("n = "+ n);
    			}
    			
                if(s.equals("}")){
                	m++;
                	System.out.println("m = "+ m);
//                	if(stack1.empty())
//                		return false;
//                	stack1.pop();
                	
                }
//    		}
        }
    	
    	if(stack1.isEmpty()){
    		return true;
    	}
    	
        return false;
    }
}

String s = st.nextToken();

    		if(s.equals("/**")){
    			stack2.push("*");
    		}	
    		if(s.equals("/*")){
    			stack2.push("*");
    		}
    		if(s.equals("*/")){
    			stack2.pop();
    			l++;
    			System.out.println("l=" +l);
    		}
//    		if(s.equals("\"")){
//    			if (stack3.isEmpty()){
//    				stack3.push("\"");
//        			continue;
//    			}
//    			else {
//    				stack3.pop();
//        			continue;
//    			}
//    		}
    		
//    		if(stack2.isEmpty()&&stack3.isEmpty()) {
    			if(s.equals("{")){
//    				stack1.push("*");
    				n++;
    				System.out.println("n = "+ n);
    			}
    			
                if(s.equals("}")){
                	m++;
                	System.out.println("m = "+ m);
//                	if(stack1.empty())
//                		return false;
//                	stack1.pop();
                	
                }
//    		}