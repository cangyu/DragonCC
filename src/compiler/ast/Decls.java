package compiler.ast;

import java.util.LinkedList;

public class Decls 
{
	public String CN = getClass().getName();
	public LinkedList<Decl> comp;
	
	public Decls(Decl _d)
	{
		comp = new LinkedList<Decl>();
		comp.add(_d);
	}
}
