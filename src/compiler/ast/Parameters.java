package compiler.ast;

import java.util.LinkedList;

public class Parameters 
{
	public String CN = getClass().getName();
	public LinkedList<PlainDecl> comp;
	
	public Parameters(PlainDecl _pd)
	{
		comp = new LinkedList<PlainDecl>();
		comp.add(_pd);
	}
}
