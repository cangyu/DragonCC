package compiler.ast;

import java.util.LinkedList;

public class Stmts 
{
	public String CN = getClass().getName();
	public LinkedList<Stmt> comp;
	
	public Stmts(Stmt _s)
	{
		comp = new LinkedList<Stmt>();
		comp.add(_s);
	}
}
