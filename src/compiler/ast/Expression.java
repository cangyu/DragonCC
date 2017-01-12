package compiler.ast;

import java.util.LinkedList;

public class Expression extends Expr
{
	public LinkedList<AssignmentExpr> comp;
	
	public Expression(AssignmentExpr _a)
	{
		comp = new LinkedList<AssignmentExpr>();
		comp.add(_a);
	}
}
