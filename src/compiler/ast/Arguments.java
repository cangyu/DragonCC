package compiler.ast;

import java.util.LinkedList;

public class Arguments 
{
	public LinkedList<AssignmentExpr> comp;
	
	public Arguments(AssignmentExpr _ae)
	{
		comp = new LinkedList<AssignmentExpr>();
		comp.add(_ae);
	}
}
