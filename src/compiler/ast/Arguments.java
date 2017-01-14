package compiler.ast;

import java.util.LinkedList;

public class Arguments
{
	public String CN = getClass().getName();
	public LinkedList<Expr> comp;
	
	public Arguments(Expr _ae)
	{
		comp = new LinkedList<Expr>();
		comp.add(_ae);
	}
}
