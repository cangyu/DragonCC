package compiler.ast;

import java.util.LinkedList;

public class Declarators 
{
	public String CN = getClass().getName();
	
	public LinkedList<Declarator> comp;
	
	public Declarators(Declarator _declr)
	{
		comp = new LinkedList<Declarator>();
		comp.add(_declr);
	}
}
