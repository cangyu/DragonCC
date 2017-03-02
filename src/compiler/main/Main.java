package compiler.main;

import java.util.*;

public class Main 
{
	public static void main(String[] args) 
	{
	    //test iterator
	    LinkedList<Integer> ta = new LinkedList<Integer>();
	    
	    for(int i=0;i<10;i++)
	        ta.add(i);
	    
	    Iterator<Integer> it = ta.iterator();
	    
	    while(it.hasNext())
	    {
	        System.out.println(it.next());
	    }
	}
}
