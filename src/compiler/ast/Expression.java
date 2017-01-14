package compiler.ast;

import java.util.LinkedList;

public class Expression extends Expr
{
	public LinkedList<Expr> comp;
	
	public Expression(Expr _a)
	{
		comp = new LinkedList<Expr>();
		comp.add(_a);
	}
}
