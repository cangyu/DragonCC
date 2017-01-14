package compiler.ast;

import java.util.LinkedList;

public class Stmts 
{
	public LinkedList<Stmt> comp;
	
	public Stmts(Stmt _s)
	{
		comp = new LinkedList<Stmt>();
		comp.add(_s);
	}
}
