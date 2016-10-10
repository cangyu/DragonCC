package compiler.ast;

import java.util.LinkedList;

public class Declarators 
{
	public LinkedList<Declarator> declarator_list;
	
	public Declarators(Declarator _declr)
	{
		declarator_list=new LinkedList<Declarator>();
		declarator_list.add(_declr);
	}
}
