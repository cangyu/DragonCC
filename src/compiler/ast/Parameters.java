package compiler.ast;

import java.util.LinkedList;

public class Parameters 
{
	public LinkedList<PlainDecl> comp;
	
	public Parameters()
	{
		comp=new LinkedList<PlainDecl>();
	}
}
